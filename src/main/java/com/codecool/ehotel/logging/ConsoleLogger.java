package com.codecool.ehotel.logging;

import java.time.LocalDateTime;

import static java.lang.System.out;

public class ConsoleLogger implements Logger {

    @Override
    public void logInfo(String message) {
        String logEntry = formatLogEntry("INFO", message);
        out.println(logEntry);
    }

    @Override
    public void logError(String message) {
        String logEntry = formatLogEntry("ERROR", message);
        out.println(logEntry);
    }

    private String formatLogEntry(String logType, String message) {
        LocalDateTime timestamp = LocalDateTime.now();
        return String.format("[%s] [%s] %s", timestamp, logType, message);
    }
}
