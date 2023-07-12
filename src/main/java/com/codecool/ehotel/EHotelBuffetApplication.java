package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.breakfast.BreakfastManager;
import com.codecool.ehotel.service.breakfast.utils.ConsumeBreakfast;
import com.codecool.ehotel.service.guest.GuestGenerator;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.model.Buffet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EHotelBuffetApplication {
    public static void main(String[] args) {
        Logger logger = new ConsoleLogger();
        logger.info("EHotel Buffet Application started");
        // Initialize services
        GuestService guestService = new GuestServiceImpl();
        // Generate guests for the season
        List<Guest> allGuests = new ArrayList<>();
        LocalDate seasonStart = LocalDate.of(2023, 6, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 9, 30);

        // Initialize services
        guestService = new GuestServiceImpl();
        // Generate guests for the season
        allGuests = GuestGenerator.generateGuests(guestService);

        // Run breakfast buffet
        logger.info("Buffet is open");
        Buffet buffet = new Buffet();
        LocalDate targetDate = LocalDate.of(2023, 7, 15);
        BreakfastManager.serve(guestService
                .splitGuestsIntoBreakfastGroups(GuestGenerator
                        .getGuestsForDay(GuestGenerator
                                .generateGuests(guestService), guestService, targetDate)), buffet);
        logger.info("There are " + buffet);
        logger.info("Number of unhappy guests is : " + ConsumeBreakfast.unhappyGuests);
        logger.info("The cost of food waste is : " + BreakfastManager.costOfFoodWaste);
        logger.info("Buffet is closed");
    }
}
