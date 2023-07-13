package com.codecool.ehotel.model;

import java.time.Instant;

public record MealPortion(MealType mealType, Instant timestamp) {

    public MealPortion(MealType mealType) {
        this(mealType, Instant.now());
    }

    public MealType getMealType() {
        return mealType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

