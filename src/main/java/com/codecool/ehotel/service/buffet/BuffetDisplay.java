package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.util.*;
import java.time.Instant;

public class BuffetDisplay {
    public static void displayBuffetSupply(Buffet buffet) {
        System.out.println("Buffet supply contains:");
        for (Map.Entry<MealType, List<MealPortion>> entry : buffet.getMealPortionsMap().entrySet()) {
            MealType mealType = entry.getKey();
            List<MealPortion> mealPortions = entry.getValue();
            int portionCount = mealPortions.size();
            System.out.println(portionCount + " portion(s) of " + mealType);
            for (MealPortion mealPortion : mealPortions) {
                Instant timestamp = mealPortion.getTimestamp();
                System.out.println("- Created at: " + timestamp);
            }
        }
        System.out.println("Total portions in the buffet: " + buffet.getMealPortionsMap().values().stream().mapToInt(List::size).sum());
    }
}
