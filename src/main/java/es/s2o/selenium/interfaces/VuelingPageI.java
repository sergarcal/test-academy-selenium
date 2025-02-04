package es.s2o.selenium.interfaces;

import es.s2o.selenium.domain.VuelingDTO;

import java.util.List;

public interface VuelingPageI {
    List<VuelingDTO> getFlightsList();
}
