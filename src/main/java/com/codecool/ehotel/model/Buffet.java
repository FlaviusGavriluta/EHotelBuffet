package com.codecool.ehotel.model;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Buffet {
    private final List<MealPortion> mealPortions;

    public Buffet() {
        this.mealPortions = new ArrayList<>();
    }

    public void addMealPortion(MealPortion mealPortion) {
        mealPortions.add(mealPortion);
    }

    public List<MealPortion> getMealPortionsByType(MealType mealType) {
        return mealPortions.stream()
                .filter(portion -> portion.mealType().equals(mealType))
                .sorted(Comparator.comparing(MealPortion::timestamp))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Buffet{" +
                "mealPortions=" + mealPortions +
                '}';
    }

    public void removeMealPortion(MealPortion mealPortion) {
        mealPortions.remove(mealPortion);
    }

    public List<MealPortion> getMealPortions() {
        return mealPortions;
    }
}
