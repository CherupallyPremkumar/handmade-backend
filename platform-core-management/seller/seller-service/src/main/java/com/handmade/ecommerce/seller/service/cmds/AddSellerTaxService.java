package com.handmade.ecommerce.seller.service.cmds;

import com.handmade.ecommerce.seller.dto.TaxInfoRequest;
import com.handmade.ecommerce.seller.dto.TaxRegistrationRequest;
import com.handmade.ecommerce.seller.dto.command.AddSellerTaxPayload;
import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.model.TaxInfo;
import com.handmade.ecommerce.seller.model.TaxRegistration;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the "addTax" event for a Seller.
 * Marks existing tax info inactive and adds new ones.
 */
public class AddSellerTaxService extends AbstractSTMTransitionAction<Seller, AddSellerTaxPayload> {

	@Override
	public void transitionTo(Seller seller,
							 AddSellerTaxPayload payload,
							 State startState,
							 String eventId,
							 State endState,
							 STMInternalTransitionInvoker<?> stm,
							 Transition transition) throws Exception {

		if (payload == null || payload.getTaxInfoRequests() == null || payload.getTaxInfoRequests().isEmpty()) {
			seller.addActivity("addTax", "No tax info provided, skipping addition.");
			return;
		}

		// ✅ Step 1: Mark all existing tax records as inactive
		for (TaxInfo existingTax : seller.getTaxInfos()) {
			existingTax.setActive(false);
		}

		// ✅ Step 2: Create new active tax records from the payload
		List<TaxInfo> newTaxInfos = new ArrayList<>();

		for (TaxInfoRequest req : payload.getTaxInfoRequests()) {
			TaxInfo taxInfo = new TaxInfo();
			taxInfo.setSellerId(seller.getId());
			taxInfo.setCompanyName(req.getCompanyName());
			taxInfo.setCompanyType(req.getCompanyType());
			taxInfo.setPanNumber(req.getPanNumber());
			taxInfo.setGstNumber(req.getGstNumber());
			taxInfo.setVatNumber(req.getVatNumber());
			taxInfo.setTaxCountry(req.getTaxCountry());
			taxInfo.setTaxState(req.getTaxState());
			taxInfo.setActive(true);
			taxInfo.setCreatedAt(LocalDateTime.now());
			taxInfo.setUpdatedAt(LocalDateTime.now());

			// ✅ Handle nested Tax Registrations
			if (req.getRegistrations() != null && !req.getRegistrations().isEmpty()) {
				List<TaxRegistration> registrations = new ArrayList<>();
				for (TaxRegistrationRequest regReq : req.getRegistrations()) {
					TaxRegistration registration = new TaxRegistration();
					registration.setCountry(regReq.getCountry());
					registration.setTaxIdNumber(regReq.getTaxIdNumber());
					registration.setStatus(regReq.getStatus());
					registration.setTaxInfo(taxInfo); // link back
					registrations.add(registration);
				}
				taxInfo.setRegistrations(registrations);
			}

			newTaxInfos.add(taxInfo);
		}

		// ✅ Step 3: Add all new tax info records to the seller
		seller.getTaxInfos().addAll(newTaxInfos);

		// ✅ Step 4: Log seller activity
		seller.addActivity("addTax",
				"Added " + newTaxInfos.size() + " new tax record(s). Old ones marked inactive.");
	}
}
