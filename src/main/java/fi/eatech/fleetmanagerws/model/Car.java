package fi.eatech.fleetmanagerws.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.eatech.fleetmanagerws.model.tools.Tools;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Car entity
 *
 * @author Ville Nupponen
 * @since 1.0.0
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car {
    @Id
    @Column(length = 16)
    private String registrationNumber;
    @Column(length = 32)
    private String brand;
    @Column(length = 32)
    private String model;
    private Short modelYear;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date inspectionDate;
    private Double engineSize;                  // l^3
    private Double enginePower;                 // kW

    public Car() {}

    public Car(String registrationNumber, String brand, String model, Short modelYear, Date inspectionDate, Double engineSize, Double enginePower) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.inspectionDate = inspectionDate;
        this.engineSize = engineSize;
        this.enginePower = enginePower;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public Short getModelYear() {
        return modelYear;
    }
    public Date getInspectionDate() {
        return inspectionDate;
    }
    public Double getEngineSize() {
        return engineSize;
    }
    public Double getEnginePower() {
        return enginePower;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setModelYear(Short modelYear) {
        this.modelYear = modelYear;
    }
    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
    public void setEngineSize(Double engineSize) {
        this.engineSize = engineSize;
    }
    public void setEnginePower(Double enginePower) {
        this.enginePower = enginePower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(registrationNumber, car.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }

    @Override
    public String toString() {
        return "Car{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", modelYear=" + modelYear +
                ", inspectionDate=" + Tools.formatDate(inspectionDate, "yyyy-MM-dd") +
                ", engineSize=" + engineSize +
                ", enginePower=" + enginePower +
                '}';
    }
}