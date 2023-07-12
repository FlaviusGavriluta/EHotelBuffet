package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealPortion;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class BuffetServiceImpl implements BuffetService {
    @Override
    public void refillBuffet(Buffet buffet, Collection<RefillRequest> refillRequests, LocalDateTime refillTimestamp) {
        List<MealPortion> mealPortions = new LinkedList<>();
        refillRequests.stream()
                .flatMap(refillRequest -> IntStream.range(0, refillRequest.amount())
                        .mapToObj(i -> new MealPortion(refillRequest.mealType(), refillTimestamp)))
                .forEach(buffet::addMealPortion);
    }

    @Override
    public boolean consumeFreshest(Buffet buffet, MealType mealType) {
        List<MealPortion> mealPortionsByType = buffet.getMealPortionsByType(mealType);
        if (!mealPortionsByType.isEmpty()) {
            buffet.removeMealPortion(mealPortionsByType.get(mealPortionsByType.size() - 1));
            return true;
        }
        return false;
    }
}
