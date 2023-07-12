package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuestGenerator {
    public static List<Guest> generateGuests(GuestService guestService) {
        List<Guest> allGuests = new ArrayList<>();
        LocalDate seasonStart = LocalDate.of(2023, 6, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 9, 30);

        for (int i = 0; i < 15; i++) {
            Guest guest = guestService.generateRandomGuest(seasonStart, seasonEnd);
            if (!allGuests.contains(guest)) {
                allGuests.add(guest);
            } else {
                i--;
            }
        }
        return allGuests;
    }

    public static List<Guest> getGuestsForDay(List<Guest> allGuests, GuestService guestService, LocalDate targetDate) {
        return guestService.getGuestsForDay(allGuests, targetDate);
    }
}
