package org.fao.fi.vme.dao.msaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.dao.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.dao.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.dao.msaccess.tables.Meetings;
import org.fao.fi.vme.dao.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.dao.msaccess.tables.VME;

public class VmeReader {
	private final GenericMapper mapper = new GenericMapper();

	private final Class<?> tables[] = { VME.class, Measures_VME_General.class, Measues_VME_Specific.class,
			Meetings.class, RFB_VME_Fishing_History.class };

	public List<Table> readObjects() {

		List<Table> tableList = new ArrayList<Table>();

		for (Class<?> clazz : tables) {
			List<Object> list = new ArrayList<Object>();
			Table table = new Table();
			table.setClazz(clazz);
			table.setRecords(list);
			ResultSet rs = getResultset(clazz.getSimpleName());
			try {
				while (rs.next()) {
					Object o = mapper.generateObject(rs, clazz);
					list.add(o);
				}
			} catch (SQLException e) {
				throw new VmeDaoException(e);
			}
			tableList.add(table);

		}
		return tableList;
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
					+ "(*.mdb, *.accdb)};DBQ=C:\\Documents and Settings\\VanIngen\\My Documents\\Dropbox\\work\\vme\\4 access db\\2\\VME_DBTest-FC.accdb";
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
