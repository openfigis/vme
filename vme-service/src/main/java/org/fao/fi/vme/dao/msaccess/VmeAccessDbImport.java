package org.fao.fi.vme.dao.msaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.dao.msaccess.tables.GenericMapper;
import org.fao.fi.vme.dao.msaccess.tables.VME;

public class VmeAccessDbImport {

	private final Class<?> tables[] = { VME.class };

	GenericMapper mapper = new GenericMapper();

	public List<Object> generateObjects() {
		List<Object> list = new ArrayList<Object>();
		for (Class<?> clazz : tables) {
			ResultSet rs = getResultset(clazz.getSimpleName());
			try {
				while (rs.next()) {
					Object o = mapper.generateObject(rs, clazz);
					list.add(o);
				}
			} catch (SQLException e) {
				throw new VmeDaoException(e);
			}
		}
		return list;
	}

	ResultSet getResultset(String table) {
		Connection connection = getConnection();
		Statement stmt = null;

		// SQL query command
		String SQL = "SELECT * FROM " + table;
		try {
			stmt = connection.createStatement();
			return stmt.executeQuery(SQL);
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
