package services.events;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import models.events.Ticket;
import utils.MyDataBase;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ticketService {

    private Connection connection;

    public ticketService() {
        connection = MyDataBase.getInstance().getConnection();
    }


    public void create(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO ticket (Idevent, Iduser,qrcode) " +
                "VALUES (?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, ticket.getIdevent());
        preparedStatement.setInt(2, ticket.getIduser());
        preparedStatement.setString(3, ticket.getQrcode());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    public void update(Ticket ticket) throws SQLException {
        String sql = "UPDATE Ticket SET Idevent = ?, Iduser = ? WHERE Idticket = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, ticket.getIdevent());
        preparedStatement.setInt(2, ticket.getIduser());
        preparedStatement.setInt(3, ticket.getIdticket());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public String getName(int eventId) throws SQLException {
        String name = null;
        String sql = "SELECT name FROM event WHERE Idevent = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        }
        return name;
    }


    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ticket WHERE Idticket = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
     /* public void generatePDF(String ticketContent) throws IOException {
        String pdfPath = "C:\\Users\\Nourhene\\Desktop\\web data\\exampledatasss.pdf";

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document doc = new Document(pdfDoc)) {

            // Create a table with three columns
            Table table = new Table(3);

            // Add column headers
            table.addCell("Ticket ID");
            table.addCell("Event ID");
            table.addCell("User ID");

            // Split the ticket content by a delimiter to extract ticket details
            String[] ticketDetails = ticketContent.split(";"); // Assuming the delimiter is ";"

            // Add ticket details to the table
            for (String detail : ticketDetails) {
                table.addCell(detail); // Add each detail to the table
            }

            // Add the table to the PDF document
            doc.add(table);
        }
    }
 */


    public List<Ticket> read() throws SQLException {
        String sql = "SELECT * FROM ticket";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Ticket> tickets = new ArrayList<>();
        while (rs.next()) {
            Ticket ticket = new Ticket();
            ticket.setIdticket(rs.getInt("Idticket"));
            ticket.setIdevent(rs.getInt("Idevent"));
            ticket.setIduser(rs.getInt("Iduser"));
            tickets.add(ticket);
        }
        rs.close();
        statement.close();
        return tickets;
    }

    public void search(int id) throws SQLException {
        String sql = "SELECT e.* FROM event e INNER JOIN ticket t ON e.idevent = t.idevent WHERE t.Iduser = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        // Process the ResultSet here (e.g., iterate over the rows and retrieve data)

        resultSet.close();
        ps.close();
    }






/*
    public void generatePDF(int eventId, String description, String barcodePath, String barcodeText) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        String pdfFileName = "newssspdf_" + timestamp + ".pdf";
        String pdfPath = "C:\\Users\\Nourhene\\Desktop\\web data\\" + pdfFileName;

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document doc = new Document(pdfDoc)) {

            // Add name and description as paragraphs to the PDF document
            doc.add(new Paragraph("Event ID: " + eventId));
            doc.add(new Paragraph("Description: " + description));

            // Load the barcode image from the file
            try {
                ImageData imageData = ImageDataFactory.create(barcodePath);
                Image barcodeImage = new Image(imageData);

                // Add barcode image to the PDF
                doc.add(barcodeImage);

                // Add barcode text below the barcode image
                doc.add(new Paragraph("Barcode Text: " + barcodeText));
            } catch (IOException e) {
                System.err.println("Error loading barcode image: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error creating PDF document: " + e.getMessage());
        }
    } */


    public void generatePDF(int eventId, String description, String date, String location, String barcodePath, String barcodeText) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        String pdfFileName = "event_ticket_" + timestamp + ".pdf";
        String pdfPath = "C:/xampp/htdocs/tickets/" + pdfFileName; // Adjust this path accordingly

        try (PdfWriter writer = new PdfWriter(pdfPath);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document doc = new Document(pdfDoc)) {

            // Set margins
            doc.setMargins(36, 36, 36, 36);
            DeviceRgb blueColor = new DeviceRgb(0, 102, 204); // Professional blue color
            DeviceRgb borderColor = new DeviceRgb(0, 0, 0); // Black color for border

            // Create a container for the ticket content
            Div ticketContainer = new Div();

            // Load the barcode image from the file
            try {
                Image barcodeImage = new Image(ImageDataFactory.create(barcodePath));
                barcodeImage.setWidth(200).setHeight(100); // Set size
                barcodeImage.setHorizontalAlignment(HorizontalAlignment.LEFT); // Align to left

                // Add barcode image to the ticket container
                ticketContainer.add(barcodeImage);
            } catch (IOException e) {
                System.err.println("Error loading barcode image: " + e.getMessage());
            }

            // Add event details to the ticket container
            Paragraph details = new Paragraph()
                    .add("Event ID: " + eventId + "\n")
                    .add("Description: " + description + "\n")
                    .add("Date: " + date + "\n")
                    .add("Location: " + location + "\n")
                    .setTextAlignment(TextAlignment.LEFT);
            ticketContainer.add(details);

            // Add barcode text below the barcode image
            Paragraph barcodeTextParagraph = new Paragraph("Barcode Text: " + barcodeText)
                    .setTextAlignment(TextAlignment.CENTER);
            ticketContainer.add(barcodeTextParagraph);

            // Set styles for the ticket container
            ticketContainer.setBorder(new SolidBorder(borderColor, 1));
            ticketContainer.setPadding(10);

            // Add "Generated by YourApp" footer to the ticket container
            Paragraph footer = new Paragraph("Flayes & Flayes pass ")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(10);
            ticketContainer.add(footer);

            // Add the ticket container to the PDF document
            doc.add(ticketContainer);

        } catch (IOException e) {
            System.err.println("Error creating PDF document: " + e.getMessage());
        }
    }
}

/* public void generatePDF(String eventId, String descriptionText, String barcodeImagePath) throws IOException {
    // Create a new document
    PdfDocument pdfDocument = new PdfDocument(new PdfWriter("C:\\Users\\Nourhene\\Desktop\\web data\\ok.pdf"));
    Document document = new Document(pdfDocument);
    try {
        // Initialize PDF writer
        PdfWriter writer = new PdfWriter("C:\\Users\\Nourhene\\Desktop\\web data\\ok.pdf");

        // Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Open document for writing
        document = new Document(pdfDoc);

        // Add event ID and description to the PDF
        document.add(new Paragraph("Event ID: " + eventId));
        document.add(new Paragraph("Description: " + descriptionText));

        // Add barcode image to the PDF
        Image barcodeImage = new Image(String.valueOf(ImageDataFactory.create(barcodeImagePath)));
        document.add((IBlockElement) barcodeImage);

        System.out.println("PDF successfully generated!");
    } finally {
        // Close the document
        if (document != null) {
            document.close();
        }
    }
}
*/





