package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalPortionsOptimizer {
    public Map<MealType, Integer> getOptimalPortionsWithKnapsack(Buffet buffet, Map<GuestType, Integer> remainingGuests, int cyclesLeft, int unhappyGuestCost) {
        Map<MealType, Integer> optimalPortions = new HashMap<>();
        int maxCapacity = 20; // Maximum number of portions we can carry

        // Create arrays to store the values and weights (unhappy guests and portions needed) for each meal type
        int[] values = new int[MealType.values().length];
        int[] weights = new int[MealType.values().length];

        // Populate the values and weights arrays based on the meal types in the buffet
        List<MealType> allMealTypes = List.of(MealType.values());
        for (int i = 0; i < allMealTypes.size(); i++) {
            MealType mealType = allMealTypes.get(i);
            List<MealPortion> mealPortions = buffet.getMealPortionsByTypeOrderedByFreshness(mealType);

            int portionsNeeded = 0;
            for (GuestType guestType : remainingGuests.keySet()) {
                int guestsRemaining = remainingGuests.get(guestType);
                List<MealType> preferences = guestType.getMealPreferences();
                if (guestsRemaining > 0 && preferences.contains(mealType)) {
                    portionsNeeded += guestsRemaining;
                }
            }

            values[i] = unhappyGuestCost * portionsNeeded;
            weights[i] = mealPortions.size();
        }

        // Use 0-1 Knapsack algorithm to find the optimal portions
        int[][] dp = new int[MealType.values().length + 1][maxCapacity + 1];
        for (int i = 1; i <= MealType.values().length; i++) {
            for (int j = 0; j <= maxCapacity; j++) {
                if (weights[i - 1] <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Trace back the optimal portions using the dp array
        int i = MealType.values().length;
        int j = maxCapacity;
        while (i > 0 && j > 0) {
            if (dp[i][j] != dp[i - 1][j]) {
                MealType mealType = allMealTypes.get(i - 1);
                int portionsToAdd = 1;
                if (weights[i - 1] > 0) {
                    portionsToAdd = Math.min(j / weights[i - 1], remainingGuests.size());
                }
                optimalPortions.put(mealType, portionsToAdd);
                j -= portionsToAdd * weights[i - 1];
                i--;
            } else {
                i--;
            }
        }

        return optimalPortions;
    }
}