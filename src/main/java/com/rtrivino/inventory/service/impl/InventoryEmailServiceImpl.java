package com.rtrivino.inventory.service.impl;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.rtrivino.inventory.service.InventoryEmailService;
import com.rtrivino.inventory.service.InventoryPdfService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * Service implementation responsible for sending inventory reports by email.
 *
 * <p>
 * This service generates the inventory PDF for a specific company and sends
 * it as an email attachment using Spring's {@link JavaMailSender}. The PDF
 * generation is delegated to {@link InventoryPdfService} so the same report
 * generation logic can be reused for both direct download and email delivery.
 * </p>
 *
 * <p>
 * The destination email address is validated before attempting to create and
 * send the email. This defensive validation protects the service layer in case
 * the method is called from another entry point without controller-level
 * validation.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class InventoryEmailServiceImpl implements InventoryEmailService {

    private final InventoryPdfService inventoryPdfService;
    private final JavaMailSender javaMailSender;

    /**
     * Generates the inventory PDF for the provided company and sends it to the
     * destination email address.
     *
     * <p>
     * The email is created as a MIME message because it includes a PDF
     * attachment. In local development, the SMTP provider can be configured with
     * Mailtrap to safely validate email delivery without sending real emails.
     * </p>
     *
     * @param nitEmpresa company identifier used to generate the inventory report
     * @param email      destination email address
     * @throws IllegalArgumentException if the email is null, blank or invalid
     * @throws RuntimeException         if the PDF or email sending process fails
     */
    @Override
    public void sendInventoryPdfByCompany(String nitEmpresa, String email) {
        validateEmail(email);

        try {
            byte[] pdf = inventoryPdfService.generateInventoryPdfByCompany(nitEmpresa);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Reporte de inventario");
            helper.setText("Hola,\n\nAdjunto encontrarás el reporte de inventario solicitado.\n\nSaludos.");
            helper.addAttachment("Inventario-" + nitEmpresa + ".pdf", new ByteArrayResource(pdf));

            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new RuntimeException("Error enviando correo de inventario", ex);
        }
    }

    /**
     * Validates the destination email address before attempting to send the
     * message.
     *
     * @param email destination email address
     * @throws IllegalArgumentException if the email is null, blank or has an
     *                                  invalid format
     */
    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo destino es obligatorio");
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("El formato del correo destino no es válido");
        }
    }

}
