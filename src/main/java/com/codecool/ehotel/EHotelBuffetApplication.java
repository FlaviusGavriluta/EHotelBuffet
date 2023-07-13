package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.service.breakfast.BreakfastManager;
import com.codecool.ehotel.service.breakfast.utils.ConsumeBreakfast;
import com.codecool.ehotel.service.guest.GuestGenerator;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.model.Buffet;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        Logger logger = new ConsoleLogger();
        LocalDate targetDate = LocalDate.of(2023, 7, 15);
        logger.info("EHotel Buffet Application started");
        GuestService guestService = new GuestServiceImpl();
        List<Guest> generateGuests = GuestGenerator.generateGuests(guestService, 40, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 9, 30)); // Generate guests
        List<Guest> guestsForToday = GuestGenerator.getGuestsForDay(generateGuests, guestService, targetDate);
        Buffet buffet = new Buffet(guestsForToday); // Initialize the buffet state
        System.out.println("Number of guest for today: " + guestsForToday.size());
        System.out.println(buffet);

        BreakfastManager.serve(guestService
                .splitGuestsIntoBreakfastGroups(GuestGenerator
                        .getGuestsForDay(generateGuests, guestService, targetDate)), buffet);

        logger.info("There are " + buffet);
        logger.info("Number of unhappy guests is : " + ConsumeBreakfast.unhappyGuests);
        logger.info("The cost of food waste is : " + BreakfastManager.costOfFoodWaste);
        logger.info("Buffet is closed");
    }
}
