package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.breakfast.utils.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreakfastManager {
    private static LocalDateTime currentTime = LocalDateTime.now();
    public static double costOfFoodWaste;

    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        Logger logger = new ConsoleLogger();
        BuffetService buffetService = new BuffetServiceImpl();
        Map<GuestType, Integer> guestsToExpect = calculateGuestsToExpect(guests);
        int cyclesLeft = 8;

        for (List<Guest> guestGroup : guests) {
            System.out.println("There are " + cyclesLeft + " cycles left");

            // Phase 1: Refill buffet supply
            List<BuffetService.RefillRequest> refillRequests = GetOptimalPortions.getOptimalPortions(guestsToExpect, cyclesLeft, 50);
            System.out.println("The optimal refill request is: " + refillRequests);
            RefillBuffet.refillBuffet(buffet, buffetService, refillRequests, currentTime);
            currentTime = currentTime.plusMinutes(30);

            // Phase 2: Consume breakfast
            ConsumeBreakfast.consumeBreakfast(guestGroup, buffet, buffetService);

            // Phase 3: Discard old meals
            DiscardOldMeals.discardOldMeals(buffet, buffetService, currentTime);

            updateGuestsToExpect(guestGroup, guestsToExpect);
            cyclesLeft--;
        }

        // Discard all SHORT and MEDIUM durability meals at the end of the day
        DiscardNonLongDurabilityMeals.discardNonLongDurabilityMeals(buffet);
    }

    private static Map<GuestType, Integer> calculateGuestsToExpect(List<List<Guest>> guests) {
        Map<GuestType, Integer> guestsToExpect = new HashMap<>();

        for (List<Guest> guestGroup : guests) {
            for (Guest guest : guestGroup) {
                GuestType guestType = guest.guestType();
                guestsToExpect.put(guestType, guestsToExpect.getOrDefault(guestType, 0) + 1);
            }
        }

        return guestsToExpect;
    }

    private static void updateGuestsToExpect(List<Guest> guestGroup, Map<GuestType, Integer> guestsToExpect) {
        for (Guest guest : guestGroup) {
            GuestType guestType = guest.guestType();
            guestsToExpect.computeIfPresent(guestType, (key, count) -> count - 1);
            guestsToExpect.remove(guestType, 0);
        }
    }
}
