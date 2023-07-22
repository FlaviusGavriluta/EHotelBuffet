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
        int index = 0;

        for (MealType mealType : MealType.values()) {
            int portionsRequired = 0;
            for (GuestType guestType : GuestType.values()) {
                int guestsExpected = remainingGuests.getOrDefault(guestType, 0);
                List<MealPortion> mealPortions = buffet.getMealPortionsByType(mealType);
                int portionsAvailable = mealPortions.size();
                int portionsPerGuest = guestType.getMealPreferences().contains(mealType) ? 1 : 0;

                if (portionsPerGuest == 0 || guestsExpected == 0) {
                    continue;
                }

                int portionsNeeded = guestsExpected * portionsPerGuest;
                portionsRequired += Math.max(0, portionsNeeded - portionsAvailable);
            }

            // Calculate the value for each meal type (number of unhappy guests we can avoid)
            int value = (int) Math.pow(unhappyGuestCost, cyclesLeft) * portionsRequired;
            values[index] = value;

            // Calculate the weight for each meal type (number of new portions needed)
            int weight = portionsRequired;
            weights[index] = weight;

            index++;
        }

        // Use 0-1 Knapsack algorithm to find the optimal portions
        int[] dp = new int[maxCapacity + 1];
        int[][] path = new int[MealType.values().length][maxCapacity + 1];

        for (int i = 0; i < MealType.values().length; i++) {
            for (int j = maxCapacity; j >= weights[i]; j--) {
                if (dp[j] < dp[j - weights[i]] + values[i]) {
                    dp[j] = dp[j - weights[i]] + values[i];
                    path[i][j] = 1;
                }
            }
        }

        // Reconstruct the optimal portions
        for (int i = MealType.values().length - 1, j = maxCapacity; i >= 0; i--) {
            if (path[i][j] == 1) {
                MealType mealType = MealType.values()[i];
                int portionsRequired = 0;
                for (GuestType guestType : GuestType.values()) {
                    int guestsExpected = remainingGuests.getOrDefault(guestType, 0);
                    List<MealPortion> mealPortions = buffet.getMealPortionsByType(mealType);
                    int portionsAvailable = mealPortions.size();
                    int portionsPerGuest = guestType.getMealPreferences().contains(mealType) ? 1 : 0;

                    if (portionsPerGuest == 0 || guestsExpected == 0) {
                        continue;
                    }

                    int portionsNeeded = guestsExpected * portionsPerGuest;
                    portionsRequired += Math.max(0, portionsNeeded - portionsAvailable);
                }
                optimalPortions.put(mealType, portionsRequired);
                j -= weights[i];
            }
        }

        return optimalPortions;
    }
}