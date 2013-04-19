package org.fao.fi.vme.dao.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.VmeObservation;

public class VmeAccessDbImport {

	static private String columnNamesTable1[] = { "ID", "RFB_ID", "VME_ID", "Year_ID", "VME_Validity_Start",
			"VME_Validity_End", "VME_Geoform", "VME_GeogArea1", "VME_GeogArea2", "VME_GeogAreaFAO", "VME_Coord",
			"VME_Area_Type (Name)", "VME_Status", "VME_Description_Physical", "VME_Description_Biology",
			"VME_Description_Impact", };

	String tables[] = { "VME" };
	String tableColums[][] = { columnNamesTable1 };
	Class<?> classes[] = { VmeObservation.class };
	Class<?> mappingClass[] = { VmeObservationMapper.class };

	List<Object> generateObjects() throws SQLException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < tables.length; i++) {
			ResultSet rs = getResultset(tables[i]);

			try {
				Mapper mapper = (Mapper) mappingClass[i].newInstance();
				while (rs.next()) {
					Object o = mapper.generateObject(rs, tableColums[1]);
				}
			} catch (InstantiationException e) {
				throw new VmeDaoException(e);
			} catch (IllegalAccessException e) {
				throw new VmeDaoException(e);
			}
		}

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
