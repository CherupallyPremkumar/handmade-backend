import com.handmade.ecommerce.seller.dto.command.CreateSellerRequest;
import com.handmade.ecommerce.seller.model.Seller;
import org.chenile.workflow.api.StateEntityService;

public interface SellerService extends StateEntityService<Seller> {
    void validate(CreateSellerRequest request);
}
