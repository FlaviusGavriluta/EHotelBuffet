package com.codecool.ehotel.service.breakfast.utils;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.service.buffet.BuffetService;

import java.time.LocalDateTime;
import java.util.List;

import static com.codecool.ehotel.service.breakfast.utils.GenerateRefillRequests.generateRefillRequests;

public class RefillBuffet {
    public static void refillBuffet(Buffet buffet, BuffetService buffetService, List<BuffetService.RefillRequest> refillRequests, LocalDateTime currentTime) {
        buffetService.refillBuffet(buffet, refillRequests, currentTime);
    }
}
