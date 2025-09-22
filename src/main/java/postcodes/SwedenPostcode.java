package postcodes;

import java.util.List;

import static postcodes.PostcodeFileUtils.readPostcodesFromFile;

public class SwedenPostcode extends PostcodeBase {

    // https://en.wikipedia.org/wiki/Postal_codes_in_Sweden
    public final int MIN = 10000;
    public final int MAX = 98499;
    private static final List<String> postcodes = readPostcodesFromFile("postcodes_SE.txt");//not filled properly

    @Override
    public List<String> getValidPostcodes() {
        return postcodes;
    }

    @Override
    public int getMinValue() {
        return MIN;
    }

    @Override
    public int getMaxValue() {
        return MAX;
    }

    public String transform(int i) {
        return String.format("%05d", i);
    }
}
