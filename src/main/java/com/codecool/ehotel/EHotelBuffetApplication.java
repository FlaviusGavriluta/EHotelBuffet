package com.codecool.ehotel;

import com.codecool.ehotel.logging.ConsoleLogger;
import com.codecool.ehotel.logging.Logger;
import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        // Initialize services


        // Generate guests for the season
        LocalDate seasonStart = LocalDate.of(2023, 8, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 8, 14);

        GuestService guestService = new GuestServiceImpl(new ArrayList<>());
        Guest randomGuest = guestService.generateRandomGuest(seasonStart, seasonEnd);

        System.out.println("Random Guest: " + randomGuest);


        // Run breakfast buffet
        Buffet buffet = new Buffet();
        BuffetService buffetService = new BuffetServiceImpl();

// Refill the buffet with portions
        Map<MealType, Integer> portionCounts = new HashMap<>();
        portionCounts.put(MealType.SCRAMBLED_EGGS, 5);
        portionCounts.put(MealType.FRIED_BACON, 10);
        Instant timestamp = Instant.now();
        buffetService.refillBuffet(buffet, portionCounts, timestamp);

// Consume the freshest portion of a meal type
        MealType mealType = MealType.FRIED_BACON;
        boolean consumed = buffetService.consumeFreshest(buffet, mealType);
        if (consumed) {
            System.out.println("A portion of " + mealType + " was consumed.");
        } else {
            System.out.println("No portions of " + mealType + " available in the buffet.");
        }

        // Collect waste based on meal durability and timestamp
        System.out.println("Meal portions in the buffet: " + buffet.getMealPortionsMap().values());
        MealDurability mealDurability = MealDurability.SHORT;
        Instant time = timestamp.plusSeconds(6000);
        int totalCost = buffetService.collectWaste(buffet, mealDurability, time);
        System.out.println("Total cost of discarded meals: " + totalCost);
    }
}