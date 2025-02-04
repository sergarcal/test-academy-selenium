package es.s2o.selenium.services;

import es.s2o.selenium.builder.FlightBuilder;
import es.s2o.selenium.domain.FlightDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class BaseVuelingPageService {
    public static String parseFlightType(String flightTypeContent) {
        boolean oneWayTrip = !flightTypeContent.toLowerCase().contains("vuelta");

        return String.valueOf(oneWayTrip);
    }

    public static String parseSelectedDate(String selectedDateContent) {
        String selectedDate = selectedDateContent + " " + Calendar.getInstance().get(Calendar.YEAR);
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("EEE d MMM yyyy", new Locale("es", "ES"));
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String formatedDate = "";
        try {
            Date date = selectedDateFormat.parse(selectedDate);
            formatedDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }

    public static FlightDTO mapFlight(Map<String, String> rowMap) {
        return FlightBuilder
                .aFlight()
                .withOrigin(rowMap.get("origin"))
                .withDestination(rowMap.get("destination"))
                .withDeparture(rowMap.get("departure"))
                .withReturnD(rowMap.get("returnD"))
                .withOneWayTrip(rowMap.get("oneWayTrip"))
                .withTravelers(rowMap.get("travelers"))
                .build();
    }
}
