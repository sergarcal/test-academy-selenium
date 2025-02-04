package es.s2o.selenium.services;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class OldVuelingPageService extends BaseVuelingPageService {
    public static String parseNumberTravelers(String numberTravelersContent) {
        String[] matches = Pattern.compile("[0-9]")
                .matcher(numberTravelersContent)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);

        return matches[0];
    }
}
