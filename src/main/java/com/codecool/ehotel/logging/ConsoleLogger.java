package com.codecool.ehotel.logging;

import java.time.LocalDateTime;

import static java.lang.System.out;

public class ConsoleLogger implements Logger {

    @Override
    public void logInfo(String message) {
        String logEntry = formatLogEntry("INFO", message);
        out.println(logEntry);
    }

    private String formatLogEntry(String logType, String message) {
        return String.format("[%s] %s", logType, message);
    }
}
