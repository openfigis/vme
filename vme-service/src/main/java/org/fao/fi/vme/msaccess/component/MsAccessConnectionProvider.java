package org.fao.fi.vme.msaccess.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MsAccessConnectionProvider {

	Connection connecton;

	String dbLocation = "../vme-service-integrationtest/src/test/resources/VME_DBTest-FC.accdb";

	public MsAccessConnectionProvider() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String url = "jdbc:odbc:Driver={Microsoft Access Driver " + "(*.mdb, *.accdb)};DBQ=" + dbLocation;
			System.out.println("Connected!");
			this.connecton = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new VmeDaoException(e);
		} catch (ClassNotFoundException e) {
			throw new VmeDaoException(e);
		}
	}

	public Connection getConnecton() {
		return connecton;
	}

}
