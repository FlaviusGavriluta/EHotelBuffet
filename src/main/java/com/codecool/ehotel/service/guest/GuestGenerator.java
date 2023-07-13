package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuestGenerator {
    public static List<Guest> generateGuests(GuestService guestService, int[] guestsToExpect) {
        List<Guest> allGuests = new ArrayList<>();
        LocalDate seasonStart = LocalDate.of(2023, 6, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 9, 30);

        for (int guestTypeIndex = 0; guestTypeIndex < guestsToExpect.length; guestTypeIndex++) {
            GuestType guestType = GuestType.values()[guestTypeIndex];
            int expectedGuests = guestsToExpect[guestTypeIndex];
            int generatedGuests = 0;

            while (generatedGuests < expectedGuests) {
                Guest guest = guestService.generateRandomGuest(seasonStart, seasonEnd);
                if (guest.guestType() == guestType) {
                    allGuests.add(guest);
                    generatedGuests++;
                }
            }
        }
        return allGuests;
    }


    public static List<Guest> getGuestsForDay(List<Guest> allGuests, GuestService guestService, LocalDate targetDate) {
        return guestService.getGuestsForDay(allGuests, targetDate);
    }

    public static List<Guest> generateExpectedGuests(GuestService guestService, int[] guestsToExpect) {
        List<Guest> expectedGuests = new ArrayList<>();
        LocalDate seasonStart = LocalDate.of(2023, 6, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 9, 30);

        for (GuestType guestType : GuestType.values()) {
            int numberOfGuests = guestsToExpect[guestType.ordinal()];
            for (int i = 0; i < numberOfGuests; i++) {
                Guest guest = guestService.generateRandomGuestOfType(guestType, seasonStart, seasonEnd);
                expectedGuests.add(guest);
            }
        }

        return expectedGuests;
    }

}
