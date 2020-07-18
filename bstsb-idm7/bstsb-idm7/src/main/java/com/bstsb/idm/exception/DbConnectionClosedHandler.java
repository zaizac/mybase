/**
 * Copyright 2018. Bestinet Sdn Bhd
 */
package com.bstsb.idm.exception;


import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;


/**
 * @author Mary Jane Buenaventura
 * @since May 4, 2018
 */
@WebListener
public class DbConnectionClosedHandler implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbConnectionClosedHandler.class);


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// DO NOTHING
	}


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
				LOGGER.error(ex.getMessage());
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