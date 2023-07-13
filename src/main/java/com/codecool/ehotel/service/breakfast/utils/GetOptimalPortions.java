package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.*;

public class GetOptimalPortions {
    public static List<BuffetService.RefillRequest> getOptimalPortions(Map<GuestType, Integer> guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
        List<BuffetService.RefillRequest> refillRequests = new ArrayList<>();

        for (Map.Entry<GuestType, Integer> entry : guestsToExpect.entrySet()) {
            GuestType guestType = entry.getKey();
            List<MealType> mealTypes = guestType.getMealPreferences();
            Integer numberOfGuests = entry.getValue();
            Optional<MealType> cheapestMeal = mealTypes.stream()
                    .min(Comparator.comparingDouble(MealType::getMealCost));

            if (cheapestMeal.isPresent()) {
                MealType mealWithLowestCost = cheapestMeal.get();
                if (cyclesLeft > 3) {
                    if (mealWithLowestCost.getMealCost() < costOfUnhappyGuest) {
                        refillRequests.add(new BuffetService.RefillRequest(mealWithLowestCost, numberOfGuests));
                    }
                } else {
                    refillRequests.add(new BuffetService.RefillRequest(mealWithLowestCost, numberOfGuests));
                }
            }
        }

        return refillRequests;
    }
}
