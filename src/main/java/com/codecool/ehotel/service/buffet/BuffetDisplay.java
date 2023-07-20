package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import java.util.*;

public class BuffetDisplay {
    public static void displayBuffetSupply(Buffet buffet) {
        System.out.println("Buffet supply contains:");
        for (Map.Entry<MealType, List<MealPortion>> entry : buffet.getMealPortionsMap().entrySet()) {
            MealType mealType = entry.getKey();
            List<MealPortion> mealPortions = entry.getValue();
            int portionCount = mealPortions.size();
            System.out.println(portionCount + " portion(s) of " + mealType);
        }
    }

    public static int collectAndPrintWasteCost(Buffet buffet) {
        BuffetService buffetService = new BuffetServiceImpl();
        int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT);
        int costMedium = buffetService.collectWaste(buffet, MealDurability.MEDIUM);
        int totalCost = costShort + costMedium;
        System.out.println("Discarded non-long durability meals after breakfast. Total cost: $" + totalCost);
        return totalCost;
    }
}
