/**
 * Copyright 2019. 
 */
package com.notify.exception;


import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;



/**
 * The Class DbConnectionClosedHandler.
 *
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@WebListener
public class DbConnectionClosedHandler implements ServletContextListener {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DbConnectionClosedHandler.class);


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Auto Generated Method
	}


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();

		Driver driver = null;

		// clear drivers
		while (drivers.hasMoreElements()) {
			try {
				driver = drivers.nextElement();
				DriverManager.deregisterDriver(driver);

			} catch (SQLException ex) {
				// deregistration failed, might want to do something, log at
				// the very least
				LOGGER.error("SQLException: {}", ex.getMessage());
			}
		}

		// MySQL driver leaves around a thread. This static method cleans it
		// up.
		try {
			AbandonedConnectionCleanupThread.checkedShutdown();
		} catch (Exception e) {
			// again failure, not much you can do
		}
	}

}