package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.util.List;

public interface GuestService {
    Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd);

    List<Guest> getGuestsForDay(List<Guest> guests, LocalDate date);

    List<List<Guest>> splitGuestsIntoBreakfastGroups(List<Guest> guests);

    Guest generateRandomGuestOfType(GuestType guestType, LocalDate seasonStart, LocalDate seasonEnd);
}
