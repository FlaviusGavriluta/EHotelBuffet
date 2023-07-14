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
        logger.logError("This is an error message");

        GuestType[] guestTypes = GuestType.values();
        for (GuestType guestType : guestTypes) {
            logger.logInfo(guestType.toString());
        }
        out.println("-----------------------------------");

        // Generate guests for the season
        LocalDate seasonStart = LocalDate.of(2023, 8, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 8, 31);
        int numberOfGuests = 100;
        List<Guest> randomGuests = GuestGenerator.generateRandomGuestsList(numberOfGuests, seasonStart, seasonEnd);

        // Print random guests for the entire season
        logger.logInfo("Random guests for the entire season:");
        for (Guest guest : randomGuests) {
            out.println(guest);
        }
        logger.logInfo("Total number of guests: " + randomGuests.size() + " guests");
        out.println("-----------------------------------");

        // Split guests for a day into 8 cycles
        LocalDate specificDate = LocalDate.of(2023, 8, 15);
        GuestService guestService = new GuestServiceImpl(randomGuests);
        List<Guest> guestsForDay = guestService.getGuestsForDay(randomGuests, specificDate);

        // Print random guests for a specific day
        System.out.println("Random guests for " + specificDate + ":");
        for (Guest guest : guestsForDay) {
            System.out.println(guest);
        }
        logger.logInfo("Total number of guests for " + specificDate + ": " + guestsForDay.size() + " guests");
        out.println("-----------------------------------");

        // Run breakfast buffet
        Buffet buffet = new Buffet();
        BuffetService buffetService = new BuffetServiceImpl();
        // Split guests for a day into 8 cycles
        List<List<Guest>> breakfastCycles = guestService.splitGuestsIntoBreakfastCycles(guestsForDay);

        // Refill the buffet with portions
        Map<MealType, Integer> portionCounts = new HashMap<>();
        portionCounts.put(MealType.CEREAL, 10); // Exemplu: 10 porții de cereale
        portionCounts.put(MealType.MILK, 5);    // Exemplu: 5 porții de lapte
        portionCounts.put(MealType.FRIED_BACON, 5); // Exemplu: 5 porții de bacon prăjit
        portionCounts.put(MealType.SCRAMBLED_EGGS, 5); // Exemplu: 5 porții de ouă ochiuri
        portionCounts.put(MealType.MASHED_POTATO, 5); // Exemplu: 5 porții de piure de cartofi
        portionCounts.put(MealType.FRIED_SAUSAGE, 5); // Exemplu: 5 porții de cârnați prăjiți
        portionCounts.put(MealType.MUFFIN, 15); // Exemplu: 15 porții de muffin
        BreakfastManager breakfastManager = new BreakfastManager(buffetService);
        breakfastManager.serve(breakfastCycles, portionCounts);

        // Print the guests in each breakfast cycle
        for (int i = 0; i < breakfastCycles.size(); i++) {
            System.out.println("Breakfast Cycle " + (i + 1) + ": " + breakfastCycles.get(i));
        }

        // Consume the freshest portion of a meal type
        MealType mealType = MealType.FRIED_BACON;
        boolean consumed = buffetService.consumeFreshest(buffet, mealType);
        if (consumed) {
            logger.logInfo("A portion of " + mealType + " was consumed.");
        } else {
            logger.logInfo("No portions of " + mealType + " available in the buffet.");
        }

        // Collect waste based on meal durability and timestamp
        logger.logInfo("Meal portions in the buffet: " + buffet.getMealPortionsMap());
        MealDurability mealDurability = MealDurability.SHORT;
        Instant timestamp = Instant.now();
        Instant time = timestamp.plusSeconds(6000);
        int totalCost = buffetService.collectWaste(buffet, mealDurability, time);
        System.out.println("Total cost of discarded meals: " + totalCost);
        logger.logInfo("Meal portions in the buffet: " + buffet.getMealPortionsMap());
    }
}