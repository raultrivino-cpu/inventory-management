package com.rtrivino.inventory.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Request body used to send an inventory PDF report by email.
 *
 * <p>The email field is validated before the request reaches the service layer.</p>
 */
@Getter
@Setter
public class EmailRequestDto {
    @NotBlank(message = "El correo destino es obligatorio")
    @Email(message = "El formato del correo destino no es válido")
    private String email;
}
