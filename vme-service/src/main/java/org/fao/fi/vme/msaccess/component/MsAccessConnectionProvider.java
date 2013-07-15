package org.fao.fi.vme.msaccess.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.fao.fi.vme.VmeException;

public class MsAccessConnectionProvider {

	public static final String JDBC_ODBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	public static final String UCANACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
	
	public static final String JDBC_ODBC_URL = "jdbc:odbc:Driver={Microsoft Access Driver " + "(*.mdb, *.accdb)};DBQ=";
	public static final String UCANACCESS_URL = "jdbc:ucanaccess://";
	
	
	
	Connection connecton;

	//String dbLocation = "../vme-service-integrationtest/src/test/resources/VME_DBTest-FC.accdb";
	
	String dbLocation = "../vme-service-integrationtest/src/test/resources/VME_DB_production.accdb";

	public MsAccessConnectionProvider() {
		try {
			Class.forName(UCANACCESS_DRIVER);
			String url = UCANACCESS_URL + dbLocation;
			System.out.println("Microsoft Access file : " +  dbLocation + "successfully connected!");
			this.connecton = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new VmeException(e);
		} catch (ClassNotFoundException e) {
			throw new VmeException(e);
		}
	}

	public Connection getConnecton() {
		return connecton;
	}

}
