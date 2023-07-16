package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.time.Instant;

public class MealDiscarder {
    private static int wasteCost = 0;

    public static int discardOldMeals(Buffet buffet, BuffetService buffetService) {
        Instant discardTime = Instant.now().minusSeconds(5400); // 90 minutes ago
        wasteCost = buffetService.collectWaste(buffet, MealDurability.SHORT, discardTime);
        System.out.println("Discarded old SHORT meals. Total cost: $" + wasteCost);
        return wasteCost;
    }

    public static int discardNonLongDurabilityMeals(Buffet buffet, BuffetService buffetService) {
        for (MealType mealType : buffet.getMealPortionsMap().keySet()) {
            mealType.getDurability(); //test personal
            if (mealType.getDurability() != MealDurability.LONG) {
                wasteCost = buffetService.collectWaste(buffet, mealType.getDurability(), Instant.now());
            }
        }
        System.out.println("Discarded non-long durability meals at the end of the day. Total cost: $" + wasteCost);
        return wasteCost;
    }
}
