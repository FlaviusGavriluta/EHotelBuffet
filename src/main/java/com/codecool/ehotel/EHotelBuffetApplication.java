package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.model.Buffet;

import java.nio.Buffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        for (int i = 0; i < 50; i++) {
            Guest guest = guestService.generateRandomGuest(seasonStart, seasonEnd);
            allGuests.add(guest);
        }
        LocalDate targetDate = LocalDate.of(2023, 7, 15);
        Set<Guest> guestForADay = guestService.getGuestsForDay(allGuests, targetDate);
        logger.info("Guests for " + targetDate + ": " + guestForADay);
        for (Guest guest : guestForADay) {
            logger.info(guest.name() + " is a " + guest.guestType() + " guest");
        }
        // Run breakfast buffet
        logger.info("Buffet is open");
        Buffet buffet = new Buffet();
        BuffetService buffetService = new BuffetServiceImpl();
        buffetService.refillBuffet(buffet, List.of(
                new BuffetService.RefillRequest(MealType.PANCAKE, 10),
                new BuffetService.RefillRequest(MealType.CROISSANT, 10),
                new BuffetService.RefillRequest(MealType.CEREAL, 10)
        ));

        logger.info("There are " + buffet + " pancakes");
        logger.info("Buffet is closed");
    }
}
