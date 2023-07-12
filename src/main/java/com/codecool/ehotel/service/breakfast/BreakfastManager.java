package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.breakfast.utils.RefillBuffet;
import com.codecool.ehotel.service.breakfast.utils.ConsumeBreakfast;
import com.codecool.ehotel.service.breakfast.utils.DiscardOldMeals;
import com.codecool.ehotel.service.breakfast.utils.DiscardNonLongDurabilityMeals;

import java.time.LocalDateTime;
import java.util.List;

public class BreakfastManager {
    private static LocalDateTime currentTime = LocalDateTime.now();
    public static double costOfFoodWaste;

    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        Logger logger = new ConsoleLogger();
        BuffetService buffetService = new BuffetServiceImpl();

        for (List<Guest> guestGroup : guests) {
            // Phase 1: Refill buffet supply
            RefillBuffet.refillBuffet(buffet, buffetService, currentTime);
            currentTime = currentTime.plusMinutes(30);

            // Phase 2: Consume breakfast
            logger.info("The buffet before: " + buffet);
            ConsumeBreakfast.consumeBreakfast(guestGroup, buffet, buffetService);
            logger.info("The buffet after: " + buffet);

            // Phase 3: Discard old meals
            DiscardOldMeals.discardOldMeals(buffet, buffetService, currentTime);
        }
        // Discard all SHORT and MEDIUM durability meals at the end of the day
        DiscardNonLongDurabilityMeals.discardNonLongDurabilityMeals(buffet);
    }
}
