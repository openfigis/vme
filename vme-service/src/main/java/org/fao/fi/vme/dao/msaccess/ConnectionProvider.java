package org.fao.fi.vme.dao.msaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	Connection connecton;

	public ConnectionProvider() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String url = "jdbc:odbc:Driver={Microsoft Access Driver "
					+ "(*.mdb, *.accdb)};DBQ=C:\\Documents and Settings\\VanIngen\\My Documents\\Dropbox\\work\\vme\\4 access db\\2\\VME_DBTest-FC.accdb";
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
