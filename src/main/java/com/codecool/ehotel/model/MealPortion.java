package com.codecool.ehotel.model;

import java.time.LocalDateTime;

public record MealPortion(MealType mealType, LocalDateTime timestamp) {

    public MealType getMealType() {
        return mealType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isConsumed() {
        // Verificați dacă porția de masă este expirată sau consumată
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Durabilitatea SHORT este eliminată dacă a fost în bucătărie timp de cel puțin 1 oră și 30 de minute (3 cicluri)
        if (mealType.getMealDurability() == MealDurability.SHORT) {
            LocalDateTime expirationDateTime = timestamp.plusMinutes(30 * 3); // Adăugați 3 cicluri (30 de minute fiecare) la data și ora înregistrării porției
            if (currentDateTime.isAfter(expirationDateTime)) {
                return true;
            }
        }

        // Porțiile de masă cu durabilitatea MEDIUM și LONG sunt considerate expirate la sfârșitul zilei
        if (currentDateTime.getHour() >= 7) { // Presupunând că ora de încheiere a zilei este 7:00
            if (mealType.getMealDurability() != MealDurability.LONG) {
                return true;
            }
        }

        return false;
    }


    @Override
    public String toString() {
        return mealType.toString();
    }
}