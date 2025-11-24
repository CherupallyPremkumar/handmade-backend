package com.handmade.ecommerce.productorch.bdd;


import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.command.CreateProductCommand;
import com.handmade.ecommerce.command.CreateVariantCommand;
import com.handmade.ecommerce.productorch.service.ProductContext;
import com.handmade.ecommerce.productorch.service.ProductEntryPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import com.handmade.ecommerce.productorch.SpringTestConfig;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
    * This "steps" file's purpose is to hook up the SpringConfig to the test case.
    * It does not contain any methods currently but can be used for writing your own custom BDD steps
    * if required. In most cases people don't need additional steps since cucumber-utils provides for
    * most of the steps. <br/>
    * This class requires a dummy method to keep Cucumber from erring out. (Cucumber needs at least
    * one step in a steps file)<br/>
*/
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = SpringTestConfig.class)
@ActiveProfiles("unittest")
@RunWith(SpringRunner.class)
public class CukesSteps {

	@Autowired
	private ProductEntryPoint productEntryPoint;

	@Test
	public void testInterception() throws Exception {
		// Create the ProductContext
		ProductContext productorch = new ProductContext();

		CreateProductCommand createProductCommand = new CreateProductCommand();
		createProductCommand.setName("myProduct");

		CreatePriceCommand priceCommand = new CreatePriceCommand();
		priceCommand.setCurrency("US");

		CreateVariantCommand createVariantCommand = new CreateVariantCommand();
		createVariantCommand.setPrices(new ArrayList<>(List.of(priceCommand)));

		createProductCommand.setVariants(new ArrayList<>(List.of(createVariantCommand)));

		productorch.setRequestProduct(createProductCommand);
		productorch.put("myRoute", "route1");

		productEntryPoint.execute(productorch);

		List<String> expectedCommands = List.of(
				"validateProductService",
				"createProductService",
				"createVariantService"
		);

		for (String cmd : expectedCommands) {
			assertTrue("Expected command not executed: " + cmd,
					productorch.commandInvocationOrder.contains(cmd));
		}
		System.out.println("Command invocation order: " + productorch.commandInvocationOrder);
	}
}
