package com.jongtix.book.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class ContextListener extends ContextLoaderListener {
    /*
    * 출처: https://stackoverflow.com/questions/45055122/tomcat-7-memory-leak-on-stop-redeploy-spring-data-jpa-hibernate-mysql
    * */

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("-= Context started =-");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
        log.info("-= Context destroyed =-");

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            if (driver.getClass().getClassLoader() == cl) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    log.error("Error deregistering JDBC driver {}", driver, e);
                }
            } else {
                log.info("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader", driver);
            }
        }
    }
}
