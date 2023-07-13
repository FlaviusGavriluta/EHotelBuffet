package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOptimalPortions {
    public static int[] getOptimalPortions(Buffet buffet, int[] guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
        int[] refillAmounts = new int[MealType.values().length];

        for (GuestType guestType : GuestType.values()) {
            List<MealType> mealPreferences = guestType.getMealPreferences();
            MealType preferredMealType = guestType.getPreferredMealType();

            int guestsToExpectForType = guestsToExpect[guestType.ordinal()];

            for (MealType mealType : mealPreferences) {
                refillAmounts[mealType.ordinal()] += guestsToExpectForType;
            }

            // Set the preferred meal type to the maximum value
            refillAmounts[preferredMealType.ordinal()] = guestsToExpectForType * cyclesLeft;
        }

        double initialCost = calculateCost(buffet, refillAmounts, costOfUnhappyGuest);
        boolean optimized = false;

        while (!optimized) {
            optimized = true;

            for (MealType mealType : MealType.values()) {
                int currentRefillAmount = refillAmounts[mealType.ordinal()];

                if (currentRefillAmount == 0) {
                    continue;
                }

                refillAmounts[mealType.ordinal()] = Math.max(0, currentRefillAmount - 1);
                double newCost = calculateCost(buffet, refillAmounts, costOfUnhappyGuest);

                if (newCost < initialCost) {
                    initialCost = newCost;
                    optimized = false;
                } else {
                    refillAmounts[mealType.ordinal()] = currentRefillAmount;
                }
            }
        }

        return refillAmounts;
    }


    private static double calculateCost(Buffet buffet, int[] refillAmounts, double costOfUnhappyGuest) {
        double totalCost = 0;

        // Calculate the cost of food waste and unhappy guests combined
        for (MealType mealType : MealType.values()) {
            int refillAmount = refillAmounts[mealType.ordinal()];

            // Calculate the number of unhappy guests based on the refill amount
            int unhappyGuests = buffet.calculateUnhappyGuests(mealType, refillAmount);

            // Calculate the potential food waste if all portions are refilled
            int potentialFoodWaste = buffet.calculatePotentialFoodWaste(mealType, refillAmount);

            // Calculate the cost of food waste and unhappy guests for this meal type
            double mealCost = (unhappyGuests * costOfUnhappyGuest) + potentialFoodWaste;
            totalCost += mealCost;
        }

        return totalCost;
    }
}