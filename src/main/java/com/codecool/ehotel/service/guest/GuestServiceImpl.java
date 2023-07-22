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
    public List<List<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guestsForDay, int numCycles) {
        // Shuffle the list of guests randomly
        Collections.shuffle(guestsForDay);

        // Initialize the breakfast cycles
        List<List<Guest>> breakfastCycles = new ArrayList<>();
        for (int i = 0; i < numCycles; i++) {
            breakfastCycles.add(new ArrayList<>());
        }

        // Shuffle the breakfast cycles to randomize the distribution
        Collections.shuffle(breakfastCycles);

        // Distribute guests into breakfast cycles
        for (Guest guest : guestsForDay) {
            int cycleIndex = new Random().nextInt(numCycles); // get the index randomly
            breakfastCycles.get(cycleIndex).add(guest); // Add the guest to the current cycle
        }

        return breakfastCycles;
    }
}