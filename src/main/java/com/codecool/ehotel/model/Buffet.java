package com.codecool.ehotel.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public record Buffet(Map<MealType, List<MealPortion>> mealPortions) {

    public Buffet() {
        this(new HashMap<>());
    }

    public void addMealPortion(MealType mealType, MealPortion mealPortion) {
        mealPortions.computeIfAbsent(mealType, k -> new ArrayList<>()).add(mealPortion);
    }

    public List<MealPortion> getMealPortionsByType(MealType mealType) {
        return mealPortions.getOrDefault(mealType, new ArrayList<>());
    }

    public List<MealPortion> getMealPortionsByTypeOrderedByFreshness(MealType mealType) {
        List<MealPortion> portions = mealPortions.getOrDefault(mealType, new ArrayList<>());
        portions.sort(Comparator.comparing(MealPortion::getTimestamp));
        return portions;
    }
}
