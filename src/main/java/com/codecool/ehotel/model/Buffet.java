package com.codecool.ehotel.model;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Buffet {
    private final List<MealPortion> mealPortions; // Lista de porții de mâncare
    private final List<Guest> guests; // Lista de oaspeți

    public Buffet(List<Guest> guests) {
        this.mealPortions = new ArrayList<>(); // Inițializat lista de porții de mâncare
        this.guests = guests; // Inițializat lista de oaspeți
    }

    public void addMealPortion(MealPortion mealPortion) {
        mealPortions.add(mealPortion);
    }

    public void removeMealPortion(MealPortion mealPortion) {
        mealPortions.remove(mealPortion);
    }

    public List<MealPortion> getMealPortions() {
        return mealPortions;
    }

    public List<MealPortion> getMealPortionsByType(MealType mealType) {
        return mealPortions.stream()
                .filter(portion -> portion.mealType().equals(mealType))
                .sorted(Comparator.comparing(MealPortion::timestamp))
                .collect(Collectors.toList());
    }

    public int getAvailablePortions(MealType mealType) {
        int availablePortions = 0;
        for (MealPortion mealPortion : mealPortions) {
            if (mealPortion.getMealType() == mealType && !mealPortion.isConsumed()) {
                availablePortions++;
            }
        }
        return availablePortions;
    }

    public int calculatePotentialFoodWaste(MealType mealType, int refillAmount) {
        int currentPortions = getAvailablePortions(mealType);
        int guestsExpected = getExpectedGuestsForMealType(mealType);
        int totalPortions = currentPortions + refillAmount;
        int potentialFoodWaste = Math.max(totalPortions - guestsExpected, 0);
        return potentialFoodWaste;
    }

    public int calculateUnhappyGuests(MealType mealType, int refillAmount) {
        int currentPortions = getAvailablePortions(mealType);
        int guestsExpected = getExpectedGuestsForMealType(mealType);
        int totalPortions = currentPortions + refillAmount;
        int unhappyGuests = Math.max(guestsExpected - totalPortions, 0);
        return unhappyGuests;
    }

    public int getExpectedGuestsForMealType(MealType mealType) {
        int expectedGuests = 0;
        for (Guest guest : guests) {
                if (guest.guestType().getMealPreferences().contains(mealType)) {
                    expectedGuests++;
                }
                    }
        return expectedGuests;
    }

    @Override
    public String toString() {
        return "Buffet{" +
                "mealPortions=" + mealPortions +
                '}';
    }
}
