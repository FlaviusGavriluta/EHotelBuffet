package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.breakfast.utils.GetOptimalPortions;
import com.codecool.ehotel.service.guest.GuestGenerator;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import static com.codecool.ehotel.EHotelBuffetApplication.guestsToExpect;

import java.util.List;

public class BuffetRefillOptimizer {
    public static int[] runOptimization() {
        GuestService guestService = new GuestServiceImpl();
        List<Guest> generateGuests = GuestGenerator.generateGuests(guestService, guestsToExpect); // Generate guests
        Buffet buffet = new Buffet(generateGuests); // Initialize the buffet state

        // Guests to expect per guest type
        int cyclesLeft = 8; // Cycles left for the day
        double costOfUnhappyGuest = 10.0; // Assumed cost of an unhappy guest

        int[] refillAmounts = GetOptimalPortions.getOptimalPortions(buffet, guestsToExpect, cyclesLeft, costOfUnhappyGuest);

        return refillAmounts;
    }
}