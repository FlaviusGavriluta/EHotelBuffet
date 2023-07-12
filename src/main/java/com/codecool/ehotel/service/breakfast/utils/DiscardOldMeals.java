package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.breakfast.BreakfastManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscardOldMeals {
    public static void discardOldMeals(Buffet buffet, BuffetService buffetService, LocalDateTime currentTime) {
        for (MealType mealType : MealType.values()) {
            List<MealPortion> mealPortions = buffet.getMealPortionsByType(mealType);
            List<MealPortion> mealsToDiscard = new ArrayList<>();
            for (MealPortion mealPortion : mealPortions) {
                // Discard SHORT durability meals that are older than 90 minutes
                if (mealPortion.getMealType().getMealDurability() == MealDurability.SHORT
                        && mealPortion.getTimestamp().plusMinutes(90).isBefore(currentTime)) {
                    System.out.println("Discarded meal: " + mealPortion.getMealType().name());
                    mealsToDiscard.add(mealPortion);
                }
            }
            // Remove the collected meals from the buffet
            for (MealPortion meal : mealsToDiscard) {
                buffet.removeMealPortion(meal);
                BreakfastManager.costOfFoodWaste += meal.getMealType().getMealCost();
            }
        }
    }
}
