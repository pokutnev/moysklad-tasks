package romashkaco.moitovary.entity;

public enum PriceOperation {
    GREATER(">"),
    LESS("<"),
    EQUAL("=");

    private String value;

    public String getValue() {
        return value;
    }

    PriceOperation(String value) {
        this.value = value;
    }

    public static PriceOperation fromString(String value){
        for (PriceOperation priceOperation : PriceOperation.values()) {
            if (priceOperation.getValue().equalsIgnoreCase(value)) {
                return priceOperation;
            }
        }
        return null;
    }
}
