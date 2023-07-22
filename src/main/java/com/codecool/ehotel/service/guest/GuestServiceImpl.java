package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GuestServiceImpl implements GuestService {
    private List<Guest> guestList;

    public GuestServiceImpl(List<Guest> guestList) {
        this.guestList = guestList;
    }

    @Override
    public List<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return guestList.stream()
                .filter(guest -> guest.checkIn().isBefore(date.plusDays(1)) && guest.checkOut().isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<List<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guests) {
        int numCycles = 8; // Number of breakfast cycles per day
        List<List<Guest>> breakfastCycles = new ArrayList<>();

        // Initialize empty breakfast cycles
        for (int i = 0; i < numCycles; i++) {
            breakfastCycles.add(new ArrayList<>());
        }

        Collections.shuffle(guests);

        // Distribute guests into breakfast cycles
        int cycleIndex = 0;
        for (Guest guest : guests) {
            breakfastCycles.get(cycleIndex).add(guest);
            cycleIndex = (cycleIndex + 1) % numCycles; // Move to the next cycle
        }

        return breakfastCycles;
    }
}