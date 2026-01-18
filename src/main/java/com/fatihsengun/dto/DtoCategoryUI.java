package com.fatihsengun.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoCategoryUI {
    @NotBlank(message = "Cattegory cannot be blank")
    @Size(min = 2,max = 50)
    private String name;
}
