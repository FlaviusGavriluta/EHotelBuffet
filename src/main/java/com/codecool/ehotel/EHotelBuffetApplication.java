package com.codecool.ehotel;

import com.codecool.ehotel.logging.ConsoleLogger;
import com.codecool.ehotel.logging.Logger;
import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.guest.GuestGenerator;
import com.codecool.ehotel.service.breakfast.BreakfastManager;

import static java.lang.System.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class EHotelBuffetApplication {

    public static void main(String[] args) {
        // Initialize logger
        Logger logger = new ConsoleLogger();
        logger.logInfo("Starting EHotel Buffet Application");
        out.println("-----------------------------------");

        // Generate guests for the season
        LocalDate seasonStart = LocalDate.of(2023, 8, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 8, 31);
        int numberOfGuests = 100;
        List<Guest> randomGuests = GuestGenerator.generateRandomGuestsList(numberOfGuests, seasonStart, seasonEnd);

        // Print random guests for the entire season
        logger.logInfo("Total number of guests for the entire season: " + randomGuests.size() + " guests");

        // Split guests for a day into 8 cycles
        LocalDate specificDate = LocalDate.of(2023, 8, 15);
        GuestService guestService = new GuestServiceImpl(randomGuests);
        List<Guest> guestsForDay = guestService.getGuestsForDay(randomGuests, specificDate);

        // Print random guests for a specific day
        logger.logInfo("Total number of guests for " + specificDate + ": " + guestsForDay.size() + " guests");
        out.println("-----------------------------------");

        // Run breakfast buffet
        BuffetService buffetService = new BuffetServiceImpl();
        // Split guests for a day into 8 cycles
        List<List<Guest>> breakfastCycles = guestService.splitGuestsIntoBreakfastCycles(guestsForDay);

        // Refill the buffet with portions
        Map<MealType, Integer> portionCounts = new HashMap<>();
        portionCounts.put(MealType.PANCAKE, 1);
        portionCounts.put(MealType.MASHED_POTATO, 1);
        portionCounts.put(MealType.MILK, 1);
        portionCounts.put(MealType.CEREAL, 1);
        portionCounts.put(MealType.MUFFIN, 1);
        portionCounts.put(MealType.SUNNY_SIDE_UP, 1);
        portionCounts.put(MealType.SCRAMBLED_EGGS, 1);
        BreakfastManager breakfastManager = new BreakfastManager(buffetService);
        breakfastManager.serve(breakfastCycles, portionCounts);
    }
}