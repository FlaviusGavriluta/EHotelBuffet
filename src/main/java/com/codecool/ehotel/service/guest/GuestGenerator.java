package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuestGenerator {
    private static final String[] RANDOM_FIRST_NAMES = {
            "John", "Emma", "Michael", "Sophia", "Daniel", "Olivia", "Levi", "Luke",
            "Jonathan", "Jayson", "Evan", "Elizabeth"
    };

    private static final String[] RANDOM_LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Davis", "Wilson", "Taylor", "Anderson",
            "Martinez", "Thomas", "Rodriguez", "Garcia", "Martinez"
    };
    private static final GuestType[] GUEST_TYPES = GuestType.values();

    public static Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Random random = new Random();
        int minStay = 1;
        int maxStay = Math.min((int) ChronoUnit.DAYS.between(seasonStart, seasonEnd) + 1, 7);

        String randomFirstName = RANDOM_FIRST_NAMES[random.nextInt(RANDOM_FIRST_NAMES.length)];
        String randomLastName = RANDOM_LAST_NAMES[random.nextInt(RANDOM_LAST_NAMES.length)];
        String randomName = randomFirstName + " " + randomLastName;
        GuestType randomGuestType = GUEST_TYPES[random.nextInt(GUEST_TYPES.length)];

        long seasonLength = ChronoUnit.DAYS.between(seasonStart, seasonEnd) + 1;
        long randomStay = minStay + random.nextInt(maxStay - minStay + 1);

        LocalDate randomCheckIn = seasonStart.plusDays(random.nextInt((int) seasonLength - (int) randomStay + 1));
        LocalDate randomCheckOut = randomCheckIn.plusDays(randomStay);

        return new Guest(randomName, randomGuestType, randomCheckIn, randomCheckOut);
    }

    public static List<Guest> generateRandomGuestsList(int numGuests, LocalDate seasonStart, LocalDate seasonEnd) {
        List<Guest> randomGuests = new ArrayList<>();
        for (int i = 0; i < numGuests; i++) {
            Guest randomGuest = generateRandomGuest(seasonStart, seasonEnd);
            randomGuests.add(randomGuest);
        }

        return randomGuests;
    }
}