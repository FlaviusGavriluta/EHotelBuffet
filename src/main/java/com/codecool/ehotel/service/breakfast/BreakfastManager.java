package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BreakfastManager {
    private BuffetService buffetService;
    private Buffet buffet;

    public BreakfastManager(BuffetService buffetService) {
        this.buffetService = buffetService;
        this.buffet = new Buffet();
    }

    public void serve(List<List<Guest>> breakfastCycles, Map<MealType, Integer> portionCounts) {
        for (int cycleIndex = 0; cycleIndex < breakfastCycles.size(); cycleIndex++) {
            System.out.println("=== Breakfast Cycle " + (cycleIndex + 1) + " ===");

            // Refill buffet supply
            buffetService.refillBuffet(buffet, portionCounts, Instant.now());

            // Serve breakfast to guests
            List<Guest> guests = breakfastCycles.get(cycleIndex);
            serveBreakfastToGuest(guests);

            // Discard old meals
            if ((cycleIndex + 1) % 3 == 0) {
                discardOldMeals();
            }
            System.out.println();
        }
        discardNonLongDurabilityMeals();
    }

    public void refillBuffet(Map<MealType, Integer> portionCounts) {
        for (Map.Entry<MealType, Integer> entry : portionCounts.entrySet()) {
            MealType mealType = entry.getKey();
            int portionCount = entry.getValue();

            List<MealPortion> mealPortions = buffet.getMealPortionsByType(mealType);
            mealPortions.clear(); // Se elimină toate porțiunile existente pentru tipul de mâncare

            for (int i = 0; i < portionCount; i++) {
                MealPortion mealPortion = new MealPortion(mealType, Instant.now());
                buffet.addMealPortion(mealType, mealPortion);
            }
        }

        buffet.removeExpiredMealPortions(Instant.now()); // Se elimină porțiunile scurte expirate

        System.out.println("Buffet supply has been refilled and it contains:");
        for (Map.Entry<MealType, List<MealPortion>> entry : buffet.getMealPortionsMap().entrySet()) {
            MealType mealType = entry.getKey();
            List<MealPortion> mealPortions = entry.getValue();
            int portionCount = mealPortions.size();

            System.out.println(portionCount + " portion(s) of " + mealType);
        }

        System.out.println("Total portions in the buffet: " + buffet.getMealPortionsMap().values().stream().mapToInt(List::size).sum());
        System.out.println("----------------------------------------");
    }

    private void serveBreakfastToGuest(List<Guest> guests) {
        for (Guest guest : guests) {
            GuestType guestType = guest.guestType();
            List<MealType> preferences = guestType.getMealPreferences();

            boolean foundPreferredMeal = false;

            for (MealType mealType : preferences) {
                if (buffetService.consumeFreshest(buffet, mealType)) {
                    System.out.println(guest.name() + " has eaten " + mealType);
                    foundPreferredMeal = true;
                    break;
                }
            }
            if (!foundPreferredMeal) {
                System.out.println(guest.name() + " has eaten nothing");
            }
        }
        System.out.println();
        System.out.println("Buffet supply after guests eaten:");
        for (Map.Entry<MealType, List<MealPortion>> entry : buffet.getMealPortionsMap().entrySet()) {
            MealType mealType = entry.getKey();
            List<MealPortion> mealPortions = entry.getValue();
            int portionCount = mealPortions.size();

            System.out.println(portionCount + " portion(s) of " + mealType);
        }

        System.out.println("Total portions in the buffet: " + buffet.getMealPortionsMap().values().stream().mapToInt(List::size).sum());
        System.out.println("=====================");
    }

    private void discardOldMeals() {
        Instant currentTime = Instant.now();
        Instant discardTime = currentTime.minusSeconds(5400); // 90 minutes ago

        int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT, discardTime);
        System.out.println("Discarded old SHORT meals. Total cost: $" + costShort);
    }

    private void discardNonLongDurabilityMeals() {
        Instant currentTime = Instant.now();

        for (MealType mealType : buffet.getMealPortionsMap().keySet()) {
            if (mealType.getDurability() != MealDurability.LONG) {
                buffetService.collectWaste(buffet, mealType.getDurability(), currentTime);
            }
        }

        System.out.println("Discarded non-long durability meals at the end of the day.");
    }
}
