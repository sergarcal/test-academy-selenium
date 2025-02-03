Feature: Booking a flight

  Narrative:
  In order to book flights
  As a client of Vueling
  I want to be able to enter the travel specifications

  Scenario: Book a flight
    Given I'm in the home page
    When I specify the journey details:
      | origin | destination | departure   | returnD     | oneWayTrip | travelers |
      | MAD    | BCN         | 01/06/2025  |             | true       | 1         |
    Then I should see available flights matching my criteria
