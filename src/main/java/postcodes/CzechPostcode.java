package postcodes;

import java.util.ArrayList;
import java.util.List;

import static postcodes.PostcodeFileUtils.readPostcodesFromFile;

public class CzechPostcode extends PostcodeBase {
    // https://en.wikipedia.org/wiki/ISO_3166-2:CZ
    // https://www.psc.cz/
    public final int MIN = 10000;
    public final int MAX = 79862;
    // TODO: add gaps of incorrect numbers
    private final static List<String> gapsPostcodeList = new ArrayList<>();

    private static final List<String> postcodes = readPostcodesFromFile("postcodes_CZ.txt");

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
