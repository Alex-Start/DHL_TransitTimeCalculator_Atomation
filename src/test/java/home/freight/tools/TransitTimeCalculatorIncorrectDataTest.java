package home.freight.tools;

import base.BaseTest;
import enums.Country;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import verifier.Verify;
import postcodes.PostcodeFactory;
import verifier.ErrorValidator;

import static pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator.ERROR_MESSAGE_CORRECT_POSTAL_CODE;
import static pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator.ERROR_MESSAGE_UNABLE_TO_RETRIEVE_DATA;
import static verifier.StringVerifier.asString;

public class TransitTimeCalculatorErrorMessageTest extends BaseTest {

    @DataProvider
    private Object[][] originDestinationCountry() {
        return new Object [][] {
                {Country.SE, Country.CZ},
                {Country.CZ, Country.SE}
        };
    }

    @Test(dataProvider = "originDestinationCountry")
    public void transitTimeCalculatorIncorrectDataTest(Country countryOrigin, Country countryDestination) {
        EuropeanRoadFreightTransitTimeCalculator calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();

        calculator
                .selectOriginCountry(countryOrigin)
                .enterOriginPostcode(PostcodeFactory.forCountry(countryOrigin).generateValid())
                .selectDestinationCountry(countryDestination)
                .enterDestinationPostcode(PostcodeFactory.forCountry(countryDestination).generateValid())
                .clickCalculate();

        Verify.forCalculator(calculator)
                .verifyGeneralError(asString(ERROR_MESSAGE_UNABLE_TO_RETRIEVE_DATA))
                .verifyOriginalPostcodeError(asString(null))
                .verifyDestinationPostcodeError(asString(null));
    }

    @Test
    public void transitTimeCalculatorEmptyPostcodeTest() {
        EuropeanRoadFreightTransitTimeCalculator calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();

        calculator
                .selectOriginCountry(Country.SE)
                .enterOriginPostcode("")
                .selectDestinationCountry(Country.SE)
                .enterDestinationPostcode(PostcodeFactory.forCountry(Country.SE).generateValid())
                .clickCalculate();

        Verify.forCalculator(calculator)
                .verifyOriginalPostcodeError(asString(ERROR_MESSAGE_CORRECT_POSTAL_CODE))
                .verifyDestinationPostcodeError(asString(null))
                .verifyGeneralError(asString(null));
    }
}
