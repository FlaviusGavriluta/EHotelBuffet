package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.List;

public interface GuestService {
    List<Guest> getGuestsForDay(List<Guest> guests, LocalDate date);

    List<List<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guests);
}