package com.handmade.ecommerce.notification.channel.email;

import com.handmade.ecommerce.notification.core.NotificationChannelPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * EmailChannelPlugin - Email notification implementation
 */
@Component
public class EmailChannelPlugin implements NotificationChannelPlugin {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    @Value("${notification.email.enabled:true}")
    private boolean enabled;
    
    @Value("${notification.email.from:noreply@handmade.com}")
    private String fromEmail;
    
    @Override
    public String getChannelCode() {
        return "EMAIL";
    }
    
    @Override
    public String getChannelName() {
        return "Email";
    }
    
    @Override
    public String getDescription() {
        return "Email notification channel via SMTP";
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public NotificationResult send(String recipient, String subject, String message, Map<String, Object> metadata) {
        // TODO: Use JavaMailSender
        // MimeMessage mimeMessage = mailSender.createMimeMessage();
        // MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // helper.setTo(recipient);
        // helper.setSubject(subject);
        // helper.setText(message, true); // HTML
        // mailSender.send(mimeMessage);
        
        System.out.println(String.format(
            "📧 [Email] Sending to %s: %s",
            recipient, subject
        ));
        
        String messageId = "email-" + System.currentTimeMillis();
        return new NotificationResult(true, messageId, "SENT", null);
    }
    
    @Override
    public BulkNotificationResult sendBulk(String[] recipients, String subject, String message, Map<String, Object> metadata) {
        int success = 0;
        int failure = 0;
        Map<String, String> failures = new HashMap<>();
        
        for (String recipient : recipients) {
            NotificationResult result = send(recipient, subject, message, metadata);
            if (result.isSuccess()) {
                success++;
            } else {
                failure++;
                failures.put(recipient, result.getErrorMessage());
            }
        }
        
        return new BulkNotificationResult(recipients.length, success, failure, failures);
    }
    
    @Override
    public boolean isValidRecipient(String recipient) {
        return recipient != null && EMAIL_PATTERN.matcher(recipient).matches();
    }
    
    @Override
    public int getPriority() {
        return 1; // High priority
    }
    
    @Override
    public boolean supportsRichContent() {
        return true; // Supports HTML
    }
}
