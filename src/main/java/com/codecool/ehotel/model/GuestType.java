package com.codecool.ehotel.model;

import java.util.List;

import static com.codecool.ehotel.model.MealType.*;

public enum GuestType {

    BUSINESS(List.of(SCRAMBLED_EGGS, FRIED_BACON, CROISSANT)),
    TOURIST(List.of(SUNNY_SIDE_UP, FRIED_SAUSAGE, MASHED_POTATO, BUN, MUFFIN)),
    KID(List.of(PANCAKE, MUFFIN, CEREAL, MILK));

    private List<MealType> mealPreferences;
    private MealType preferredMealType;

    GuestType(List<MealType> mealPreferences) {
        this.mealPreferences = mealPreferences;
        this.preferredMealType = mealPreferences.get(0);
    }

    public List<MealType> getMealPreferences() {
        return mealPreferences;
    }

    public MealType getPreferredMealType() {
        return preferredMealType;
    }

    public void setPreferredMealType(MealType preferredMealType) {
        this.preferredMealType = preferredMealType;
    }
}
