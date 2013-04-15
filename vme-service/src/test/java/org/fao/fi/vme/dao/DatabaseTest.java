package org.fao.fi.vme.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class DatabaseTest {

	@Test
	public void derby() throws Exception {
		
		//redirect logging to avoid file pollution
		System.setProperty("derby.stream.error.field", "java.lang.System.out");
		
		//in-memory: no need to load driver, no need to name db
		Connection connection = DriverManager.getConnection("jdbc:derby:memory:test;create=true");
		
		connection.close();
		
	}
}
