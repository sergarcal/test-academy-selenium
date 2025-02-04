package es.s2o.selenium.services;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class NewVuelingPageService extends BaseVuelingPageService {
    public static String parseNumberTravelers(String numberTravelersContent) {
        int totalTravelers = 0;

        String[] matches = Pattern.compile("[0-9]")
                .matcher(numberTravelersContent)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);

        for (String match : matches) {
            totalTravelers += Integer.parseInt(match);
        }

        return String.valueOf(totalTravelers);
    }
}
