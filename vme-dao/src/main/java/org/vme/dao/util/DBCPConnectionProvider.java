/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.vme.dao.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 25 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 25 Feb 2014
 */
public class DBCPConnectionProvider extends AbstractConnectionProvider {
	/** Field serialVersionUID */
	private static final long serialVersionUID = -1281741635401231503L;

	private static final String CUSTOM_PREFIX = "hibernate.dbcp.";
	
	private BasicDataSource _ds;
	
	public DBCPConnectionProvider() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.vme.dao.util.AbstractConnectionProvider#getCustomPrefix()
	 */
	@Override
	protected String getCustomPrefix() {
		return CUSTOM_PREFIX;
	}

	/* (non-Javadoc)
	 * @see org.vme.dao.util.AbstractConnectionProvider#asProperties(org.vme.dao.util.AbstractConnectionProvider.CommonConfiguration)
	 */
	@Override
	protected Properties asProperties(CommonConfiguration common) {
		Properties dbcpProperties = new Properties();
		
		if(common != null) {
			this.safePut(dbcpProperties, "driverClassName", common.getDriverClass());
			this.safePut(dbcpProperties, "url", common.getUrl());
			this.safePut(dbcpProperties, "username", common.getUsername());
			this.safePut(dbcpProperties, "password", common.getPassword());
			this.safePut(dbcpProperties, "defaultTransactionIsolation", common.getTransactionIsolation());
			this.safePut(dbcpProperties, "defaultAutoCommit", common.getDefaultAutocommit());
			this.safePut(dbcpProperties, "maxActive", common.getPoolSize());
			this.safePut(dbcpProperties, "connectionProperties", common.getConnectionProperties());
		}
		
		return dbcpProperties;
	}

	/* (non-Javadoc)
	 * @see org.vme.dao.util.AbstractConnectionProvider#configurePool(java.util.Properties)
	 */
	@Override
	protected void configurePool(Properties configuration) throws Throwable {
		if (LOG.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			
			configuration.list(new PrintWriter(sw, true));

			LOG.debug(sw.toString());
		}

		// Let the factory create the pool
		_ds = (BasicDataSource) BasicDataSourceFactory.createDataSource(configuration);

		Connection conn = this._ds.getConnection();
		conn.close();

		// Log pool statistics before continuing.
		this.logStatistics();
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = this._ds.getConnection();
		} catch(SQLException SQLe) {
			SQLe.printStackTrace();
		} finally {
			logStatistics();
		}
		return conn;
	}

	/* (non-Javadoc)
	 * @see org.vme.dao.util.AbstractConnectionProvider#cleanup()
	 */
	protected void cleanup() {
		if(this._ds != null) {
			try {
				this._ds.close();
			} catch (Exception e2) {
				// ignore
			}
			
			this._ds = null;
		}
	}
	
	protected void logStatistics() {
		LOG.debug("Active: {} (max: {}) - Idle: {} (max: {})", this._ds.getNumActive(), this._ds.getMaxActive(), this._ds.getNumIdle(), this._ds.getMaxIdle());
	}
}
