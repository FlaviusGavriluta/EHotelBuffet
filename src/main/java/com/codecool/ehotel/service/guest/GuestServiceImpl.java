package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GuestServiceImpl implements GuestService {
    private List<Guest> guestList;

    public GuestServiceImpl(List<Guest> guestList) {
        this.guestList = guestList;
    }

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Random random = new Random();
        int minStay = 1;
        int maxStay = Math.min((int) ChronoUnit.DAYS.between(seasonStart, seasonEnd) + 1, 7);

        String[] randomNames = {"John", "Emma", "Michael", "Sophia", "Daniel", "Olivia", "Levi", "Luke", "Jonathan", "Jayson", "Evan", "Elizabeth"};
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
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return guestList.stream()
                .filter(guest -> guest.checkIn().isBefore(date.plusDays(1)) && guest.checkOut().isAfter(date))
                .collect(Collectors.toSet());
    }
}
