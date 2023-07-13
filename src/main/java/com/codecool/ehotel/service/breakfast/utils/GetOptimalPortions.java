package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.*;
import java.util.stream.Collectors;

public class GetOptimalPortions {
    public static List<BuffetService.RefillRequest> getOptimalPortions(Map<GuestType, Integer> guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
        return guestsToExpect.entrySet().stream()
                .flatMap(entry -> {
                    GuestType guestType = entry.getKey();
                    List<MealType> mealTypes = guestType.getMealPreferences();
                    Integer numberOfGuests = entry.getValue();
                    Optional<MealType> cheapestMeal = mealTypes.stream()
                            .min(Comparator.comparingDouble(MealType::getMealCost));

                    return cheapestMeal.stream()
                            .filter(meal -> cyclesLeft > 3 || meal.getMealCost() < costOfUnhappyGuest)
                            .map(meal -> new BuffetService.RefillRequest(meal, numberOfGuests));
                })
                .collect(Collectors.toList());
    }
}
