package com.workintech.twitter_api.dto.response;

public record LoginResponseDto(


        @NotBlank(message = "Email boş olamaz.")
        @Email(message = "Geçerli bir email adresi giriniz.")
        String email,

        @NotBlank(message = "Şifre boş olamaz.")
        @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır.")
        String password
) {
}
