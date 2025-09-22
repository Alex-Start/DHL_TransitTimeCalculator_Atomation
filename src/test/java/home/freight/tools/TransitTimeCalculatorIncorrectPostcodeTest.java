package home.freight.tools;

import base.BaseTest;
import enums.Country;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import postcodes.PostcodeFactory;
import verifier.Verify;

import static pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator.ERROR_MESSAGE_CORRECT_POSTAL_CODE;
import static verifier.StringVerifier.asString;

public class TransitTimeCalculatorIncorrectPostcodeTest extends BaseTest {

    EuropeanRoadFreightTransitTimeCalculator calculator;

    @BeforeClass
    private void prerequisites() {
        calculator = new EuropeanRoadFreightTransitTimeCalculator(driver);
        calculator.open();
    }

    @DataProvider
    private Object[][] originDestinationCountry() {
        //Origin country, Origin postcode, Destination country, Destination postcode, origin is failed?, destination is failed?
        return new Object [][] {
                {Country.SE, "", Country.SE, PostcodeFactory.forCountry(Country.SE).generateValid(), true, false},
                {Country.SE, PostcodeFactory.forCountry(Country.SE).generateValid(), Country.SE, "", false, true},
                {Country.SE, "0", Country.SE, PostcodeFactory.forCountry(Country.SE).generateValid(), true, false},
                {Country.SE, PostcodeFactory.forCountry(Country.SE).generateValid(), Country.SE, "00000", false, true},
                {Country.SE, "88888", Country.SE, "asdfg", true, true},
                {Country.SE, "null", Country.SE, "+", true, true}, //TODO issue?
                {Country.SE, PostcodeFactory.forCountry(Country.SE).generateInvalid(), Country.SE, "-", true, true},
                {Country.SE, ".", Country.SE, PostcodeFactory.forCountry(Country.SE).generateInvalid(), true, true}
        };
    }

    @Test(dataProvider = "originDestinationCountry")
    public void transitTimeCalculatorIncorrectPostcodeTest(Country countryOrigin, String postcodeOrigin, Country countryDestination, String postcodeDestination, boolean originExp, boolean destinationExp) {

        calculator
                .selectOriginCountry(countryOrigin)
                .enterOriginPostcode(postcodeOrigin)
                .selectDestinationCountry(countryDestination)
                .enterDestinationPostcode(postcodeDestination)
                .clickCalculate();

        Verify.forCalculator(calculator)
                .verifyOriginalPostcodeError(originExp ? asString(ERROR_MESSAGE_CORRECT_POSTAL_CODE) : asString(null))
                .verifyDestinationPostcodeError(destinationExp ? asString(ERROR_MESSAGE_CORRECT_POSTAL_CODE) : asString(null))
                .verifyGeneralError(asString(null));

        Verify.forTransitTimeResult(driver)
                .verifyTransitTimeIsNotDisplayed();
    }
}
