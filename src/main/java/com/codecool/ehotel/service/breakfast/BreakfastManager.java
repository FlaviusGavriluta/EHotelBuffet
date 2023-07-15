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
            refillBuffet(portionCounts);

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
        Instant timestamp = Instant.now(); // Puteți folosi un timestamp valid pentru reumplerea aprovizionării

        for (Map.Entry<MealType, Integer> entry : portionCounts.entrySet()) {
            MealType mealType = entry.getKey();
            int portionCount = entry.getValue();

            for (int i = 0; i < portionCount; i++) {
                MealPortion mealPortion = new MealPortion(mealType, timestamp);
                buffet.addMealPortion(mealType, mealPortion);
            }
        }

        System.out.println("Buffet supply has been refilled.");
        System.out.println("Buffet contains:" + buffet.getMealPortionsMap());
        System.out.println("Buffet portions count: " + buffet.getMealPortionsMap().values().stream().mapToInt(List::size).sum());

    }

    private void serveBreakfastToGuest(List<Guest> guests) {
        for (Guest guest : guests) {
            GuestType guestType = guest.guestType();
            List<MealType> preferences = guestType.getMealPreferences();

            boolean foundPreferredMeal = false;

            for (MealType mealType : preferences) {
                System.out.println("Guest " + guest.name() + " is looking for " + mealType);
                if (buffetService.consumeFreshest(buffet, mealType)) {
                    System.out.println("Guest " + guest.name() + " has eaten " + mealType);
                    foundPreferredMeal = true;
                    break;
                }
            }
            if (!foundPreferredMeal) {
                System.out.println("Guest " + guest.name() + " has eaten nothing");
            }
        }
        System.out.println("Buffet contains after guests have eaten: " + buffet.getMealPortionsMap());
        System.out.println("Buffet portions count: " + buffet.getMealPortionsMap().values().stream().mapToInt(List::size).sum());
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
