package com.handmade.ecommerce.productorch;

import java.util.List;

public interface InvocationOrder {
    void addCommand(String commandId);
    List<String> getInvocationOrder();
}
