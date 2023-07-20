package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class BreakfastServer {
    public static void serveBreakfastToGuest(List<Guest> guests, Buffet buffet, BuffetService buffetService) {
        List<Guest> unhappyGuests = new ArrayList<>();
        System.out.println();

        for (Guest guest : guests) {
            GuestType guestType = guest.guestType();
            List<MealType> preferences = new ArrayList<>(guestType.getMealPreferences());
            Collections.shuffle(preferences);
            boolean foundPreferredMeal = false;

            for (MealType mealType : preferences) {
                if (buffetService.consumeFreshest(buffet, mealType)) {
                    foundPreferredMeal = true;
                    break;
                }
            }
            if (!foundPreferredMeal) {
                unhappyGuests.add(guest);
            }
        }

        if (!unhappyGuests.isEmpty()) {
            System.out.println("Total unhappy guests: " + unhappyGuests.size() + "\n");
        } else {
            System.out.println("All guests were satisfied!\n");
        }
    }
}
