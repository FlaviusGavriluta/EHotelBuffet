package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GuestServiceImpl implements GuestService {
    private List<Guest> guestList;

    public GuestServiceImpl(List<Guest> guestList) {
        this.guestList = guestList;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        // Implementați generarea aleatorie a unui oaspete în intervalul specificat
        // și returnați obiectul Guest rezultat
        // Exemplu de implementare:
        // Guest randomGuest = ... // generați un obiect Guest aleator
        // return randomGuest;
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return guestList.stream()
                .filter(guest -> guest.checkIn().isBefore(date.plusDays(1)) && guest.checkOut().isAfter(date))
                .collect(Collectors.toSet());
    }
}
