package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOptimalPortions {
    public static List<BuffetService.RefillRequest> getOptimalPortions(Map<GuestType, Integer> guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
        List<BuffetService.RefillRequest> refillRequests = new ArrayList<>();

        for (Map.Entry<GuestType, Integer> entry : guestsToExpect.entrySet()) {
            GuestType guestType = entry.getKey();
            List<MealType> mealTypes = guestType.getMealPreferences();
            Integer numberOfGuests = entry.getValue();

            for (MealType mealType : mealTypes) {
                if (cyclesLeft > 3) {
                    if (mealType.getMealCost() < costOfUnhappyGuest) {
                        refillRequests.add(new BuffetService.RefillRequest(mealType, numberOfGuests));
                    }
                } else {
                    refillRequests.add(new BuffetService.RefillRequest(mealType, numberOfGuests));
                }
            }
        }

        return refillRequests;
    }
}
