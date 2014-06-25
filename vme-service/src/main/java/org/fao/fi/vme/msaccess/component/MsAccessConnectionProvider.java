package org.fao.fi.vme.msaccess.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.fao.fi.vme.VmeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MsAccessConnectionProvider {
	protected static final Logger LOG = LoggerFactory.getLogger(MsAccessConnectionProvider.class);

	protected static final String JDBC_ODBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	protected static final String UCANACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

	protected static final String JDBC_ODBC_URL = "jdbc:odbc:Driver={Microsoft Access Driver "
			+ "(*.mdb, *.accdb)};DBQ=";
	protected static final String UCANACCESS_URL = "jdbc:ucanaccess://";

	protected Connection connection;

	public MsAccessConnectionProvider() {
		super();
	}

	@PostConstruct
	public final void postConstruct() {
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

	public final Connection getConnection() {
		return connection;
	}

	protected abstract String getDBLocation();
}
