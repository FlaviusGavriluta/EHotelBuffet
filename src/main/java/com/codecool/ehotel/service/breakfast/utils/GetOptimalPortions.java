package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.*;

import java.util.ArrayList;
import java.util.List;

public class GetOptimalPortions {

    public static List<List<MealType>> getOptimalPortions(Buffet buffet, List<List<Guest>> guestsOfTheDay, int cycleCount, double value) {
        List<List<MealType>> allPreferenceList = new ArrayList<>();
//        List<GuestType> guestTypes = new ArrayList<>();
//
//        for(List<Guest> guestList: guestsOfTheDay) {
//            for(Guest guest2 : guestList) {
//                guestTypes.add(guest2.guestType());
//            }
//
//        }
//        System.out.println(guestTypes);
//        System.out.println(",,,,,,,,,,,,,,,");
        //going through each breakfast cycle group
        for (List<Guest> guestGroup : guestsOfTheDay) {
            //each guest of current cycle
            for (Guest guest : guestGroup) {
            allPreferenceList.add(guest.guestType().getMealPreferences());
             //   [ [1, 2, 3 ], [], [] ]
            }
        }

        List<MealType> mediumAndLong = new ArrayList<>();
        for (List<MealType> mealList : allPreferenceList) {
//            System.out.println(mealList);
//            System.out.println("............");
            for ( MealType meal : mealList) {
                if (meal.getDurability() == MealDurability.MEDIUM || meal.getDurability() == MealDurability.LONG ) {
                 // mealList
                }
            }
        }

        return allPreferenceList;
    }

}
