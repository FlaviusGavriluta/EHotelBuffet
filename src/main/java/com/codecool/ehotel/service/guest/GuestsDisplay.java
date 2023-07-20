package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.util.List;

public class GuestsDisplay {
    public static void displayGuests(List<Guest> guests) {
        System.out.println("Guests:");
        for (Guest guest : guests) {
            String guestInfo = String.format("- %s (%s): Booked from %s to %s",
                    guest.name(), guest.guestType(), guest.checkIn(), guest.checkOut());
            System.out.println(guestInfo);
        }
        System.out.println();
    }
}
