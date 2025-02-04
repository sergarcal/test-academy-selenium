package es.s2o.selenium.interfaces;

import es.s2o.selenium.domain.FlightDTO;

import java.util.List;

public interface VuelingPageI {
    List<FlightDTO> getFlightsList();
}
