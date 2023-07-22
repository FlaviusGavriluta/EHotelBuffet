package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import static java.lang.System.out;

import java.util.List;

public class GuestsDisplay {
    public static void displayGuests(List<Guest> guests) {
        out.println("Guests:");
        if(guests.isEmpty()) {
            out.println("No guests for this cycle.");
            out.println();
            return;
        }
        for (Guest guest : guests) {
            out.println(String.format("- %s (%s): Booked from %s to %s",
                    guest.name(), guest.guestType(), guest.checkIn(), guest.checkOut()));
        }
        out.println();
    }
}