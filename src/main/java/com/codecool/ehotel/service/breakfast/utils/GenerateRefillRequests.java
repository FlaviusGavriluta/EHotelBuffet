package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRefillRequests {
    static List<BuffetService.RefillRequest> generateRefillRequests() {
        List<BuffetService.RefillRequest> refillRequests = new ArrayList<>();

        Random random = new Random();
        MealType[] mealTypes = MealType.values();
        int numberOfRequests = random.nextInt(5) + 1; // Generate 1 to 5 refill requests

        for (int i = 0; i < numberOfRequests; i++) {
            MealType randomMealType = mealTypes[random.nextInt(mealTypes.length)];
            int randomAmount = random.nextInt(5) + 1; // Generate 1 to 5 desired amounts

            refillRequests.add(new BuffetService.RefillRequest(randomMealType, randomAmount));
        }
        return refillRequests;
    }
}
