package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.breakfast.utils.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetDisplay;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
            BuffetDisplay.displayBuffetSupply(buffet);

            // Serve breakfast to guests
            List<Guest> guests = breakfastCycles.get(cycleIndex);
            BreakfastServer.serveBreakfastToGuest(guests, buffet, buffetService);
            BuffetDisplay.displayBuffetSupply(buffet);
            System.out.println("Bufetul contine: "+buffet.getMealPortionsMap().values().stream().mapToInt(List::size)+" portii");

            // Discard old meals
            if ((cycleIndex + 1) >= 3) {
                MealDiscarder.discardOldMeals(buffet, buffetService);
            }

            System.out.println("=====================");
            System.out.println();
        }
        MealDiscarder.discardNonLongDurabilityMeals(buffet, buffetService);
    }
}
