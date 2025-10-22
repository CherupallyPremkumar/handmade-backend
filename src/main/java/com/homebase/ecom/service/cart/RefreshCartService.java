package com.homebase.ecom.service.cart;

import com.homebase.ecom.constants.HmHeaderUtils;
import com.homebase.ecom.constants.QueryConstants;
import com.homebase.ecom.domain.Cart;
import com.homebase.ecom.mybatis.cart.CartItemsDto;
import org.chenile.query.model.ResponseRow;
import org.chenile.query.model.SearchRequest;
import org.chenile.query.model.SearchResponse;
import org.chenile.query.service.SearchService;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.action.STMTransitionAction;
import org.chenile.stm.model.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.chenile.core.context.ContextContainer.CONTEXT_CONTAINER;


@Service
public class RefreshCartService implements STMTransitionAction<Cart> {

    @Autowired
    SearchService searchService;

    @Override
    public void doTransition(Cart cart, Object transitionParam, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        SearchResponse searchResponse = searchService.search(buildActiveCartItemsSearchRequest(cart.getId()));
        List<ResponseRow> cartItems=  searchResponse.getList();


        // Recalculate totals
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalOriginalAmount = BigDecimal.ZERO;

        for (ResponseRow responseRow : cartItems) {
            CartItemsDto item = (CartItemsDto) responseRow.getRow();
            BigDecimal price = item.getSalePrice() != null ? item.getSalePrice() : item.getSnapshotPrice();
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));

            if (item.getBasePrice() != null) {
                totalOriginalAmount = totalOriginalAmount.add(item.getBasePrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        cart.setTotalAmount(totalAmount);
        cart.setTransactionAmount(totalOriginalAmount);
        cart.setTotalItems(cartItems.size());
    }

    private SearchRequest<Map<String, Object>> buildActiveCartItemsSearchRequest(String cartId) {
        SearchRequest<Map<String, Object>> searchRequest = new SearchRequest<>();
        Map<String, Object> filters = new HashMap<>();
        filters.put("cart_id", cartId);
        filters.put("sub_tenant_id", HmHeaderUtils.getSubTenantIdKey(CONTEXT_CONTAINER.getContext()));
        filters.put("status_not_in", List.of("REMOVED")); // exclude removed items
        searchRequest.setFilters(filters);
        searchRequest.setQueryName(QueryConstants.FIND_ACTIVE_CART_ITEMS_BY_CART_ID);
        return searchRequest;
    }
}
