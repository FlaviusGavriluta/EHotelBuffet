package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.service.breakfast.BreakfastManager;
import com.codecool.ehotel.service.breakfast.BuffetRefillOptimizer;
import com.codecool.ehotel.service.breakfast.utils.ConsumeBreakfast;
import com.codecool.ehotel.service.guest.GuestGenerator;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDate;
import java.util.List;

public class EHotelBuffetApplication {
    public static int[] guestsToExpect;

    public static void main(String[] args) {
        guestsToExpect = new int[GuestType.values().length];
        guestsToExpect[GuestType.BUSINESS.ordinal()] = 6;
        guestsToExpect[GuestType.TOURIST.ordinal()] = 4;
        guestsToExpect[GuestType.KID.ordinal()] = 2;

        Logger logger = new ConsoleLogger();
        logger.info("EHotel Buffet Application started");
        GuestService guestService = new GuestServiceImpl();
        List<Guest> generateGuests = GuestGenerator.generateGuests(guestService, guestsToExpect); // Generate guests
        Buffet buffet = new Buffet(generateGuests); // Initialize the buffet state

        // Set refill amounts for each meal type in the buffet
        for (MealType mealType : MealType.values()) {
            buffet.setRefillAmount(mealType, BuffetRefillOptimizer.runOptimization()[mealType.ordinal()]);
        }

        LocalDate targetDate = LocalDate.of(2023, 7, 15);
        BreakfastManager.serve(guestService
                .splitGuestsIntoBreakfastGroups(GuestGenerator
                        .getGuestsForDay(GuestGenerator
                                .generateGuests(guestService, guestsToExpect), guestService, targetDate)), buffet);
        logger.info("There are " + buffet);
        logger.info("Number of unhappy guests is : " + ConsumeBreakfast.unhappyGuests);
        logger.info("The cost of food waste is : " + BreakfastManager.costOfFoodWaste);
        logger.info("Buffet is closed");
    }
}
