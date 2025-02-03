package es.s2o.selenium.domain;


/**
 * Created by sacrists on 26.02.17.
 */
public class VuelingDTO {

    private String origin;
    private String destination;
    private String departure;
    private String returnD;
    private String oneWayTrip;
    private String travelers;

    public VuelingDTO() {

    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getReturnD() {
        return returnD;
    }

    public void setReturnD(String returnD) {
        this.returnD = returnD;
    }

    public String getOneWayTrip() {
        return oneWayTrip;
    }

    public void setOneWayTrip(String oneWayTrip) {
        this.oneWayTrip = oneWayTrip;
    }

    public String getTravelers() {
        return travelers;
    }

    public void setTravelers(String travelers) {
        this.travelers = travelers;
    }

    @Override
    public String toString() {
        return "VuelingDTO{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departure='" + departure + '\'' +
                ", returnD='" + returnD + '\'' +
                ", oneWayTrip='" + oneWayTrip + '\'' +
                ", travelers='" + travelers + '\'' +
                '}';
    }
}
