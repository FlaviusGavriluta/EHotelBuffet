package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.guest.GuestService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GuestServiceImpl implements GuestService {
    private final GuestService guestService;

    public GuestServiceImpl(GuestService guestService) {
        this.guestService = guestService;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        return null;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        Set<Guest> guestsForDay = new HashSet<>();
        for (Guest guest : guests) {
            if (date.isAfter(guest.checkIn()) && date.isBefore(guest.checkOut())) {
                guestsForDay.add(guest);
            }
        }
        return guestsForDay;
    }
}
