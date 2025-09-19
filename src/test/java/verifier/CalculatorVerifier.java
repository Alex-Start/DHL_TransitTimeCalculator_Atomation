package pages.home.freight.tools;

import verifier.IVerifier;

import static verifier.StringVerifier.asString;

public class CalculatorVerifier {
    private final EuropeanRoadFreightTransitTimeCalculator calculator;

    public CalculatorVerifier(EuropeanRoadFreightTransitTimeCalculator calculator) {
        this.calculator = calculator;
    }

    public boolean verifyOriginalPostcodeError(String expected) {
        return asString(expected).verify(calculator.getOriginPostcodeError());
    }

    public boolean verifyOriginalPostcodeError(IVerifier expected) {
        return expected.verify(calculator.getOriginPostcodeError());
    }

    public boolean verifyDestinationPostcodeError(String expected) {
        return asString(expected).verify(calculator.getDestinationPostcodeError());
    }

    public boolean verifyDestinationPostcodeError(IVerifier expected) {
        return expected.verify(calculator.getDestinationPostcodeError());
    }

    public boolean verifyGeneralError(String expected) {
        return asString(expected).verify(calculator.getGeneralErrorMessage());
    }

    public boolean verifyGeneralError(IVerifier expected) {
        return expected.verify(calculator.getGeneralErrorMessage());
    }
}
