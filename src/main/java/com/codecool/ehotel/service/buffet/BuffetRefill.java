package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;

import java.time.Instant;

public class BuffetRefill {
    public static void addMealPortionsToBuffet(Buffet buffet, MealType mealType, int portionCount, Instant timestamp) {
        for (int i = 0; i < portionCount; i++) {
            MealPortion mealPortion = new MealPortion(mealType, timestamp);
            buffet.addMealPortion(mealType, mealPortion);
        }
    }
}
