package verifier;

import org.testng.Assert;
import pages.home.freight.tools.EuropeanRoadFreightTransitTimeCalculator;
import utils.Report;

import static verifier.StringVerifier.asString;

public class CalculatorVerifier {
    private final Report logger = Report.getLogger(CalculatorVerifier.class);

    private final EuropeanRoadFreightTransitTimeCalculator calculator;

    public CalculatorVerifier(EuropeanRoadFreightTransitTimeCalculator calculator) {
        this.calculator = calculator;
    }

    public CalculatorVerifier verifyOriginalPostcodeError(String expected) {
        return verifyOriginalPostcodeError(asString(expected));
    }

    public CalculatorVerifier verifyOriginalPostcodeError(IVerifier expected) {
        String actual = calculator.getOriginPostcodeError();
        boolean result = expected.verify(actual);
        logger.info("verifyOriginalPostcodeError: Expected: {}, Actual: {}", expected, actual);
        Assert.assertTrue(result, "Expected error message not matched!");
        return this;
    }

    public CalculatorVerifier verifyDestinationPostcodeError(String expected) {
        return verifyDestinationPostcodeError(asString(expected));
    }

    public CalculatorVerifier verifyDestinationPostcodeError(IVerifier expected) {
        String actual = calculator.getDestinationPostcodeError();
        boolean result = expected.verify(actual);
        logger.info("verifyDestinationPostcodeError: Expected: {}, Actual: {}", expected, actual);
        Assert.assertTrue(result, "Expected error message not matched!");
        return this;
    }

    public CalculatorVerifier verifyGeneralError(String expected) {
        return verifyGeneralError(asString(expected));
    }

    public CalculatorVerifier verifyGeneralError(IVerifier expected) {
        String actual = calculator.getGeneralErrorMessage();
        boolean result = expected.verify(actual);
        logger.info("verifyGeneralError: Expected: {}, Actual: {}", expected, actual);
        Assert.assertTrue(result, "Expected error message not matched!");
        return this;
    }
}
