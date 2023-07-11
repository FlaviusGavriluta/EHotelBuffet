package com.codecool.ehotel.model;

import java.time.LocalDateTime;

public record MealPortion(MealType mealType, LocalDateTime timestamp) {

    public MealType getMealType() {
        return mealType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return mealType.toString();
    }
}