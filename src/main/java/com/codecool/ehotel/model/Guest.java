package com.codecool.ehotel.model;

import java.time.LocalDate;

public record Guest(String name, GuestType guestType, LocalDate checkIn, LocalDate checkOut) {

}
