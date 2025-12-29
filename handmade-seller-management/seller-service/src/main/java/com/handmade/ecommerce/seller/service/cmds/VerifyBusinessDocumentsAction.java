import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.command.VerifyBusinessDocumentsPayload;

public class VerifyBusinessDocumentsAction extends AbstractSTMTransitionAction<SellerAccount, VerifyBusinessDocumentsPayload> {

    @Override
    public void doTransition(SellerAccount seller, VerifyBusinessDocumentsPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate verification result
        if (payload.isVerified()) {
            // Document verification successful
            seller.setUpdatedAt(java.time.LocalDateTime.now());
            
            // TODO: Store verification details
            // verificationService.storeDocumentVerification(seller, payload.getComments());
        } else {
            // Document verification failed
            // TODO: Request additional documents or clarification
            // notificationService.requestAdditionalDocuments(seller, payload.getComments());
        }
        
        // Activity completion is handled by STM framework automatically
    }
}
