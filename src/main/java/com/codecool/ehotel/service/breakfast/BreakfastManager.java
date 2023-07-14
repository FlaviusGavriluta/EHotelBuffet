package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;
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
            //buffetService.collectWaste(buffet);

            System.out.println();
        }
    }

    public void refillBuffet(Map<MealType, Integer> portionCounts) {
        Instant timestamp = Instant.now(); // Puteți folosi un timestamp valid pentru reumplerea aprovizionării
        buffetService.refillBuffet(buffet, portionCounts, timestamp);
        System.out.println("Buffet supply has been refilled.");
        System.out.println("Buffet contains:" + buffet.getMealPortionsMap());
    }

    private void serveBreakfastToGuest(List<Guest> guests) {
        for (Guest guest : guests) {
            GuestType guestType = guest.guestType();
            List<MealType> preferences = guestType.getMealPreferences();

            boolean foundPreferredMeal = false;

            for(MealType mealType : preferences) {
                if(buffetService.consumeFreshest(buffet, mealType)) {
                    System.out.println("Guest " + guest.name() + " has eaten " + mealType);
                    foundPreferredMeal = true;
                    break;
                }
            }
            if(!foundPreferredMeal) {
                System.out.println("Guest " + guest.name() + " has eaten nothing");
            }
        }
    }
}
