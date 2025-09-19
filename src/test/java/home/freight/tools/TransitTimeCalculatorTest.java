package home.freight.tools;

import base.BaseTest;
import enums.Country;
import org.testng.annotations.Test;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import postcodes.PostcodeFactory;
import verifier.ErrorValidator;

public class TransitTimeCalculatorErrorMessageTest extends BaseTest {

    @Test
    public void transitTimeCalculatorErrorMessageTest() {
        EuropeanRoadFreightTransitTimeCalculator calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();

        calculator
                .selectOriginCountry(Country.SE)
                .enterOriginPostcode(PostcodeFactory.forCountry(Country.SE).generateValid())
                .selectDestinationCountry(Country.CZ)
                .enterDestinationPostcode(PostcodeFactory.forCountry(Country.CZ).generateValid())
                .clickCalculate();

        ErrorValidator.shouldBeEqualTo(calculator.getGeneralErrorMessage(), "Unfortunately the online tool is unable to retrieve data for the specified shipment. Please try again later or contact us for assistance.");

    }
}
