package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.Instant;
import java.util.Map;

public interface BuffetService {
    void refillBuffet(Buffet buffet, Map<MealType, Integer> portionCounts, Instant timestamp);

    boolean consumeFreshest(Buffet buffet, MealType mealType);

    int collectWaste(Buffet buffet, MealDurability mealDurability, Instant timestamp);
    int collectWaste(Buffet buffet, MealDurability mealDurability);
}
