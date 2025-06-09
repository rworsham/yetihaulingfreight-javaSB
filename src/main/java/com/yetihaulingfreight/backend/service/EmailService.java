package com.yetihaulingfreight.backend.service;

import com.mailjet.client.ClientOptions;
import com.yetihaulingfreight.backend.dto.ContactRequest;
import com.yetihaulingfreight.backend.dto.QuoteRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class EmailService {

    @Value("${MJ_APIKEY_PUBLIC}")
    private String mailjetApiKey;

    @Value("${MJ_APIKEY_PRIVATE}")
    private String mailjetApiSecret;

    @Value("${MAILJET_SENDER_EMAIL}")
    private String senderEmail;

    @Value("${MAILJET_SENDER_NAME}")
    private String senderName;

    @Value("${MAILJET_RECIPIENT_EMAIL}")
    private String recipientEmail;

    private MailjetClient mailjetClient;

    @PostConstruct
    public void initClient() {
        this.mailjetClient = new MailjetClient(ClientOptions.builder()
                .apiKey(mailjetApiKey)
                .apiSecretKey(mailjetApiSecret)
                .build());
    }

    public String sendQuoteEmail(QuoteRequest quoteRequest) {
        if ((quoteRequest.getEmail() == null || quoteRequest.getEmail().isBlank()) &&
                (quoteRequest.getPhoneNumber() == null || quoteRequest.getPhoneNumber().isBlank())) {
            throw new IllegalArgumentException("Either email or phone number must be provided.");
        }

        try {
            StringBuilder textBody = new StringBuilder();
            textBody.append("Pickup ZIP: ").append(quoteRequest.getPickupZip()).append("\n")
                    .append("Delivery ZIP: ").append(quoteRequest.getDeliveryZip()).append("\n")
                    .append("Weight: ").append(quoteRequest.getWeight()).append("\n")
                    .append("Length: ").append(quoteRequest.getLength()).append("\n")
                    .append("Width: ").append(quoteRequest.getWidth()).append("\n")
                    .append("Load Type: ").append(quoteRequest.getLoadType()).append("\n")
                    .append("Email: ").append(quoteRequest.getEmail() != null ? quoteRequest.getEmail() : "N/A").append("\n")
                    .append("Phone Number: ").append(quoteRequest.getPhoneNumber() != null ? quoteRequest.getPhoneNumber() : "N/A");

            StringBuilder htmlBody = new StringBuilder();
            htmlBody.append("<h3>Quote Request Details</h3>")
                    .append("<ul>")
                    .append("<li><strong>Pickup ZIP:</strong> ").append(quoteRequest.getPickupZip()).append("</li>")
                    .append("<li><strong>Delivery ZIP:</strong> ").append(quoteRequest.getDeliveryZip()).append("</li>")
                    .append("<li><strong>Weight:</strong> ").append(quoteRequest.getWeight()).append("</li>")
                    .append("<li><strong>Length:</strong> ").append(quoteRequest.getLength()).append("</li>")
                    .append("<li><strong>Width:</strong> ").append(quoteRequest.getWidth()).append("</li>")
                    .append("<li><strong>Load Type:</strong> ").append(quoteRequest.getLoadType()).append("</li>")
                    .append("<li><strong>Email:</strong> ").append(quoteRequest.getEmail() != null ? quoteRequest.getEmail() : "N/A").append("</li>")
                    .append("<li><strong>Phone Number:</strong> ").append(quoteRequest.getPhoneNumber() != null ? quoteRequest.getPhoneNumber() : "N/A").append("</li>")
                    .append("</ul>");

            MailjetRequest request = new MailjetRequest(com.mailjet.client.resource.Emailv31.resource)
                    .property(com.mailjet.client.resource.Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put("From", new JSONObject()
                                            .put("Email", senderEmail)
                                            .put("Name", senderName))
                                    .put("To", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", recipientEmail)
                                                    .put("Name", "Support")))
                                    .put("Subject", "New Quote Request Received")
                                    .put("TextPart", textBody.toString())
                                    .put("HTMLPart", htmlBody.toString())
                            ));

            MailjetResponse response = this.mailjetClient.post(request);

            if (response.getStatus() == 200) {
                return "Email sent successfully.";
            } else {
                throw new MailjetException("Failed to send email: " + response.getData().toString());
            }

        } catch (MailjetException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }

    public String sendContactEmail(ContactRequest contactRequest) {
        try {
            MailjetRequest request = new MailjetRequest(com.mailjet.client.resource.Emailv31.resource)
                    .property(com.mailjet.client.resource.Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put("From", new JSONObject()
                                            .put("Email", senderEmail)
                                            .put("Name", senderName))
                                    .put("To", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", recipientEmail)
                                                    .put("Name", "Support")))
                                    .put("Subject", "New Contact Form Submission")
                                    .put("TextPart", "Email: " + contactRequest.getEmail() + "\n\n" + contactRequest.getMessage())
                                    .put("HTMLPart", "<h3>Contact Form Message</h3>" +
                                            "<p><strong>From:</strong> " + contactRequest.getEmail() + "</p>" +
                                            "<p><strong>Message:</strong><br/>" + contactRequest.getMessage() + "</p>")
                            ));

            MailjetResponse response = this.mailjetClient.post(request);

            if (response.getStatus() == 200) {
                return "Email sent successfully.";
            } else {
                throw new MailjetException("Failed to send email: " + response.getData().toString());
            }

        } catch (MailjetException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}
