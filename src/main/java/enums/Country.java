package enums;

public enum Country {
    SE("SE", "Sweden"),
    AT("AT", "Austria"),
    BE("BE", "Belgium"),
    BG("BG", "Bulgaria"),
    HR("HR", "Croatia"),
    CZ("CZ", "Czech Republic"),
    DK("DK", "Denmark"),
    EE("EE", "Estonia"),
    FI("FI", "Finland"),
    FR("FR", "France"),
    DE("DE", "Germany"),
    GB("GB", "Great Britain"),
    GR("GR", "Greece"),
    HU("HU", "Hungary"),
    IE("IE", "Ireland"),
    IT("IT", "Italy"),
    LV("LV", "Latvia"),
    LT("LT", "Lithuania"),
    LU("LU", "Luxembourg"),
    MK("MK", "Macedonia"),
    NL("NL", "Netherlands"),
    NO("NO", "Norway"),
    PL("PL", "Poland"),
    PT("PT", "Portugal"),
    RO("RO", "Romania"),
    RS("RS", "Serbia"),
    SK("SK", "Slovak Republic"),
    SI("SI", "Slovenia"),
    ES("ES", "Spain"),
    CH("CH", "Switzerland"),
    TR("TR", "Turkey");

    private final String code;
    private final String name;

    Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // Utility: find enum by code
    public static Country fromCode(String code) {
        for (Country c : values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid country code: " + code);
    }

    // Utility: find enum by name
    public static Country fromName(String name) {
        for (Country c : values()) {
            if (c.name.equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid country name: " + name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
