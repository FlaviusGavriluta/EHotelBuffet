package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;
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
        List<MealPortion> mealBatch = new ArrayList<>();
        MealPortion meal1 = new MealPortion(MealType.PANCAKE, LocalDateTime.now());
        MealPortion meal2 = new MealPortion(MealType.FRIED_SAUSAGE, LocalDateTime.now());
        mealBatch.add(meal1);
        mealBatch.add(meal2);

        buffet.addMealPortion(mealBatch);
        buffet.getMealPortionsByType(MealType.PANCAKE);
        for(MealPortion meal : buffet.getMealPortionsByType(MealType.PANCAKE)){
            logger.info(meal.mealType() + " " + meal.timestamp());
        }
        logger.info("There are " + buffet.getMealPortionsByType(MealType.PANCAKE).size() + " pancakes");
        logger.info("Buffet is closed");
    }
}
