package com.handmade.ecommerce.notification.channel.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * TwilioApiAdapter - Adapter for Twilio SMS API
 */
@Component
public class TwilioApiAdapter {
    
    @Value("${notification.sms.twilio.account.sid:}")
    private String accountSid;
    
    @Value("${notification.sms.twilio.auth.token:}")
    private String authToken;
    
    @Value("${notification.sms.twilio.from.number:}")
    private String fromNumber;
    
    /**
     * Send SMS via Twilio API
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public TwilioSmsResult sendSms(String toNumber, String message) {
        try {
            // TODO: Actual Twilio API call
            // Twilio.init(accountSid, authToken);
            // Message twilioMessage = Message.creator(
            //     new PhoneNumber(toNumber),
            //     new PhoneNumber(fromNumber),
            //     message
            // ).create();
            
            String messageSid = "SM" + System.currentTimeMillis();
            
            System.out.println(String.format(
                "[Twilio Adapter] Sent SMS to %s: %s (SID: %s)",
                toNumber, message.substring(0, Math.min(50, message.length())), messageSid
            ));
            
            return new TwilioSmsResult(messageSid, "sent");
            
        } catch (Exception e) {
            throw new TwilioApiException("Failed to send SMS", e);
        }
    }
    
    /**
     * Get message status
     */
    public String getMessageStatus(String messageSid) {
        try {
            // TODO: Actual Twilio API call
            // Message message = Message.fetcher(messageSid).fetch();
            // return message.getStatus().toString();
            
            return "delivered";
            
        } catch (Exception e) {
            throw new TwilioApiException("Failed to get message status", e);
        }
    }
    
    public static class TwilioSmsResult {
        private String messageSid;
        private String status;
        
        public TwilioSmsResult(String messageSid, String status) {
            this.messageSid = messageSid;
            this.status = status;
        }
        
        public String getMessageSid() { return messageSid; }
        public String getStatus() { return status; }
    }
    
    public static class TwilioApiException extends RuntimeException {
        public TwilioApiException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
