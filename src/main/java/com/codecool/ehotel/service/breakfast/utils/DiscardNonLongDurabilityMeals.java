package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.service.breakfast.BreakfastManager;

import java.util.Iterator;
import java.util.List;

public class DiscardNonLongDurabilityMeals {
    public static void discardNonLongDurabilityMeals(Buffet buffet) {
        List<MealPortion> mealPortions = buffet.getMealPortions();
        Iterator<MealPortion> iterator = mealPortions.iterator();

        while (iterator.hasNext()) {
            MealPortion mealPortion = iterator.next();

            if (mealPortion.getMealType().getDurability() != MealDurability.LONG) {
                iterator.remove();
                BreakfastManager.costOfFoodWaste += mealPortion.getMealType().getCost();
            }
        }
    }
}
