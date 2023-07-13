package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuestGenerator {
    public static List<Guest> generateGuests(GuestService guestService, int number, LocalDate seasonStart, LocalDate seasonEnd) {
        List<Guest> allGuests = new ArrayList<>();

        while (allGuests.size() < number) {
            allGuests.add(guestService.generateRandomGuest(seasonStart, seasonEnd));
        }
        return allGuests;
    }

    public static List<Guest> getGuestsForDay(List<Guest> allGuests, GuestService guestService, LocalDate targetDate) {
        return guestService.getGuestsForDay(allGuests, targetDate);
    }
}
