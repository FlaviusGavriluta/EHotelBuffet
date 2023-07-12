package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.breakfast.utils.GetOptimalPortions;
import com.codecool.ehotel.service.guest.GuestGenerator;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import java.util.List;

public class BuffetRefillOptimizer {
    public static int[] runOptimization() {
        GuestService guestService = new GuestServiceImpl();
        List<Guest> generateGuests = GuestGenerator.generateGuests(guestService); // Generate guests
        Buffet buffet = new Buffet(generateGuests); // Initialize the buffet state

        int[] guestsToExpect = {6, 4, 2}; // Guests to expect per guest type
        int cyclesLeft = 8; // Cycles left for the day
        double costOfUnhappyGuest = 10.0; // Assumed cost of an unhappy guest

        int[] refillAmounts = GetOptimalPortions.getOptimalPortions(buffet, guestsToExpect, cyclesLeft, costOfUnhappyGuest);

        return refillAmounts;
    }

    public static int[] getOptimalPortions(Buffet buffet, int[] guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
        int[] refillAmounts = new int[MealType.values().length];

        // Calculate the total number of portions needed for each meal type
        for (MealType mealType : MealType.values()) {
            int remainingGuests = guestsToExpect[mealType.ordinal()];
            int totalPortions = remainingGuests * cyclesLeft;
            refillAmounts[mealType.ordinal()] = totalPortions;
        }

        // Adjust refill amounts based on trade-off
        for (MealType mealType : MealType.values()) {
            int refillAmount = refillAmounts[mealType.ordinal()];

            // Calculate the number of unhappy guests based on the refill amount
            int unhappyGuests = buffet.calculateUnhappyGuests(mealType, refillAmount);

            // Calculate the potential food waste if all portions are refilled
            int potentialFoodWaste = buffet.calculatePotentialFoodWaste(mealType, refillAmount);

            // Calculate the cost of food waste and unhappy guests combined
            double totalCost = (unhappyGuests * costOfUnhappyGuest) + potentialFoodWaste;

            // Adjust the refill amount based on the cost of food waste and unhappy guests
            if (totalCost > 0) {
                double adjustedRefillAmount = refillAmount - (totalCost / costOfUnhappyGuest);
                refillAmounts[mealType.ordinal()] = Math.max(0, (int) Math.ceil(adjustedRefillAmount));
            }
        }

        return refillAmounts;
    }
}