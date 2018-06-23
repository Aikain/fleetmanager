package fi.eatech.fleetmanagerws.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * .
 *
 * @author Ville Nupponen
 * @since .
 */
@Entity
public class Car {
    @Id
    private String registrationNumber;
    private String brand;
    private String model;
    private short modelYear;
    private Date inspectionDate;
    private double engineSize;                  // l^3
    private double enginePower;                 // kW

    public Car() {}

    public Car(String registrationNumber, String brand, String model, short modelYear, Date inspectionDate, double engineSize, double enginePower) {
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
    public short getModelYear() {
        return modelYear;
    }
    public Date getInspectionDate() {
        return inspectionDate;
    }
    public double getEngineSize() {
        return engineSize;
    }
    public double getEnginePower() {
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
    public void setModelYear(short modelYear) {
        this.modelYear = modelYear;
    }
    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }
    public void setEnginePower(double enginePower) {
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
                ", inspectionDate=" + inspectionDate +
                ", engineSize=" + engineSize +
                ", enginePower=" + enginePower +
                '}';
    }
}