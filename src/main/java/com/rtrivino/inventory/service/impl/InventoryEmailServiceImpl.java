package com.rtrivino.inventory.service.impl;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.rtrivino.inventory.service.InventoryEmailService;
import com.rtrivino.inventory.service.InventoryPdfService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryEmailServiceImpl implements InventoryEmailService {

    private final InventoryPdfService inventoryPdfService;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendInventoryPdfByCompany(String nitEmpresa, String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo destino es obligatorio");
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("El formato del correo destino no es válido");
        }

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

}
