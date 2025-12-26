package com.handmade.ecommerce.notification.core;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * NotificationChannelRegistry - Auto-discovers and manages notification channel plugins
 */
@Service
public class NotificationChannelRegistry {
    
    private final Map<String, NotificationChannelPlugin> channels;
    private final List<NotificationChannelPlugin> channelsByPriority;
    
    public NotificationChannelRegistry(List<NotificationChannelPlugin> channelPlugins) {
        this.channels = new HashMap<>();
        
        for (NotificationChannelPlugin plugin : channelPlugins) {
            if (plugin.isEnabled()) {
                channels.put(plugin.getChannelCode(), plugin);
                System.out.println(String.format(
                    "✅ Registered notification channel: %s (%s)",
                    plugin.getChannelCode(),
                    plugin.getChannelName()
                ));
            }
        }
        
        // Sort by priority for fallback
        this.channelsByPriority = channels.values().stream()
            .sorted(Comparator.comparing(NotificationChannelPlugin::getPriority))
            .collect(Collectors.toList());
        
        System.out.println("📧 Total active notification channels: " + channels.size());
    }
    
    /**
     * Get channel by code
     */
    public NotificationChannelPlugin getChannel(String channelCode) {
        NotificationChannelPlugin channel = channels.get(channelCode);
        if (channel == null) {
            throw new ChannelNotFoundException(
                "Notification channel not found: " + channelCode
            );
        }
        return channel;
    }
    
    /**
     * Get all channels
     */
    public Collection<NotificationChannelPlugin> getAllChannels() {
        return channels.values();
    }
    
    /**
     * Get channels by priority (for fallback)
     */
    public List<NotificationChannelPlugin> getChannelsByPriority() {
        return channelsByPriority;
    }
    
    /**
     * Get available channel codes
     */
    public List<String> getAvailableChannels() {
        return new ArrayList<>(channels.keySet());
    }
    
    /**
     * Send with fallback - tries channels in priority order
     */
    public NotificationChannelPlugin.NotificationResult sendWithFallback(
        String recipient,
        String subject,
        String message,
        Map<String, Object> metadata
    ) {
        for (NotificationChannelPlugin channel : channelsByPriority) {
            if (channel.isValidRecipient(recipient)) {
                NotificationChannelPlugin.NotificationResult result = 
                    channel.send(recipient, subject, message, metadata);
                
                if (result.isSuccess()) {
                    return result;
                }
            }
        }
        
        return new NotificationChannelPlugin.NotificationResult(
            false, null, "FAILED", "All channels failed"
        );
    }
    
    public static class ChannelNotFoundException extends RuntimeException {
        public ChannelNotFoundException(String message) {
            super(message);
        }
    }
}
