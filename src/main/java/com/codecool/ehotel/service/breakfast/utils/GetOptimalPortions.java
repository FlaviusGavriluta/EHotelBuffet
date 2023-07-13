package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealType;

public class GetOptimalPortions {
    public static int[] getOptimalPortions(Buffet buffet, int[] guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
        int[] refillAmounts = new int[MealType.values().length]; // Refill amounts for each meal type

        // Initialize refill amounts with the maximum possible value
        for (int i = 0; i < MealType.values().length; i++) {
            MealType mealType = MealType.values()[i];
            refillAmounts[i] = guestsToExpect[mealType.ordinal()] * cyclesLeft;
        }

        // Calculate the initial cost based on maximum refill amounts
        double initialCost = calculateCost(buffet, refillAmounts, costOfUnhappyGuest);

        // Perform iterative optimization
        boolean optimized = false;
        while (!optimized) {
            optimized = true;

            // Try reducing refill amounts for each meal type
            for (MealType mealType : MealType.values()) {
                int currentRefillAmount = refillAmounts[mealType.ordinal()];

                // Skip if the current refill amount is already 0
                if (currentRefillAmount == 0) {
                    continue;
                }

                // Reduce the refill amount by 1 and calculate the new cost
                refillAmounts[mealType.ordinal()] = Math.max(0, currentRefillAmount - 1);
                double newCost = calculateCost(buffet, refillAmounts, costOfUnhappyGuest);

                // If the new cost is lower, keep the reduced refill amount
                if (newCost < initialCost) {
                    initialCost = newCost;
                    optimized = false; // Continue optimization
                } else {
                    // Otherwise, revert the refill amount to the previous value
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