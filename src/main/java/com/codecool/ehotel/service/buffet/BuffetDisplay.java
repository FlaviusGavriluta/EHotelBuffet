package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.*;

import static java.lang.System.out;

import java.util.*;

public class BuffetDisplay {
    public static void displayBuffetSupply(Buffet buffet) {
        out.println("Buffet supply contains:");
        for (Map.Entry<MealType, List<MealPortion>> entry : buffet.getMealPortionsMap().entrySet()) {
            MealType mealType = entry.getKey();
            List<MealPortion> mealPortions = entry.getValue();
            int portionCount = mealPortions.size();
            out.println(portionCount + " portion(s) of " + mealType);
        }
    }

    public static int collectAndPrintWasteCost(Buffet buffet) {
        BuffetService buffetService = new BuffetServiceImpl();
        int costShort = buffetService.collectWaste(buffet, MealDurability.SHORT);
        int costMedium = buffetService.collectWaste(buffet, MealDurability.MEDIUM);
        int totalCost = costShort + costMedium;
        out.println("Discarded non-long durability meals after breakfast. Total cost: $" + totalCost);
        return totalCost;
    }
}
