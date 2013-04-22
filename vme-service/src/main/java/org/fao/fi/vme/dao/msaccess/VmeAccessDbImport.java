package org.fao.fi.vme.dao.msaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.dao.msaccess.tables.Mapper;
import org.fao.fi.vme.dao.msaccess.tables.VMEMapper;

public class VmeAccessDbImport {

	private final String tables[] = { "VME" };
	private final Class<?> mappingClass[] = { VMEMapper.class };

	public List<Object> generateObjects() {

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < tables.length; i++) {
			ResultSet rs = getResultset(tables[i]);
			try {
				Mapper mapper = (Mapper) mappingClass[i].newInstance();
				try {
					while (rs.next()) {
						Object o = mapper.generateObject(rs);
						list.add(o);
					}
				} catch (Exception e) {
					throw new VmeDaoException(e);
				}
			} catch (InstantiationException e) {
				throw new VmeDaoException(e);
			} catch (IllegalAccessException e) {
				throw new VmeDaoException(e);
			}
		}
		return list;
	}

	ResultSet getResultset(String table) {
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

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
