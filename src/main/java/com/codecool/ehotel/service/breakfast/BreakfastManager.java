package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BreakfastManager {
    private static LocalDateTime currentTime = LocalDateTime.now();
    public static int unhappyGuests;
    public static double costOfFoodWaste;

    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        Logger logger = new ConsoleLogger();
        BuffetService buffetService = new BuffetServiceImpl();


        for (List<Guest> guestGroup : guests) {
            // Phase 1: Refill buffet supply
            refillBuffet(buffet, buffetService, currentTime);
            currentTime = currentTime.plusMinutes(30);

            // Phase 2: Consume breakfast
            logger.info("The buffet before: " + buffet);
            consumeBreakfast(guestGroup, buffet, buffetService);
            logger.info("The buffet after: " + buffet);

            // Phase 3: Discard old meals
//            if (cycleCount % 3 == 0) {
//                discardOldMeals(buffet, buffetService);
//            }
//            for (MealPortion mealPortion : buffet.getMealPortions()) {
//                if (mealPortion.getTimestamp().isBefore(currentTime.plusMinutes(90))) {
//                    discardOldMeals(buffet, buffetService, currentTime);
//                    System.out.println("Discarded " + mealPortion.getMealType().name());
//                }
//            }
//            for (MealPortion mealPortion : buffet.getMealPortions()) {
//                if (mealPortion.getTimestamp().isBefore(currentTime.minusMinutes(90))) {
//                    discardOldMeals(buffet, buffetService, currentTime);
//                    System.out.println("Discarded " + mealPortion.getMealType().name());
//                }
//            }

            discardOldMeals(buffet, buffetService, currentTime);
        }

        // Discard all SHORT and MEDIUM durability meals at the end of the day
        discardNonLongDurabilityMeals(buffet);
    }

    private static void refillBuffet(Buffet buffet, BuffetService buffetService, LocalDateTime currentTime) {
        List<BuffetService.RefillRequest> refillRequests = generateRefillRequests();
        buffetService.refillBuffet(buffet, refillRequests, currentTime);
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
                unhappyGuests++;
                System.out.println(guest.name() + " couldn't find any of their preferred meals and went unhappy");
            }
        }
    }


    private static void discardOldMeals(Buffet buffet, BuffetService buffetService, LocalDateTime currentTime) {
        List<MealPortion> mealPortions = buffet.getMealPortions();
        List<MealPortion> mealsToDiscard = new ArrayList<>();
        for (MealPortion mealPortion : mealPortions) {
            // Discard SHORT durability meals that are older than 90 minutes
            System.out.println("Current: " + currentTime);
            System.out.println("Meal: " + mealPortion.getTimestamp());
            System.out.println("Meal type: " + mealPortion.getMealType().name());
            System.out.println("Durability: " + mealPortion.getMealType().getDurability());
            System.out.println();

            if (mealPortion.getMealType().getDurability() == MealDurability.SHORT
                    && mealPortion.getTimestamp().plusMinutes(90).isBefore(currentTime)) {
                System.out.println("Discarded meal: " + mealPortion.mealType().name());
               // buffetService.removeMealPortion(buffet, mealPortion);
                mealsToDiscard.add(mealPortion);
            }
        }
        // Remove the collected meals from the buffet
        for (MealPortion meal : mealsToDiscard) {
            buffet.removeMealPortion(meal);
            costOfFoodWaste += meal.getMealType().getCost();
        }
    }

    private static void discardNonLongDurabilityMeals(Buffet buffet) {
        List<MealPortion> mealPortions = buffet.getMealPortions();
        Iterator<MealPortion> iterator = mealPortions.iterator();

        while (iterator.hasNext()) {
            MealPortion mealPortion = iterator.next();

            if (mealPortion.getMealType().getDurability() != MealDurability.LONG) {
                iterator.remove();
                costOfFoodWaste += mealPortion.getMealType().getCost();
            }
        }
    }

    private static int getOptimalPortions(Buffet buffet, List<Guest> guests, int cyclesLeft, double costOfUnhappyGuest) {
        return 0;
    }

}
