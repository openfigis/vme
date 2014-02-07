package org.fao.fi.vme.msaccess.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.fao.fi.vme.VmeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class MsAccessConnectionProvider {
	final static protected Logger LOG = LoggerFactory.getLogger(MsAccessConnectionProvider.class);

	final static protected String JDBC_ODBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	final static protected String UCANACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

	final static protected String JDBC_ODBC_URL = "jdbc:odbc:Driver={Microsoft Access Driver "
			+ "(*.mdb, *.accdb)};DBQ=";
	final static protected String UCANACCESS_URL = "jdbc:ucanaccess://";

	protected Connection connection;

	public MsAccessConnectionProvider() {
		super();
	}

	@PostConstruct
	final public void postConstruct() {
		try {
			String dbFilePath = this.getDBLocation();

			Class.forName(UCANACCESS_DRIVER);
			String url = UCANACCESS_URL + dbFilePath;
			this.connection = DriverManager.getConnection(url);
			LOG.info("Microsoft Access file : " + dbFilePath + "successfully connected!");
		} catch (SQLException e) {
			throw new VmeException(e);
		} catch (ClassNotFoundException e) {
			throw new VmeException(e);
		}
	}

	final public Connection getConnection() {
		return connection;
	}

	protected abstract String getDBLocation();
}
