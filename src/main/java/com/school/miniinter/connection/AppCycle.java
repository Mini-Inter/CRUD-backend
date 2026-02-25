package com.school.miniinter.connection;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppCycle implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionFactory.disconnect();
    }
}
