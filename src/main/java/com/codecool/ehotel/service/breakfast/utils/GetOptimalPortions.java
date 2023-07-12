package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;

import java.util.ArrayList;
import java.util.List;

public class GetOptimalPortions {

public static List<MealType> getOptimalPortions(Buffet buffet, List<List<Guest>> guestsOfTheDay, int cycleCount, double value ) {
    List<MealType> allPreferenceList = new ArrayList<>();
    for (List<Guest> guestGroup : guestsOfTheDay ) {
            for ( Guest guest : guestGroup) {

        }
    }
    return allPreferenceList;
}

}
