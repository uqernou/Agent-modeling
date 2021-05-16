package enums;

public enum StanE {
    S("Ignorant"),
    I("Spreader"),
    R("Stifler");

    private String nazwa;

    StanE(String nazwa) {
        this.nazwa = nazwa;
    }
    public String getNazwa() {
        return this.nazwa;
    }

    public static StanE fromValue(String v) {
        for (StanE c : StanE.values()) {
            if (c.getNazwa().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
