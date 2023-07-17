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
    private Instant initialTime;

    public BreakfastManager(BuffetService buffetService) {
        this.buffetService = buffetService;
        this.buffet = new Buffet();
        this.initialTime = Instant.now();
    }

    public void serve(List<List<Guest>> breakfastCycles, Map<MealType, Integer> portionCounts) {
        boolean shouldCollectShortMeals = false;
        for (int cycleIndex = 0; cycleIndex < breakfastCycles.size(); cycleIndex++) {
            System.out.println("=== Breakfast Cycle " + (cycleIndex + 1) + " ===");

            // Refill buffet supply
            buffetService.refillBuffet(buffet, portionCounts, initialTime.plusSeconds(cycleIndex * 60 * 30));
            BuffetDisplay.displayBuffetSupply(buffet);

            // Serve breakfast to guests
            List<Guest> guests = breakfastCycles.get(cycleIndex);
            BreakfastServer.serveBreakfastToGuest(guests, buffet, buffetService);
            BuffetDisplay.displayBuffetSupply(buffet);

            // Discard old meals
            if ((cycleIndex + 1) >= 3) {
                shouldCollectShortMeals = true;
            }
            if (shouldCollectShortMeals) {
                int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT, Instant.now());
                System.out.println("Collected expired SHORT meals. Total cost: $" + costShort);
                BuffetDisplay.displayBuffetSupply(buffet);
            }
            System.out.println(Instant.now());

            System.out.println("=====================");
            System.out.println();
        }
        int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT, Instant.now());
        int costMedium = buffetService.collectWaste(buffet, MealDurability.MEDIUM, Instant.now());
        System.out.println("Discarded non-long durability meals at the end of the day. Total cost: $" + costShort + costMedium);
        BuffetDisplay.displayBuffetSupply(buffet);
    }
}
