package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealType;

import java.util.Collection;

public interface BuffetService {

    void refillBuffet(Buffet buffet, Collection<RefillRequest> refillRequests);

    record RefillRequest(MealType mealType, int amount) {
    }
}
