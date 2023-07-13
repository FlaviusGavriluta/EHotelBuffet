package com.codecool.ehotel.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Buffet {
    private final Map<MealType, List<MealPortion>> mealPortions;
    private final List<Guest> guests;

    public Buffet(List<Guest> guests) {
        this.mealPortions = new HashMap<>();
        this.guests = guests;
        initializeMealPortions();
    }

    private void initializeMealPortions() {
        for (MealType mealType : MealType.values()) {
            mealPortions.put(mealType, new ArrayList<>());
        }
        for (Guest guest : guests) {
            for (MealType mealType : guest.guestType().getMealPreferences()) {
                mealPortions.get(mealType).add(new MealPortion(mealType, LocalDateTime.now()));
            }
        }
    }

    public void addMealPortion(MealPortion mealPortion) {
        MealType mealType = mealPortion.getMealType();
        mealPortions.get(mealType).add(mealPortion);
    }

    public void removeMealPortion(MealPortion mealPortion) {
        MealType mealType = mealPortion.getMealType();
        mealPortions.get(mealType).remove(mealPortion);
    }

    public List<MealPortion> getMealPortionsByType(MealType mealType) {
        return mealPortions.get(mealType);
    }

    public int getAvailablePortions(MealType mealType) {
        List<MealPortion> portions = mealPortions.getOrDefault(mealType, new ArrayList<>());
        return portions.size();
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

    public void setRefillAmount(MealType mealType, int refillAmount) {
        List<MealPortion> portions = mealPortions.getOrDefault(mealType, new ArrayList<>());
        int currentPortions = portions.size();
        if (currentPortions > refillAmount) {
            for (int i = currentPortions - 1; i >= refillAmount; i--) {
                portions.remove(i);
            }
        } else if (currentPortions < refillAmount) {
            for (int i = currentPortions; i < refillAmount; i++) {
                portions.add(new MealPortion(mealType, LocalDateTime.now()));
            }
        }
    }

    @Override
    public String toString() {
        return "Buffet{" +
                "mealPortions=" + mealPortions +
                '}';
    }
}