package postcodes;

import enums.Country;

public class PostcodeFactory {
    public static PostcodeGenerator forCountry(Country country) {
        return switch (country) {
            case SE -> new SwedenPostcode();
            case CZ -> new CzechPostcode();

            // add more
            default -> throw new IllegalArgumentException("Unsupported country: " + country);
        };
    }
}
