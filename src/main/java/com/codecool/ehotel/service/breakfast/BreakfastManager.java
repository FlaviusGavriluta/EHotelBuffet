package com.codecool.ehotel.service.breakfast;


import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BreakfastManager {
    public static void serve(List<List<Guest>> guests, Buffet buffet) {
        BuffetService buffetService = new BuffetServiceImpl();
        int counter = 0;
        for (List<Guest> guestGroup : guests) {
            List<MealPortion> mealSHORT = new ArrayList<>();
            for (Guest guest : guestGroup) {

                buffetService.refillBuffet(buffet, List.of(
                        new BuffetService.RefillRequest(MealType.PANCAKE, 1),
                        new BuffetService.RefillRequest(MealType.CROISSANT, 1),
                        new BuffetService.RefillRequest(MealType.CEREAL, 1)
                ));
                System.out.println(buffet.getMealPortions());
                if (guest.guestType().getMealPreferences().contains(MealType.PANCAKE)) {
                    if (buffetService.consumeFreshest(buffet, MealType.PANCAKE)) {
                        buffetService.consumeFreshest(buffet, MealType.PANCAKE);
                        System.out.println(guest.name() + " ate a pancake");
                    }
                }
                if (guest.guestType().getMealPreferences().contains(MealType.CROISSANT)) {
                    if (buffetService.consumeFreshest(buffet, MealType.CROISSANT)) {
                        System.out.println(guest.name() + " ate a croissant");
                    }
                }
                if (guest.guestType().getMealPreferences().contains(MealType.CEREAL)) {
                    if (buffetService.consumeFreshest(buffet, MealType.CEREAL)) {
                        System.out.println(guest.name() + " ate a cereal");
                    }
                }
            }
            counter++;
            for (MealPortion meal : buffet.getMealPortions()) {
                if (meal.getMealType().getDurability() == MealDurability.SHORT) {
                    mealSHORT.add(meal);
                }
            }
            if (counter % 3 == 0) {
                buffetService.collectWaste(buffet, MealType.PANCAKE.getDurability(), LocalDateTime.now());
            }
            System.out.println("Meal SHORT: " + mealSHORT);
        }
    }
}