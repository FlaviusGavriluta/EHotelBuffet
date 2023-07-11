package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreakfastManager {
    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        Logger logger = new ConsoleLogger();
        BuffetService buffetService = new BuffetServiceImpl();
        int cycleCount = 0;

        for (List<Guest> guestGroup : guests) {
            cycleCount++;

            // Phase 1: Refill buffet supply
            refillBuffet(buffet, buffetService);

            // Phase 2: Consume breakfast
            logger.info("The buffet before: " + buffet);
            consumeBreakfast(guestGroup, buffet, buffetService);
            logger.info("The buffet after: " + buffet);

            // Phase 3: Discard old meals
            if (cycleCount % 3 == 0) {
                discardOldMeals(buffet, buffetService);
            }
        }

        // Discard all SHORT and MEDIUM durability meals at the end of the day
        discardNonLongDurabilityMeals(buffet);
    }

    private static void refillBuffet(Buffet buffet, BuffetService buffetService) {
        List<BuffetService.RefillRequest> refillRequests = generateRefillRequests();
        buffetService.refillBuffet(buffet, refillRequests);
    }

    private static List<BuffetService.RefillRequest> generateRefillRequests() {
        List<BuffetService.RefillRequest> refillRequests = new ArrayList<>();

        Random random = new Random();
        MealType[] mealTypes = MealType.values();
        int numberOfRequests = random.nextInt(5) + 1; // Generate 1 to 5 refill requests

        for (int i = 0; i < numberOfRequests; i++) {
            MealType randomMealType = mealTypes[random.nextInt(mealTypes.length)];
            int randomAmount = random.nextInt(5) + 1; // Generate 1 to 5 desired amounts

            refillRequests.add(new BuffetService.RefillRequest(randomMealType, randomAmount));
        }

        return refillRequests;
    }

    private static void consumeBreakfast(List<Guest> guests, Buffet buffet, BuffetService buffetService) {
        for (Guest guest : guests) {
            List<MealType> mealPreferences = guest.guestType().getMealPreferences();
            boolean foundPreferredMeal = false;

            for (MealType mealType : mealPreferences) {
                if (buffetService.consumeFreshest(buffet, mealType)) {
                    System.out.println(guest.name() + " ate " + mealType);
                    foundPreferredMeal = true;
                    break; // Exit the loop if a preferred meal is found
                }
            }

            if (!foundPreferredMeal) {
                System.out.println(guest.name() + " couldn't find any of their preferred meals and went unhappy");
            }
        }
    }


    private static void discardOldMeals(Buffet buffet, BuffetService buffetService) {
        LocalDateTime currentTime = LocalDateTime.now();
        List<MealPortion> mealPortions = buffet.getMealPortions();

        for (MealPortion mealPortion : mealPortions) {
            if (mealPortion.getMealType().getDurability() == MealDurability.SHORT
                    && mealPortion.getTimestamp().isBefore(currentTime.minusMinutes(30 * 3))) {
                buffetService.removeMealPortion(buffet, mealPortion);
            }
        }
    }

    private static void discardNonLongDurabilityMeals(Buffet buffet) {
        List<MealPortion> mealPortions = buffet.getMealPortions();

        mealPortions.removeIf(mealPortion -> mealPortion.getMealType().getDurability() != MealDurability.LONG);
    }

}
