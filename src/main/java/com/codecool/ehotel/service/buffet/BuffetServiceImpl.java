package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BuffetServiceImpl implements BuffetService {


    @Override
    public void refillBuffet(Buffet buffet, Collection<RefillRequest> refillRequests) {
        List<MealPortion> mealPortions = new LinkedList<>();
        LocalDateTime refillTimestamp = LocalDateTime.now();

        for (RefillRequest refillRequest : refillRequests) {
            MealType mealType = refillRequest.mealType();
            int amount = refillRequest.amount();

            for (int i = 0; i < amount; i++) {
                mealPortions.add(new MealPortion(mealType, refillTimestamp));
            }
        }
        buffet.addMealPortion(mealPortions);
    }
}
