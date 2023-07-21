package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalPortionsOptimizer {
    public Map<MealType, Integer> getOptimalPortions(Buffet buffet, Map<GuestType, Integer> remainingGuests, int cyclesLeft, int unhappyGuestCost) {
        Map<MealType, Integer> optimalPortions = new HashMap<>();

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

            optimalPortions.put(mealType, portionsRequired);
        }

        return optimalPortions;
    }
}
