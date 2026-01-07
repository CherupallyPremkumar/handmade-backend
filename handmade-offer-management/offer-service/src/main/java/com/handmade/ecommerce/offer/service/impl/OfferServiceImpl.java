import com.handmade.ecommerce.offer.service.store.OfferEntityStore;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl extends StateEntityServiceImpl<Offer> implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private InventoryService inventoryService;

    public OfferServiceImpl(STM<Offer> stm, STMActionsInfoProvider stmActionsInfoProvider,
                           OfferEntityStore entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        Offer offer = mapToEntity(offerDto);
        offer = offerRepository.save(offer);
        return mapToDto(offer);
    }

    @Override
    public OfferDto updateOffer(String offerCode, OfferDto offerDto) {
        Offer offer = offerRepository.findByOfferCode(offerCode)
                .orElseThrow(() -> new RuntimeException("Offer not found: " + offerCode));
        
        offer.setPrice(offerDto.getPrice());
        // State is now managed by STM transitions, not direct boolean updates.
        
        offer = offerRepository.save(offer);
        return mapToDto(offer);
    }

    @Override
    public OfferDto getOffer(String offerCode) {
        Offer offer = offerRepository.findByOfferCode(offerCode)
                .orElseThrow(() -> new RuntimeException("Offer not found: " + offerCode));
        return mapToDto(offer);
    }

    @Override
    public OfferDto getOffer(String variantId, String sellerId, String regionId) {
        Offer offer = offerRepository.findByVariantIdAndSellerIdAndRegionId(variantId, sellerId, regionId)
                .orElseThrow(() -> new RuntimeException("Offer not found for variant: " + variantId + " in region: " + regionId));
        return mapToDto(offer);
    }

    @Override
    public List<OfferDto> getOffersForVariant(String variantId) {
        return offerRepository.findByVariantId(variantId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> getOffersBySeller(String sellerId) {
        return offerRepository.findBySellerId(sellerId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private Offer mapToEntity(OfferDto dto) {
        Offer entity = new Offer();
        entity.setOfferCode(dto.getOfferCode());
        entity.setProductId(dto.getProductId());
        entity.setVariantId(dto.getVariantId());
        entity.setSellerId(dto.getSellerId());
        entity.setRegionId(dto.getRegionId());
        entity.setPrice(dto.getPrice());
        entity.setCurrency(dto.getCurrency());
        return entity;
    }

    private OfferDto mapToDto(Offer entity) {
        OfferDto dto = new OfferDto();
        dto.setOfferId(entity.getId());
        dto.setOfferCode(entity.getOfferCode());
        dto.setProductId(entity.getProductId());
        dto.setVariantId(entity.getVariantId());
        dto.setSellerId(entity.getSellerId());
        dto.setRegionId(entity.getRegionId());
        dto.setPrice(entity.getPrice());
        dto.setCurrency(entity.getCurrency());
        dto.setState(entity.getCurrentState());
        dto.setActivationReason(entity.getActivationReason());
        dto.setSuspensionReason(entity.getSuspensionReason());
        return dto;
    }
}
