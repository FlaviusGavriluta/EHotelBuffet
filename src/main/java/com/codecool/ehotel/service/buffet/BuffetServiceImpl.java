package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BuffetServiceImpl implements BuffetService {
    @Override
    public void refillBuffet(Buffet buffet, Map<MealType, Integer> portionCounts, Instant timestamp) {
        for (Map.Entry<MealType, Integer> entry : portionCounts.entrySet()) {
            MealType mealType = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                MealPortion mealPortion = new MealPortion(mealType, timestamp);
                buffet.addMealPortion(mealType, mealPortion);
            }
        }
    }

    @Override
    public boolean consumeFreshest(Buffet buffet, MealType mealType) {
        List<MealPortion> mealPortions = buffet.getMealPortionsByTypeOrderedByFreshness(mealType);
        if(!mealPortions.isEmpty()) {
            MealPortion freshestPortion = mealPortions.get(0);
            buffet.removeMealPortion(mealType, freshestPortion);
            return true;
        }
        return false;
    }

    @Override
    public int collectWaste(Buffet buffet, MealDurability mealDurability, Instant time) {
        int totalCost = 0;
        for (List<MealPortion> mealPortions : buffet.getMealPortionsMap().values()) {
            Iterator<MealPortion> iterator = mealPortions.iterator();
            while (iterator.hasNext()) {
                MealPortion mealPortion = iterator.next();
                if (mealPortion.getMealType().getDurability() == mealDurability && mealPortion.getTimestamp().isBefore(time)) {
                    totalCost += mealPortion.getMealType().getCost();
                    iterator.remove();
                }
            }
        }
        return totalCost;
    }
}