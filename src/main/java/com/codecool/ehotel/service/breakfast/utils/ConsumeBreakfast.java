package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.List;

public class ConsumeBreakfast {
    public static int unhappyGuests;

    public static void consumeBreakfast(List<Guest> guests, Buffet buffet, BuffetService buffetService) {
        for (Guest guest : guests) {
            List<MealType> mealPreferences = guest.guestType().getMealPreferences();
            boolean foundPreferredMeal = false;
            for (MealType mealType : mealPreferences) {
                if (buffetService.consumeFreshest(buffet, mealType)) {
                    System.out.println(guest.name() + " ate " + mealType);
                    foundPreferredMeal = true;
                    break; // Exit the loop if a preferred meal is found
                }
            }
            if (!foundPreferredMeal) {
                unhappyGuests++;
                System.out.println(guest.name() + " couldn't find any of their preferred meals and went unhappy");
            }
        }
    }
}
