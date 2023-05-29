package com.codecool.ehotel.service.logger;

import com.codecool.ehotel.service.logger.logger;

public class ConsoleLogger implements logger {
    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}
