package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.breakfast.utils.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetDisplay;
import com.codecool.ehotel.service.guest.GuestsDisplay;

import java.time.*;
import java.time.format.DateTimeFormatter;
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

        LocalTime targetTime = LocalTime.of(7, 0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(initialTime, ZoneId.systemDefault());
        LocalDateTime targetDateTime = currentDateTime.with(targetTime);
        Duration duration = Duration.between(currentDateTime, targetDateTime);
        long secondsToAdd = duration.toSeconds() + 60 * 60 * 3 + 1;
        initialTime = initialTime.plusSeconds(secondsToAdd);

        for (int cycleIndex = 0; cycleIndex < breakfastCycles.size(); cycleIndex++) {
            LocalTime startTime = LocalTime.of(7 + cycleIndex / 2, (cycleIndex % 2) * 30);
            LocalTime endTime = startTime.plusMinutes(30);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = startTime.format(formatter) + " - " + endTime.format(formatter);
            System.out.println("Cycle " + (cycleIndex + 1) + " of Breakfast Service: " + formattedTime);
            GuestsDisplay.displayGuests(breakfastCycles.get(cycleIndex));

            // Refill buffet supply
            buffetService.refillBuffet(buffet, portionCounts, initialTime);
            BuffetDisplay.displayBuffetSupply(buffet);

            // Serve breakfast to guests
            List<Guest> guests = breakfastCycles.get(cycleIndex);
            BreakfastServer.serveBreakfastToGuest(guests, buffet, buffetService);

            // Discard old meals
            if ((cycleIndex + 1) >= 3) {
                shouldCollectShortMeals = true;
            }
            initialTime = initialTime.plusSeconds(60 * 30);

            if (shouldCollectShortMeals) {
                int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT, initialTime);
                System.out.println("Collected expired SHORT meals. Total cost: $" + costShort);
            }
            System.out.println();
        }
        int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT);
        int costMedium = buffetService.collectWaste(buffet, MealDurability.MEDIUM);
        int totalCost = costShort + costMedium;
        System.out.println("Discarded non-long durability meals after breakfast. Total cost: $" + totalCost);
    }
}
