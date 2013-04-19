package org.fao.fi.vme.dao.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VmeAccessDbImport {

	void importAccessDB() {
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		// SQL query command
		String SQL = "SELECT * FROM VME ";
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {

				System.out.println(rs.getString("VME_ID") + " : " + rs.getString("RFB_ID") + " : "
						+ rs.getString("Year_ID"));
			}
		} catch (SQLException e) {
			throw new VmeDaoException(e);
		}
	}

	Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String url = "jdbc:odbc:Driver={Microsoft Access Driver "
					+ "(*.mdb, *.accdb)};DBQ=C:\\Database\\VME_DBTest-FC.accdb";
			System.out.println("Connected!");
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new VmeDaoException(e);
		} catch (ClassNotFoundException e) {
			throw new VmeDaoException(e);
		}
		return connection;
	}

}
