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

import static com.codecool.ehotel.service.breakfast.utils.ConsumeBreakfast.consumeBreakfast;
import static com.codecool.ehotel.service.breakfast.utils.RefillBuffet.refillBuffet;
import static com.codecool.ehotel.service.breakfast.utils.GetOptimalPortions.getOptimalPortions;

public class BreakfastManager {
    private static LocalDateTime currentTime = LocalDateTime.now();
    public static double costOfFoodWaste;

    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        Logger logger = new ConsoleLogger();
        BuffetService buffetService = new BuffetServiceImpl();
        Map<GuestType, Integer> guestsToExpect = new HashMap<>();
        int cyclesLeft = 8;

        for (List<Guest> guestGroup : guests) {
            for (Guest guest : guestGroup) {
                GuestType guestType = guest.guestType();
                if (guestsToExpect.containsKey(guestType)) {
                    int count = guestsToExpect.get(guestType);
                    guestsToExpect.put(guestType, count + 1);
                } else {
                    guestsToExpect.put(guestType, 1);
                }
            }
        }

        for (List<Guest> guestGroup : guests) {
            System.out.println("There are " + cyclesLeft + " cycles left");
            // Phase 1: Refill buffet supply
            List<BuffetService.RefillRequest> refillRequests = getOptimalPortions(guestsToExpect, cyclesLeft, 50);
            System.out.println("The optimal refill request is: " + refillRequests);
            refillBuffet(buffet, buffetService, refillRequests ,currentTime);
            currentTime = currentTime.plusMinutes(30);
            // Phase 2: Consume breakfast
            consumeBreakfast(guestGroup, buffet, buffetService);

            // Phase 3: Discard old meal
            DiscardOldMeals.discardOldMeals(buffet, buffetService, currentTime);
            for (Guest guest : guestGroup) {
                GuestType guestType = guest.guestType();
                if (guestsToExpect.containsKey(guestType)) {
                    int count = guestsToExpect.get(guestType);
                    count--;
                    if (count > 0) {
                        guestsToExpect.put(guestType, count);
                    } else {
                        guestsToExpect.remove(guestType);
                    }
                }
            }
            cyclesLeft--;
        }
        // Discard all SHORT and MEDIUM durability meals at the end of the day
        DiscardNonLongDurabilityMeals.discardNonLongDurabilityMeals(buffet);
    }
}
