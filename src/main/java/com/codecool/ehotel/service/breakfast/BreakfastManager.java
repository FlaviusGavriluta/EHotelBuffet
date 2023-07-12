package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.breakfast.utils.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDateTime;
import java.util.List;

import static com.codecool.ehotel.service.breakfast.utils.ConsumeBreakfast.consumeBreakfast;
import static com.codecool.ehotel.service.breakfast.utils.GetOptimalPortions.getOptimalPortions;
import static com.codecool.ehotel.service.breakfast.utils.RefillBuffet.refillBuffet;

public class BreakfastManager {
    private static LocalDateTime currentTime = LocalDateTime.now();
    public static double costOfFoodWaste;

    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        Logger logger = new ConsoleLogger();
        BuffetService buffetService = new BuffetServiceImpl();
        List<List<Guest>> guestsCopy = guests;
        int cycles = 1;
        double value = 0;
        for (List<Guest> guestGroup : guests) {
            // Phase 1: Refill buffet supply
            //getOptimalPortions(buffet, guestsCopy, cycles, value);
            guestsCopy.remove(guests);
            cycles++;
            refillBuffet(buffet, buffetService, currentTime);
            currentTime = currentTime.plusMinutes(30);
            // Phase 2: Consume breakfast
            logger.info("The buffet before: " + buffet);
            consumeBreakfast(guestGroup, buffet, buffetService);
            logger.info("The buffet after: " + buffet);

            // Phase 3: Discard old meal
            DiscardOldMeals.discardOldMeals(buffet, buffetService, currentTime);
        }
        // Discard all SHORT and MEDIUM durability meals at the end of the day
        DiscardNonLongDurabilityMeals.discardNonLongDurabilityMeals(buffet);
    }
}
