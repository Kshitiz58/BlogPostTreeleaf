package com.treeleaf.blog.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Autowired
    private DataSource dataSource;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }
}

