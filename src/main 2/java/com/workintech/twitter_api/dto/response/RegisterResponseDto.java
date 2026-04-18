package com.workintech.twitter_api.dto.response;

public record RegisterResponseDto(
        @NotBlank(message = "Kullanıcı adı boş olamaz.")
        @Size(max = 150, message = "Kullanıcı adı en fazla 150 karakter olabilir.")
        String username,

        @NotBlank(message = "Email boş olamaz.")
        @Email(message = "Geçerli bir email adresi giriniz.")
        String email,

        @NotBlank(message = "Şifre boş olamaz.")
        @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır.")
        String password,

        String bio

) {
}
