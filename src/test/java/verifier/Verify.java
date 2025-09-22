package verifier;

import org.openqa.selenium.WebDriver;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import pages.home.freight.tools.TransitTimeResultValidator;

public class Verify {

    // verify calculator functionality
    public static CalculatorVerifier forCalculator(EuropeanRoadFreightTransitTimeCalculator calculator) {
        return new CalculatorVerifier(calculator);
    }

    public static TransitTimeVerifier forTransitTimeResult(WebDriver driver) {
        return new TransitTimeVerifier(new TransitTimeResultValidator(driver));
    }
}
