package com.codecool.ehotel;

import com.codecool.ehotel.logging.ConsoleLogger;
import com.codecool.ehotel.logging.Logger;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;

public class EHotelBuffetApplication {

    public static void main(String[] args) {
        // Initialize logger
        Logger logger = new ConsoleLogger();
        logger.logInfo("Starting EHotel Buffet Application");
        logger.logError("This is an error message");

        GuestType[] guestTypes = GuestType.values();
        for (GuestType guestType : guestTypes) {
            logger.logInfo(guestType.toString());
        }
        // Initialize services


        // Generate guests for the season
        LocalDate seasonStart = LocalDate.of(2023, 8, 1);
        LocalDate seasonEnd = LocalDate.of(2023, 8, 14);

        GuestService guestService = new GuestServiceImpl(new ArrayList<>());
        Guest randomGuest = guestService.generateRandomGuest(seasonStart, seasonEnd);

        System.out.println("Random Guest: " + randomGuest);


        // Run breakfast buffet


    }
}