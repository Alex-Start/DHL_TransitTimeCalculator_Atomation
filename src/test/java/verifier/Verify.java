package pages.home.freight.tools;

public class Verify {

    public static CalculatorVerifier forCalculator(EuropeanRoadFreightTransitTimeCalculator calculator) {
        return new CalculatorVerifier(calculator);
    }

}
