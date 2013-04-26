package org.fao.fi.vme.dao.msaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableReader {

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private final GenericMapper mapper = new GenericMapper();

	public Table read(Class<?> clazz) {
		Table table = new Table();
		List<Object> list = new ArrayList<Object>();
		table.setClazz(clazz);
		table.setObjectList(list);
		ResultSet rs = getResultset(clazz.getSimpleName());
		try {
			while (rs.next()) {
				Object o = mapper.generateObject(rs, clazz);
				list.add(o);
			}
		} catch (SQLException e) {
			throw new VmeDaoException(e);
		}

		return table;
	}

	private ResultSet getResultset(String table) {
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

}
