package verifier;

import org.testng.Assert;
import pages.home.freight.tools.TransitTimeResultValidator;
import pages.home.freight.tools.TransitTimeTable;
import utils.Report;

import static org.testng.Assert.assertEquals;

public class TransitTimeVerifier {
    private final Report logger = Report.getLogger(TransitTimeVerifier.class);

    private final TransitTimeResultValidator transitTimeResultValidator;

    public TransitTimeVerifier(TransitTimeResultValidator transitTimeResultValidator) {
        this.transitTimeResultValidator = transitTimeResultValidator;
    }

    public TransitTimeVerifier verifyTransitTimeIsDisplayed(){
        logger.info("verifyTransitTime is displayed");
        Assert.assertTrue(transitTimeResultValidator.isTransitTimeResultDisplayed(), "Transit Time Result is not displayed");
        return this;
    }

    public TransitTimeVerifier verifyTransitTimeIsNotDisplayed(){
        logger.info("verifyTransitTime is not displayed");
        Assert.assertFalse(transitTimeResultValidator.isTransitTimeResultDisplayed(), "Transit Time Result is displayed");
        return this;
    }

    public void assertTablesEqual(TransitTimeTable expected) {
        TransitTimeTable actual = transitTimeResultValidator.getTransitTimeTable();
        // Compare features
        assertEquals(actual.getFeatures(), expected.getFeatures(), "Features mismatch");

        // Compare number of products
        assertEquals(actual.getProducts().size(), expected.getProducts().size(), "Number of products mismatch.\nExpected: "+ expected.getProducts() +"\nActual:"+actual.getProducts());

        // Compare products one by one
        for (int i = 0; i < expected.getProducts().size(); i++) {
            TransitTimeTable.Product expProduct = expected.getProducts().get(i);
            TransitTimeTable.Product actProduct = actual.getProducts().get(i);

            assertEquals(actProduct.getName(), expProduct.getName(), "Product name mismatch at index " + i);
            assertEquals(actProduct.getDescription(), expProduct.getDescription(), "Product description mismatch for " + expProduct.getName());
            assertEquals(actProduct.getDeliveryDate(), expProduct.getDeliveryDate(), "Product delivery date mismatch for " + expProduct.getName());

            assertEquals(
                    actProduct.getFeatureStates(),
                    expProduct.getFeatureStates(),
                    "Feature states mismatch at product: " + expProduct.getName()
                            +"\nExpected: "+ expProduct.getFeatureStates()
                            +"\nActual: "+ actProduct.getFeatureStates()
            );
        }
    }
}
