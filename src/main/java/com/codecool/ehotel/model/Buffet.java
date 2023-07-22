package com.codecool.ehotel.model;

import java.util.*;

public class Buffet {
    private Map<MealType, List<MealPortion>> mealPortionsMap;

    public Buffet() {
        this.mealPortionsMap = new HashMap<>();
    }

    public void addMealPortion(MealType mealType, MealPortion mealPortion) {
        mealPortionsMap.computeIfAbsent(mealType, k -> new ArrayList<>()).add(mealPortion);
    }

    public void removeMealPortion(MealType mealType, MealPortion mealPortion) {
        List<MealPortion> mealPortions = mealPortionsMap.get(mealType);
        if (mealPortions != null) {
            mealPortions.remove(mealPortion);
        }
    }

    public List<MealPortion> getMealPortionsByType(MealType mealType) {
        return mealPortionsMap.getOrDefault(mealType, new ArrayList<>());
    }

    public List<MealPortion> getMealPortionsByTypeOrderedByFreshness(MealType mealType) {
        List<MealPortion> portions = mealPortionsMap.getOrDefault(mealType, new ArrayList<>());
        portions.sort(Comparator.comparing(MealPortion::getTimestamp));
        return portions;
    }

    public Map<MealType, List<MealPortion>> getMealPortionsMap() {
        return mealPortionsMap;
    }

    public int estimateMaxPortions() {
        int totalCapacity = 0;
        for (List<MealPortion> mealPortions : mealPortionsMap.values()) {
            totalCapacity += mealPortions.size();
        }
        return totalCapacity;
    }

    public int estimateMaxPortionsToAdd(int maxCapacity) {
        int currentCapacity = estimateMaxPortions();
        int maxPortionsToAdd = Math.max(0, maxCapacity - currentCapacity);
        return maxPortionsToAdd;
    }
}
