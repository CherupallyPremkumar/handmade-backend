import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import org.chenile.workflow.api.StateEntityService;

public interface SellerManager extends StateEntityService<Seller> {
    void validate(CreateSellerRequest request);
}
