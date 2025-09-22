package home.freight.tools;

import base.BaseTest;
import enums.Country;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import verifier.Verify;
import postcodes.PostcodeFactory;

import static pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator.ERROR_MESSAGE_UNABLE_TO_RETRIEVE_DATA;
import static verifier.StringVerifier.asString;

public class TransitTimeCalculatorIncorrectDataTest extends BaseTest {

    @DataProvider
    private Object[][] originDestinationCountry() {
        return new Object [][] {
                {Country.SE, PostcodeFactory.forCountry(Country.SE).generateInvalid(), Country.CZ, PostcodeFactory.forCountry(Country.CZ).generateInvalid()},
                {Country.CZ, PostcodeFactory.forCountry(Country.CZ).generateInvalid(), Country.SE, PostcodeFactory.forCountry(Country.SE).generateInvalid()}
        };
    }

    @Test(dataProvider = "originDestinationCountry")
    public void transitTimeCalculatorIncorrectDataTest(Country countryOrigin, String postcodeOrigin, Country countryDestination, String postcodeDestination) {
        EuropeanRoadFreightTransitTimeCalculator calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();

        calculator
                .selectOriginCountry(countryOrigin)
                .enterOriginPostcode(postcodeOrigin)
                .selectDestinationCountry(countryDestination)
                .enterDestinationPostcode(postcodeDestination)
                .clickCalculate();

        Verify.forCalculator(calculator)
                .verifyGeneralError(asString(ERROR_MESSAGE_UNABLE_TO_RETRIEVE_DATA))
                .verifyOriginalPostcodeError(asString(null))
                .verifyDestinationPostcodeError(asString(null));

        Verify.forTransitTimeResult(driver)
                .verifyTransitTimeIsNotDisplayed();
    }
}
