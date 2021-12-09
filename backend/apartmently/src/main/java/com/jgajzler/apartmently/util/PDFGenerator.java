package com.jgajzler.apartmently.util;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.AdImage;
import com.jgajzler.apartmently.entity.enums.AdType;
import com.jgajzler.apartmently.exception.PDFGeneratorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PDFGenerator {
    private static final String boldFont = StandardFonts.HELVETICA_BOLD;
    private static final String normalFont = StandardFonts.HELVETICA;

    private static Document document;

    public static ByteArrayInputStream adReport(Ad ad) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setTagged();
        document = new Document(pdfDocument);

        document.add(generateParagraph("Ad Details", boldFont).setTextAlignment(TextAlignment.CENTER));
        document.add(generateImage(ad));
        document.add(generateParagraph("Ad Name: ", boldFont)
                .add(generateParagraph(ad.getAdName(), normalFont)));
        document.add(generateParagraph("Description: ", boldFont)
                .add(generateParagraph(ad.getDescription(), normalFont)));

        addPrice(ad);
        createTable(ad);

        document.add(generateParagraph("\n\nAd Type: ", boldFont))
                .add(generateParagraph(ad.getAdType().name(), normalFont));

        document.add(generateParagraph("Address Details", boldFont));
        document.add(generateParagraph(getAddressString(ad), normalFont));

        document.add(generateParagraph("Date created: ", boldFont)
                .add(generateParagraph(ad.getDateCreated().toString(), normalFont)));

        addUserDetails(ad);

        document.close();


        return new ByteArrayInputStream(out.toByteArray());

    }

    private static Paragraph generateParagraph(String content, String font) {
        try {
            return new Paragraph(content)
                    .setFont(PdfFontFactory.createFont(font, PdfEncodings.CP1250));
        } catch (IOException e) {
            throw new PDFGeneratorException();
        }
    }

    private static void addHeaderCellToTable(Table table, String content) {
        try {
            table.addCell(content)
                    .setFont(PdfFontFactory.createFont(boldFont, PdfEncodings.CP1250))
                    .setTextAlignment(TextAlignment.CENTER);
        } catch (IOException e) {
            throw new PDFGeneratorException();
        }
    }

    private static void addCellToTable(Table table, String content) {
        try {
            table.addCell(new Cell().add(new Paragraph(content))
                            .setFont(PdfFontFactory.createFont(normalFont)))
                    .setTextAlignment(TextAlignment.CENTER);
        } catch (IOException e) {
            throw new PDFGeneratorException();
        }
    }

    private static Image generateImage(Ad ad) {
        try {
            List<AdImage> adImages = new ArrayList<>(ad.getAdImages());
            ImageData imageData = ImageDataFactory.create(adImages.get(0).getImageUrl());
            Image pdfImg = new Image(imageData);
            pdfImg.setMaxHeight(200);
            pdfImg.setHorizontalAlignment(HorizontalAlignment.CENTER);

            return pdfImg;
        } catch (MalformedURLException e) {
            throw new PDFGeneratorException();
        }
    }

    private static void createTable(Ad ad) {
        Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
        addHeaderCellToTable(table, "Plot Surface");
        addHeaderCellToTable(table, "Number Of Bathrooms");
        addHeaderCellToTable(table, "Number Of Bedrooms");
        addHeaderCellToTable(table, "Floor Number");
        addCellToTable(table, ad.getPlotSurface() + " m\u00B2");
        addCellToTable(table, String.valueOf(ad.getNumberOfBathrooms()));
        addCellToTable(table, String.valueOf(ad.getNumberOfBedrooms()));
        addCellToTable(table, String.valueOf(ad.getFloorNumber()));

        document.add(table);

    }

    private static void addPrice(Ad ad) {
        if (ad.getAdType() == AdType.SALE) {
            document.add(generateParagraph("Price: ", boldFont)
                    .add(generateParagraph(ad.getPrice() + " zł", normalFont)));
        } else {
            document.add(generateParagraph("Price: ", boldFont)
                    .add(generateParagraph(ad.getPrice() + " zł/mth", normalFont)));
        }
    }

    private static String getAddressString(Ad ad) {
        return ad.getAddress().getStreetName() + " "
                + ad.getAddress().getStreetNumber() + "\n" + ad.getAddress().getPostalCode()
                + ", " + ad.getAddress().getCity().getName() + "\n"
                + ad.getAddress().getCountry().getName();
    }

    private static void addUserDetails(Ad ad) {
        document.add(generateParagraph("Owner Contact Info", boldFont));
        document.add(generateParagraph(ad.getUser().getUserDetails()
                .getName() + " " +
                ad.getUser().getUserDetails().getSurname(), normalFont));
        document.add(generateParagraph("Phone: " + ad.getUser().getUserDetails()
                        .getPhoneNumber()
                , normalFont));
        document.add(generateParagraph("Email: " + ad.getUser().getEmail(), normalFont));
    }


}
