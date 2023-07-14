package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class GuestServiceImpl implements GuestService {
    private List<Guest> guestList;

    public GuestServiceImpl(List<Guest> guestList) {
        this.guestList = guestList;
    }

    @Override
    public List<List<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guests) {
        int numCycles = 8; // Number of breakfast cycles per day
        List<List<Guest>> breakfastCycles = new ArrayList<>();

        // Initialize empty breakfast cycles
        for (int i = 0; i < numCycles; i++) {
            breakfastCycles.add(new ArrayList<>());
        }

        // Shuffle the guests randomly
        Collections.shuffle(guests);

        // Distribute guests into breakfast cycles
        int cycleIndex = 0;
        for (Guest guest : guests) {
            breakfastCycles.get(cycleIndex).add(guest);
            cycleIndex = (cycleIndex + 1) % numCycles; // Move to the next cycle
        }

        return breakfastCycles;
    }


    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Random random = new Random();
        int minStay = 1;
        int maxStay = Math.min((int) ChronoUnit.DAYS.between(seasonStart, seasonEnd) + 1, 7);

        String[] randomNames = {
                "John Smith", "Emma Johnson", "Michael Brown", "Sophia Davis", "Daniel Wilson",
                "Olivia Taylor", "Levi Anderson", "Luke Martinez", "Jonathan Thomas",
                "Jayson Rodriguez", "Evan Garcia", "Elizabeth Martinez"
        };
        GuestType[] guestTypes = GuestType.values();

        String randomName = randomNames[random.nextInt(randomNames.length)];
        GuestType randomGuestType = guestTypes[random.nextInt(guestTypes.length)];

        long seasonLength = ChronoUnit.DAYS.between(seasonStart, seasonEnd) + 1;
        long randomStay = minStay + random.nextInt(maxStay - minStay + 1);
        LocalDate randomCheckIn = seasonStart.plusDays(random.nextInt((int) seasonLength - (int) randomStay + 1));
        LocalDate randomCheckOut = randomCheckIn.plusDays(randomStay);

        return new Guest(randomName, randomGuestType, randomCheckIn, randomCheckOut);
    }

    @Override
    public List<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return guestList.stream()
                .filter(guest -> guest.checkIn().isBefore(date.plusDays(1)) && guest.checkOut().isAfter(date))
                .collect(Collectors.toList());
    }
}
