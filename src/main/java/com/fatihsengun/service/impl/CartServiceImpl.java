package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoCart;
import com.fatihsengun.dto.DtoCartItemUI;
import com.fatihsengun.dto.DtoCartUI;
import com.fatihsengun.entity.Cart;
import com.fatihsengun.entity.CartItem;
import com.fatihsengun.entity.Product;
import com.fatihsengun.entity.User;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.CartRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.ICartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IGlobalMapper globalMapper;

    private Cart resolveCart(String guestId) {
        if (guestId != null && !guestId.isEmpty()) {
            return cartRepository.findByGuestId(guestId)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setGuestId(guestId);
                        return cartRepository.save(newCart);
                    });
        } else {
            User currentUser = identityService.getCurrentUser();
            if (currentUser == null) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Must provide a guest ID or be logged in."));
            }
            return cartRepository.findByUser(currentUser)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser(currentUser);
                        return cartRepository.save(newCart);
                    });
        }
    }

    @Override
    @Transactional
    public DtoCart createCart(DtoCartUI dtoCartUI) {
        Cart cart = resolveCart(dtoCartUI.getGuestId());


        for (DtoCartItemUI itemUI : dtoCartUI.getItems()) {
            Product product = productRepository.findById(itemUI.getProductId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
            if (product.getStock() < itemUI.getQuantity()) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Insufficient stock for: " + product.getName()));
            }

            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId())).findFirst();
            if (existingItem.isPresent()) {
                CartItem cartItem = existingItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + itemUI.getQuantity());
            } else {
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(itemUI.getQuantity());
                cart.getItems().add(newItem);
            }
        }
        Cart savedCart = cartRepository.save(cart);
        return globalMapper.toDtoCart(savedCart);
    }

    @Override
    public DtoCart getMyCart(String guestId) {
        Cart cart = resolveCart(guestId);

        return globalMapper.toDtoCart(cart);
    }

    @Override
    public DtoCart deleteCartItem(DtoCartItemUI dtoCartItemUI, String guestId) {
        Cart cart = resolveCart(guestId);

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(dtoCartItemUI.getProductId()))
                .findFirst();
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            Integer newQuantity = existingItem.getQuantity() - dtoCartItemUI.getQuantity();
            if (newQuantity <= 0) {
                cart.getItems().remove(existingItem);
            } else {
                existingItem.setQuantity(newQuantity);
            }
        } else {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found"));
        }
        Cart savedCart = cartRepository.save(cart);
        return globalMapper.toDtoCart(savedCart);
    }


}


