package com.rtrivino.inventory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.dto.EmailRequestDto;
import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.service.InventoryEmailService;
import com.rtrivino.inventory.service.InventoryPdfService;
import com.rtrivino.inventory.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * REST controller responsible for inventory-related operations.
 *
 * <p>
 * In this application, inventory is represented as the list of products
 * associated with a specific company. This controller allows clients to query
 * inventory by company, download the inventory report as a PDF file, and send
 * that same PDF report by email.
 * </p>
 *
 * <p>
 * The PDF generation and email delivery responsibilities are delegated to
 * dedicated services in order to keep the controller focused on request
 * handling
 * and HTTP response construction.
 * </p>
 */
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventoryController {

    private final ProductService productService;
    private final InventoryPdfService inventoryPdfService;
    private final InventoryEmailService inventoryEmailService;

    /**
     * Retrieves all products associated with the provided company NIT.
     *
     * <p>
     * This endpoint is used by the frontend inventory screen when the user
     * selects a company and searches for its related products.
     * </p>
     *
     * @param nitEmpresa company identifier used to filter inventory products
     * @return list of products registered for the selected company
     */
    @GetMapping("/empresa/{nitEmpresa}")
    public List<ProductDto> findByCompanyNit(@PathVariable String nitEmpresa) {
        return productService.findByCompanyNit(nitEmpresa);
    }

    /**
     * Generates and downloads a PDF inventory report for the selected company.
     *
     * <p>
     * The PDF is generated in memory and returned as binary content with
     * the {@code application/pdf} media type. The {@code Content-Disposition}
     * header is used so the browser can download the file as an attachment.
     * </p>
     *
     * @param nitEmpresa company identifier used to generate the inventory report
     * @return PDF file content as a byte array response
     */
    @GetMapping("/empresa/{nitEmpresa}/pdf")
    public ResponseEntity<byte[]> downloadInventoryPdf(@PathVariable String nitEmpresa) {
        byte[] pdf = inventoryPdfService.generateInventoryPdfByCompany(nitEmpresa);

        String fileName = "Inventario-" + nitEmpresa + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    /**
     * Sends the inventory PDF report to the provided email address.
     *
     * <p>
     * The request body is validated before the email service is called.
     * This ensures that invalid or empty email addresses are rejected at the
     * API boundary before attempting to generate and send the message.
     * </p>
     *
     * @param nitEmpresa      company identifier used to generate the inventory
     *                        report
     * @param emailRequestDto request body containing the destination email address
     */
    @PostMapping("/empresa/{nitEmpresa}/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendInventoryPdfByEmail(
            @PathVariable String nitEmpresa,
            @Valid @RequestBody EmailRequestDto emailRequestDto) {
        inventoryEmailService.sendInventoryPdfByCompany(
                nitEmpresa,
                emailRequestDto.getEmail());
    }

}
