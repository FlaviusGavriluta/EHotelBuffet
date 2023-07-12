package com.codecool.ehotel.model;

import static com.codecool.ehotel.model.MealDurability.*;

public enum MealType {
    SCRAMBLED_EGGS(70, SHORT),
    SUNNY_SIDE_UP(70, SHORT),
    FRIED_SAUSAGE(100, SHORT),
    FRIED_BACON(70, SHORT),
    PANCAKE(40, SHORT),
    CROISSANT(40, SHORT),
    MASHED_POTATO(20, MEDIUM),
    MUFFIN(20, MEDIUM),
    BUN(10, MEDIUM),
    CEREAL(30, LONG),
    MILK(10, LONG);
    private int cost;
    private MealDurability mealDurability;

    MealType(int cost, MealDurability mealDurability) {
        this.cost = cost;
        this.mealDurability = mealDurability;
    }

    public int getCost() {
        return cost;
    }

    public MealDurability getDurability() {
        return mealDurability;
    }

    @Override
    public String toString() {
        return MealType.this.name();
    }
}



//    public static int[] getOptimalPortions(Buffet buffet, int[] guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
//        int[] refillAmounts = new int[MealType.values().length];
//
//        // Calculate the total number of portions needed for each meal type
//        for (MealType mealType : MealType.values()) {
//            int remainingGuests = guestsToExpect[mealType.ordinal()];
//            int totalPortions = remainingGuests * cyclesLeft;
//            refillAmounts[mealType.ordinal()] = totalPortions;
//        }
//
//        // Adjust refill amounts based on trade-off
//        for (MealType mealType : MealType.values()) {
//            int refillAmount = refillAmounts[mealType.ordinal()];
//
//            // Calculate the number of unhappy guests based on the refill amount
//            int unhappyGuests = buffet.calculateUnhappyGuests(mealType, refillAmount);
//
//            // Calculate the potential food waste if all portions are refilled
//            int potentialFoodWaste = buffet.calculatePotentialFoodWaste(mealType, refillAmount);
//
//            // Calculate the cost of food waste and unhappy guests combined
//            double totalCost = (unhappyGuests * costOfUnhappyGuest) + potentialFoodWaste;
//
//            // Adjust the refill amount based on the cost of food waste and unhappy guests
//            if (totalCost > 0) {
//                double adjustedRefillAmount = refillAmount - (totalCost / costOfUnhappyGuest);
//                refillAmounts[mealType.ordinal()] = Math.max(0, (int) Math.ceil(adjustedRefillAmount));
//            }
//        }
//
//        return refillAmounts;
//    }
//public class BuffetRefillOptimizer {
//    public static void main(String[] args) {
//        Buffet buffet = new Buffet(); // Initialize the buffet state
//
//        int[] guestsToExpect = {6, 4, 2}; // Guests to expect per guest type
//        int cyclesLeft = 8; // Cycles left for the day
//        double costOfUnhappyGuest = 10.0; // Assumed cost of an unhappy guest
//
//        int[] refillAmounts = getOptimalPortions(buffet, guestsToExpect, cyclesLeft, costOfUnhappyGuest);
//
//        System.out.println("Refill Amounts:");
//        for (MealType mealType : MealType.values()) {
//            System.out.println(mealType + ": " + refillAmounts[mealType.ordinal()]);
//        }
//    }
//
//    public static int[] getOptimalPortions(Buffet buffet, int[] guestsToExpect, int cyclesLeft, double costOfUnhappyGuest) {
//        int[] refillAmounts = new int[MealType.values().length];
//
//        // Calculate the total number of portions needed for each meal type
//        for (MealType mealType : MealType.values()) {
//            int remainingGuests = guestsToExpect[mealType.ordinal()];
//            int totalPortions = remainingGuests * cyclesLeft;
//            refillAmounts[mealType.ordinal()] = totalPortions;
//        }
//
//        // Adjust refill amounts based on trade-off
//        // You can implement your own logic here based on the costOfUnhappyGuest parameter
//
//        return refillAmounts;
//    }
//}