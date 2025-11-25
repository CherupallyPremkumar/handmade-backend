package com.handmade.ecommerce.notification.channel.sms;

import com.handmade.ecommerce.notification.core.NotificationChannelPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * SMSChannelPlugin - SMS notification implementation (Twilio, AWS SNS, etc.)
 */
@Component
public class SMSChannelPlugin implements NotificationChannelPlugin {
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\+?[1-9]\\d{1,14}$"); // E.164 format
    
    @Value("${notification.sms.enabled:true}")
    private boolean enabled;
    
    @Value("${notification.sms.provider:TWILIO}")
    private String provider;
    
    @Override
    public String getChannelCode() {
        return "SMS";
    }
    
    @Override
    public String getChannelName() {
        return "SMS";
    }
    
    @Override
    public String getDescription() {
        return "SMS notification channel via " + provider;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public NotificationResult send(String recipient, String subject, String message, Map<String, Object> metadata) {
        // TODO: Integrate with Twilio/AWS SNS
        // Twilio.init(accountSid, authToken);
        // Message.creator(
        //     new PhoneNumber(recipient),
        //     new PhoneNumber(fromNumber),
        //     message
        // ).create();
        
        System.out.println(String.format(
            "📱 [SMS] Sending to %s: %s",
            recipient, message
        ));
        
        String messageId = "sms-" + System.currentTimeMillis();
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
        return recipient != null && PHONE_PATTERN.matcher(recipient).matches();
    }
    
    @Override
    public int getPriority() {
        return 2; // Medium priority
    }
    
    @Override
    public boolean supportsRichContent() {
        return false; // Plain text only
    }
}
