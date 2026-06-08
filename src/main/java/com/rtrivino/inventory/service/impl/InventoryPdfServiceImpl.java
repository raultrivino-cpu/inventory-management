package com.rtrivino.inventory.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rtrivino.inventory.dto.CategoryDto;
import com.rtrivino.inventory.dto.ProductDto;
import com.rtrivino.inventory.service.InventoryPdfService;
import com.rtrivino.inventory.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryPdfServiceImpl implements InventoryPdfService {

    private final ProductService productService;

    @Override
    public byte[] generateInventoryPdfByCompany(String nitEmpresa) {
        List<ProductDto> products = productService.findByCompanyNit(nitEmpresa);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font subtitleFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            Font bodyFont = new Font(Font.HELVETICA, 9, Font.NORMAL);

            Paragraph title = new Paragraph("Reporte de Inventario", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            if (!products.isEmpty()) {
                ProductDto firstProduct = products.get(0);
                document.add(new Paragraph("Empresa: " + firstProduct.getNombreEmpresa(), subtitleFont));
                document.add(new Paragraph("NIT: " + firstProduct.getNitEmpresa(), subtitleFont));
            } else {
                document.add(new Paragraph("NIT Empresa: " + nitEmpresa, subtitleFont));
                document.add(new Paragraph("No hay productos registrados para esta empresa.", subtitleFont));
            }

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 1.2f, 2.5f, 3.5f, 2.5f, 1.5f, 1.5f, 1.5f });

            addHeaderCell(table, "Código", headerFont);
            addHeaderCell(table, "Producto", headerFont);
            addHeaderCell(table, "Características", headerFont);
            addHeaderCell(table, "Categorías", headerFont);
            addHeaderCell(table, "Precio COP", headerFont);
            addHeaderCell(table, "Precio USD", headerFont);
            addHeaderCell(table, "Precio EUR", headerFont);

            for (ProductDto product : products) {
                addBodyCell(table, "PROD-" + product.getId(), bodyFont);
                addBodyCell(table, product.getNombre(), bodyFont);
                addBodyCell(table, product.getCaracteristicas(), bodyFont);
                addBodyCell(table, formatCategories(product.getCategorias()), bodyFont);
                addBodyCell(table, product.getPrecioPesos() != null ? product.getPrecioPesos().toString() : "-", bodyFont);
                addBodyCell(table, product.getPrecioDolares() != null ? product.getPrecioDolares().toString() : "-", bodyFont);
                addBodyCell(table, product.getPrecioEuros() != null ? product.getPrecioEuros().toString() : "-", bodyFont);
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception exception) {
            throw new RuntimeException("Error generando PDF de inventario", exception);
        }
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        table.addCell(cell);
    }

    private void addBodyCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "-", font));
        cell.setPadding(5);
        table.addCell(cell);
    }

    private String formatCategories(List<CategoryDto> categories) {
        if (categories == null || categories.isEmpty()) {
            return "-";
        }

        return categories.stream()
                .map(CategoryDto::getNombre)
                .collect(Collectors.joining(", "));
    }
}