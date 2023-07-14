package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface GuestService {

    Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd);

    List<Guest> getGuestsForDay(List<Guest> guests, LocalDate date);

    List<List<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guests);
}