package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.breakfast.utils.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetDisplay;
import com.codecool.ehotel.service.guest.GuestsDisplay;

import static java.lang.System.out;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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

    public void manageBreakfastCycle(List<Guest> guests, Map<MealType, Integer> optimalPortions, int cycleIndex) {
        boolean shouldCollectShortMeals = false;

        LocalTime startTime = LocalTime.of(6 + cycleIndex / 2, (cycleIndex % 2) * 30);
        LocalTime endTime = startTime.plusMinutes(30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = startTime.format(formatter) + " - " + endTime.format(formatter);
        System.out.println("*** Cycle " + (cycleIndex + 1) + " of Breakfast Service: " + formattedTime);
        GuestsDisplay.displayGuests(guests);

        // Refill buffet supply
        buffetService.refillBuffet(buffet, optimalPortions, initialTime);
        BuffetDisplay.displayBuffetSupply(buffet);

        // Serve breakfast to guests
        BreakfastServer.serveBreakfastToGuest(guests, buffet, buffetService);

        // Discard old meals
        if ((cycleIndex + 1) >= 3) {
            shouldCollectShortMeals = true;
        } else if ((cycleIndex + 1) == 8) {
            shouldCollectShortMeals = false;
        }

        initialTime = initialTime.plusSeconds(60 * 30);

        if (shouldCollectShortMeals) {
            int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT, initialTime);
            if (costShort > 0)
                System.out.println("Collected expired SHORT meals. Total cost: $" + costShort + "\n");
        }
    }

    public Map<GuestType, Integer> calculateRemainingGuests(List<List<Guest>> breakfastCycles, int currentCycleIndex) {
        Map<GuestType, Integer> remainingGuests = new HashMap<>();
        for (GuestType guestType : GuestType.values()) {
            int guestsExpected = 0;
            for (int i = currentCycleIndex; i < breakfastCycles.size(); i++) {
                List<Guest> guestsInCycle = breakfastCycles.get(i);
                int guestsOfTypeInCycle = (int) guestsInCycle.stream()
                        .filter(guest -> guest.guestType() == guestType)
                        .count();
                guestsExpected += guestsOfTypeInCycle;
            }
            remainingGuests.put(guestType, guestsExpected);
        }
        return remainingGuests;
    }

    public Buffet getBuffet() {
        return buffet;
    }
}
