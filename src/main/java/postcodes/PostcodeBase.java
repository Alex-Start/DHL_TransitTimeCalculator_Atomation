package postcodes;

import java.util.*;

public abstract class PostcodeBase implements PostcodeGenerator {
    public final int MIN = 10000;
    public final int MAX = 99999;
    private static final Random random = new Random();

    public int getMinValue() {
        return MIN;
    }

    public int getMaxValue() {
        return MAX;
    }

    public abstract List<String> getValidPostcodes();

    public abstract String transform(int i);

    public String generateValid() {
        return getValidPostcodes().get(random.nextInt(getValidPostcodes().size()));
    }

    public String generateInvalid() {
        List<String> missing = findMissingPostcodes();
        return missing.get(random.nextInt(missing.size()));
    }

    public boolean isValid(String postcode) {
        if (postcode == null) return false;
        String clean = postcode.replaceAll("\\s+", "");
        if (!clean.matches("\\d{5}")) return false;
        return getValidPostcodes().contains(clean);
    }

    public List<String> findMissingPostcodes() {
        return findMissingPostcodes(getValidPostcodes(), getMinValue(), getMaxValue());
    }

    public List<String> findMissingPostcodes(List<String> postcodes, int min, int max) {
        Set<String> existing = new HashSet<>(postcodes);//not ordered
        List<String> missing = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            String postcode = transform(i);
            if (!existing.contains(postcode)) {
                missing.add(postcode);
            }
        }
        return missing;
    }
}
