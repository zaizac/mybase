/**
 * Copyright 2019. Universal Recruitment Platform
 */
package com.wfw.exception;


import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;


/**
 * @author Mary Jane Buenaventura
 * @since Jun 21, 2018
 */
@WebListener
public class DbConnectionClosedHandler implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Comment generated for SonarLint Fix
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
				// deregistration failed, might want to do something, log at
				// the very least
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