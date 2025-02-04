package es.s2o.selenium.builder;

import es.s2o.selenium.domain.FlightDTO;

public final class FlightBuilder {
    private String origin;
    private String destination;
    private String departure;
    private String returnD;
    private String oneWayTrip;
    private String travelers;

    private FlightBuilder() {
    }

    public static FlightBuilder aFlight() {
        return new FlightBuilder();
    }

    public FlightBuilder withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public FlightBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public FlightBuilder withDeparture(String departure) {
        this.departure = departure;
        return this;
    }

    public FlightBuilder withReturnD(String returnD) {
        this.returnD = returnD;
        return this;
    }

    public FlightBuilder withOneWayTrip(String oneWayTrip) {
        this.oneWayTrip = oneWayTrip;
        return this;
    }

    public FlightBuilder withTravelers(String travelers) {
        this.travelers = travelers;
        return this;
    }

    public FlightDTO build() {
        FlightDTO flight = new FlightDTO();
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setDeparture(departure);
        flight.setReturnD(returnD);
        flight.setOneWayTrip(oneWayTrip);
        flight.setTravelers(travelers);
        return flight;
    }
}
