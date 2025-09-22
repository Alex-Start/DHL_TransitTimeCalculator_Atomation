package home.freight.tools;

import base.BaseTest;
import enums.Country;
import org.testng.annotations.Test;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import pages.home.freight.tools.TransitTimeTable;
import verifier.Verify;

import java.util.Arrays;

import static utils.Util.getNextBusinessDay;
import static verifier.StringVerifier.asString;

public class TransitTimeCalculatorTest extends BaseTest {

    @Test
    public void transitTimeCalculatorTest() {
        EuropeanRoadFreightTransitTimeCalculator calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();

        calculator
                .selectOriginCountry(Country.SE)
                .enterOriginPostcode("12586")
                .selectDestinationCountry(Country.SE)
                .enterDestinationPostcode("11450")
                .clickCalculate();

        Verify.forCalculator(calculator)
                .verifyOriginalPostcodeError(asString(null))
                .verifyDestinationPostcodeError(asString(null))
                .verifyGeneralError(asString(null))
        ;

        String expDate = getNextBusinessDay();
        TransitTimeTable expectedTTT = new TransitTimeTable();
        expectedTTT.addFeatures(Arrays.asList("Online Tracking", "eProof of Delivery online", "Delivery before 7.30 am PRE7", "Delivery before 10 am PRE10", "Delivery before noon PRE12"));
        expectedTTT.addProduct()
                .addProductName("DHL PAKET")
                .addDescription("B2B door-to-door parcel service")
                .addDeliveryDate(expDate)
                .addFeatureState("Online Tracking", "Available")
                .addFeatureState("eProof of Delivery online", "Available")
                .addFeatureState("Delivery before 7.30 am PRE7", "Available")
                .addFeatureState("Delivery before 10 am PRE10", "Available")
                .addFeatureState("Delivery before noon PRE12", "Available")
                .build();
        expectedTTT.addProduct()
                .addProductName("DHL PALL")
                .addDescription("B2B groupage on pallets")
                .addDeliveryDate(expDate)
                .addFeatureState("Online Tracking", "Available")
                .addFeatureState("eProof of Delivery online", "Available")
                .addFeatureState("Delivery before 7.30 am PRE7", "Available")
                .addFeatureState("Delivery before 10 am PRE10", "Available")
                .addFeatureState("Delivery before noon PRE12", "Available")
                .build();
        expectedTTT.addProduct()
                .addProductName("DHL STYCKE")
                .addDescription("B2B groupage cargo")
                .addDeliveryDate(expDate)
                .addFeatureState("Online Tracking", "Available")
                .addFeatureState("eProof of Delivery online", "Available")
                .addFeatureState("Delivery before 7.30 am PRE7", "Available")
                .addFeatureState("Delivery before 10 am PRE10", "Available")
                .addFeatureState("Delivery before noon PRE12", "Available")
                .build();
        expectedTTT.addProduct()
                .addProductName("DHL SERVICE POINT")
                .addDescription("B2C parcel service through Service Points")
                .addDeliveryDate(expDate)
                .addFeatureState("Online Tracking", "Available")
                .addFeatureState("eProof of Delivery online", "Not available")
                .addFeatureState("Delivery before 7.30 am PRE7", "Not available")
                .addFeatureState("Delivery before 10 am PRE10", "Not available")
                .addFeatureState("Delivery before noon PRE12", "Not available")
                .build();

        Verify.forTransitTimeResult(driver)
                .verifyTransitTimeIsDisplayed()
                .assertTablesEqual(expectedTTT);
    }
}
