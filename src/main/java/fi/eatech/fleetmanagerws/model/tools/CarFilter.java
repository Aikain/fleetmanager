package fi.eatech.fleetmanagerws.model.tools;

/**
 * Helper class for car filtering
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
public class CarFilter {
    private Integer modelYearMin;
    private Integer modelYearMax;
    private String model;
    private String brand;

    public CarFilter() {}

    public CarFilter(Integer modelYearMin, Integer modelYearMax, String model, String brand) {
        this.modelYearMin = modelYearMin;
        this.modelYearMax = modelYearMax;
        this.model = model;
        this.brand = brand;
    }

    public Integer getModelYearMin() {
        return modelYearMin;
    }
    public Integer getModelYearMax() {
        return modelYearMax;
    }
    public String getModel() {
        return model;
    }
    public String getBrand() {
        return brand;
    }

    public void setModelYearMin(Integer modelYearMin) {
        this.modelYearMin = modelYearMin;
    }
    public void setModelYearMax(Integer modelYearMax) {
        this.modelYearMax = modelYearMax;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        String s = "";
        s += makeString(s, "modelYearMin", modelYearMin != null ? modelYearMin + "" : "");
        s += makeString(s, "modelYearMax", modelYearMax != null ? modelYearMax + "" : "");
        s += makeString(s, "model", model);
        s += makeString(s, "brand", brand);
        return s;
    }

    private String makeString(String old, String add, String value) {
        if (value == null || value.isEmpty()) return "";
        return (old.isEmpty() ? "" : ", ") + add + ": " + value;
    }
}
