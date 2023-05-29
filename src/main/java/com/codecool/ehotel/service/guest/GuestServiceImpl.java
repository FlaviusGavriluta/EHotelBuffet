package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.service.guest.GuestService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class GuestServiceImpl implements GuestService {

    private static Random random = new Random();
    private static final List<String> GUEST_NAMES = List.of("John", "Jane", "Jack", "Jill", "James", "Judy", "Joe", "Jenny", "Jim", "Jessica");
    private static final GuestType[] GUEST_TYPES = GuestType.values();



    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        String name = getRandomName();
        GuestType guestType = getRandomGuestType();
        LocalDate checkIn = getRandomCheckInDate(seasonStart, seasonEnd);
        LocalDate checkOut = getRandomCheckOutDate(checkIn, seasonEnd);
        return new Guest(name, guestType, checkIn, checkOut);
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

    public String getRandomName() {
        return GUEST_NAMES.get(random.nextInt(GUEST_NAMES.size()));
    }

    public GuestType getRandomGuestType() {
        return GUEST_TYPES[random.nextInt(GUEST_TYPES.length)];
    }

    private LocalDate getRandomCheckInDate(LocalDate seasonStart, LocalDate seasonEnd) {
        int seasonLength = (int) seasonStart.until(seasonEnd, java.time.temporal.ChronoUnit.DAYS) + 1;
        int randomStayLength = random.nextInt(7) + 1;
        int maxCheckInDay = seasonLength - randomStayLength;
        int randomCheckInDay = random.nextInt(maxCheckInDay + 1);
        return seasonStart.plusDays(randomCheckInDay);
    }

    private LocalDate getRandomCheckOutDate(LocalDate checkIn, LocalDate seasonEnd) {
        int maxStayLength = (int) checkIn.until(seasonEnd, java.time.temporal.ChronoUnit.DAYS) + 1;
        int randomStayLength = random.nextInt(maxStayLength) + 1;
        return checkIn.plusDays(randomStayLength);
    }
}
