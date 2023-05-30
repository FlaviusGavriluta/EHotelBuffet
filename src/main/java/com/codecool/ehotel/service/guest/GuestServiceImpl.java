package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.service.guest.GuestService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class GuestServiceImpl implements GuestService {

    private static Random random = new Random();
    private static final List<String> GUEST_NAMES = List.of("John", "Jane", "Jack", "Jill", "James", "Judy", "Joe", "Jenny", "Jim", "Jessica");
    private static final GuestType[] GUEST_TYPES = GuestType.values();

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        Guest generatedGuest;
        String name = getRandomName();
        GuestType guestType = getRandomGuestType();
        LocalDate checkIn = getRandomCheckInDate(seasonStart, seasonEnd);
        LocalDate checkOut = getRandomCheckOutDate(checkIn, seasonEnd);
        generatedGuest = new Guest(name, guestType, checkIn, checkOut);
        return generatedGuest;
    }

    @Override
    public List<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        List<Guest> guestsForDay = new ArrayList<>();
        for (Guest guest : guests) {
            if (date.isAfter(guest.checkIn()) && date.isBefore(guest.checkOut())) {
                guestsForDay.add(guest);
            }
        }
        return guestsForDay;
    }

    @Override
    public List<List<Guest>> splitGuestsIntoBreakfastGroups(List<Guest> guests) {
        List<List<Guest>> breakfastGroups = new ArrayList<>();
        int breakfastCycles = 8;
        // Shuffle the guest list randomly
        Collections.shuffle(guests);
        // Initialize the breakfast groups with empty lists
        for (int i = 0; i < breakfastCycles; i++) {
            breakfastGroups.add(new ArrayList<>());
        }
        for (Guest guest : guests) {
            // Generate a random cycle index for the guest
            int cycleIndex = random.nextInt(breakfastCycles);
            // Check if the cycle is already populated with guests
            List<Guest> cycleGuests = breakfastGroups.get(cycleIndex);

            cycleGuests.add(guest);

        }

        return breakfastGroups;
    }


//    @Override
//    public List<List<Guest>> splitGuestsIntoBreakfastGroups(List<Guest> guests) {
//        int breakfastCycles = 8;
//        int[] cyclesNumbers = new int[breakfastCycles];
//        int guestsNumber = guests.size() / 2;
//        int cyclesNumbersIndex = 0;
//        while (guestsNumber > 0) {
//            int oneCycleGuestsNumber = random.nextInt(guestsNumber) + 1;
//            cyclesNumbers[cyclesNumbersIndex] = oneCycleGuestsNumber;
//            guestsNumber -= oneCycleGuestsNumber;
//            cyclesNumbersIndex++;
//        }
//        List<List<Guest>> breakfastGroups = new ArrayList<>();
//        //Collections.shuffle(guests);
//        List<Guest> currentGroup = new ArrayList<>();
//        cyclesNumbersIndex = 0;
//        int oneCycleGuestCounter = 0;
//        for (Guest guest : guests) {
//            if (cyclesNumbers[cyclesNumbersIndex] > 0 && oneCycleGuestCounter < cyclesNumbers[cyclesNumbersIndex]) {
//                currentGroup.add(guest);
//                oneCycleGuestCounter++;
//            } else {
//                cyclesNumbersIndex++;
//                breakfastGroups.add(currentGroup);
//                currentGroup = new ArrayList<>();
//                oneCycleGuestCounter = 0;
//            }
//        }
//        return breakfastGroups;
//    }

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
