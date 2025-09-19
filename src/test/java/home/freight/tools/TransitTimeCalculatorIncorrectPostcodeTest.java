package home.freight.tools;

import base.BaseTest;
import enums.Country;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import postcodes.PostcodeFactory;
import verifier.Verify;

import static pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator.ERROR_MESSAGE_CORRECT_POSTAL_CODE;
import static verifier.StringVerifier.asString;

public class TransitTimeCalculatorEmptyPostcodeTest extends BaseTest {

    @DataProvider
    private Object[][] originDestinationCountry() {
        return new Object [][] {
                {Country.SE, "", Country.SE, PostcodeFactory.forCountry(Country.SE).generateValid()},
                {Country.SE, PostcodeFactory.forCountry(Country.SE).generateValid(), Country.SE, ""}
        };
    }

    @Test(dataProvider = "originDestinationCountry")
    public void transitTimeCalculatorEmptyPostcodeTest(Country countryOrigin, String postcodeOrigin, Country countryDestination, String postcodeDestination) {
        EuropeanRoadFreightTransitTimeCalculator calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();

        calculator
                .selectOriginCountry(countryOrigin)
                .enterOriginPostcode(postcodeOrigin)
                .selectDestinationCountry(countryDestination)
                .enterDestinationPostcode(postcodeDestination)
                .clickCalculate();

        Verify.forCalculator(calculator)
                .verifyOriginalPostcodeError(postcodeOrigin.isEmpty() ? asString(ERROR_MESSAGE_CORRECT_POSTAL_CODE) : asString(null))
                .verifyDestinationPostcodeError(postcodeDestination.isEmpty() ? asString(ERROR_MESSAGE_CORRECT_POSTAL_CODE) : asString(null))
                .verifyGeneralError(asString(null));
    }
}
