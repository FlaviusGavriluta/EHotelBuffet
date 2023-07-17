package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.List;

public class BreakfastServer {
    public static void serveBreakfastToGuest(List<Guest> guests, Buffet buffet, BuffetService buffetService) {
        System.out.println("=== Serving breakfast to guests ===");
        for (Guest guest : guests) {
            GuestType guestType = guest.guestType();
            List<MealType> preferences = guestType.getMealPreferences();

            boolean foundPreferredMeal = false;

            for (MealType mealType : preferences) {
                if (buffetService.consumeFreshest(buffet, mealType)) {
                    System.out.println(guest.name() + " has eaten " + mealType);
                    foundPreferredMeal = true;
                    break;
                }
            }
            if (!foundPreferredMeal) {
                System.out.println(guest.name() + " has eaten nothing");
            }
        }
    }
}
