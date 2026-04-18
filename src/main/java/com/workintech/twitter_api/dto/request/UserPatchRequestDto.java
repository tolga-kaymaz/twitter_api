package com.workintech.twitter_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserPatchRequestDto(
        @Size(max = 150, message = "Kullanıcı adı en fazla 150 karakter olabilir.")
        String username,

        @Email(message = "Geçerli bir email adresi giriniz.")
        String email,

        String bio
) {
}
