/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.vme.dao.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Configurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
abstract public class AbstractConnectionProvider implements ConnectionProvider, Configurable {
	/** Field serialVersionUID */
	private static final long serialVersionUID = -1281741635401231503L;
	
	final static protected Logger LOG = LoggerFactory.getLogger(AbstractConnectionProvider.class);
	
	final static protected String AUTOCOMMIT = "hibernate.connection.autocommit";

	final static protected String JDBC_DRIVER_CLASS_PROP = "javax.persistence.jdbc.driver";
	final static protected String JDBC_URL_PROP = "javax.persistence.jdbc.url";
	final static protected String JDBC_USER_PROP = "javax.persistence.jdbc.user";
	final static protected String JDBC_PWD_PROP = "javax.persistence.jdbc.password";
	
	public AbstractConnectionProvider() {
		super();
	}
	
	class CommonConfiguration {
		private String _driverClass;
		private String _url;
		private String _username;
		private String _password;
		
		private String _transactionIsolation;
		private Boolean _defaultAutocommit;
		private Integer _poolSize;
		
		private String _connectionProperties;
		
		/**
		 * Class constructor
		 *
		 */
		public CommonConfiguration() {
			super();
		}
		
		/**
		 * @return the 'driverClass' value
		 */
		public String getDriverClass() {
			return this._driverClass;
		}
		
		/**
		 * @param driverClass the 'driverClass' value to set
		 */
		public void setDriverClass(String driverClass) {
			this._driverClass = driverClass;
		}
		
		/**
		 * @return the 'url' value
		 */
		public String getUrl() {
			return this._url;
		}
		
		/**
		 * @param url the 'url' value to set
		 */
		public void setUrl(String url) {
			this._url = url;
		}
		
		/**
		 * @return the 'username' value
		 */
		public String getUsername() {
			return this._username;
		}
		
		/**
		 * @param username the 'username' value to set
		 */
		public void setUsername(String username) {
			this._username = username;
		}
		
		/**
		 * @return the 'password' value
		 */
		public String getPassword() {
			return this._password;
		}
		
		/**
		 * @param password the 'password' value to set
		 */
		public void setPassword(String password) {
			this._password = password;
		}
		
		/**
		 * @return the 'transactionIsolation' value
		 */
		public String getTransactionIsolation() {
			return this._transactionIsolation;
		}
		
		/**
		 * @param transactionIsolation the 'transactionIsolation' value to set
		 */
		public void setTransactionIsolation(String transactionIsolation) {
			this._transactionIsolation = transactionIsolation;
		}
		
		/**
		 * @return the 'defaultAutocommit' value
		 */
		public Boolean getDefaultAutocommit() {
			return this._defaultAutocommit;
		}
		
		/**
		 * @param defaultAutocommit the 'defaultAutocommit' value to set
		 */
		public void setDefaultAutocommit(Boolean defaultAutocommit) {
			this._defaultAutocommit = defaultAutocommit;
		}
		
		/**
		 * @return the 'poolSize' value
		 */
		public Integer getPoolSize() {
			return this._poolSize;
		}
		
		/**
		 * @param poolSize the 'poolSize' value to set
		 */
		public void setPoolSize(Integer poolSize) {
			this._poolSize = poolSize;
		}

		/**
		 * @return the 'connectionProperties' value
		 */
		public String getConnectionProperties() {
			return this._connectionProperties;
		}

		/**
		 * @param driverProperties the 'connectionProperties' value to set
		 */
		public void setConnectionProperties(String connectionProperties) {
			this._connectionProperties = connectionProperties;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.service.spi.Configurable#configure(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void configure(Map configurationValues) throws HibernateException {
		CommonConfiguration common = this.commonConfiguration(configurationValues);
		
		Properties asProp = this.asProperties(common);
		asProp.putAll(this.customConfiguration(configurationValues));
		
		StringWriter sw = new StringWriter();
		asProp.list(new PrintWriter(sw, true));
		
		LOG.info("Configuring pool {}", this.getClass().getSimpleName());
		LOG.debug("Configuration parameters:\n{}", sw.toString());
		
		try {
			this.configurePool(asProp);
			
		} catch (Throwable t) {
			String message = "Could not create connection pool of type " + this.getClass().getSimpleName() + ": " + t.getMessage();
			
			LOG.error(message, t);
			
			try {
				this.cleanup();
			} catch (Throwable tt) {
				//Suffocate
			}
			
			throw new HibernateException(message, t);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	final protected Properties customConfiguration(Map configurationValues) {
		String property, value;
		
		Properties asProps = new Properties();
		
		final String customPrefix = this.getCustomPrefix();
		
		for (Iterator<?> iter=configurationValues.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			
			String key = (String) entry.getKey();
			
			if (key.startsWith(customPrefix)) {
				property = key.substring(customPrefix.length());
				value = (String) entry.getValue();
				
				asProps.put(property, value);
			}
		}
		
		return asProps;
	}

	abstract protected void cleanup() throws Throwable;
	
	abstract protected Properties asProperties(CommonConfiguration common);

	abstract protected String getCustomPrefix();

	abstract protected void configurePool(Properties configuration) throws Throwable;

	final protected void safePut(Properties props, String key, Object value) {
		if(value != null) {
			props.put(key, value.toString());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	final protected CommonConfiguration commonConfiguration(Map props) {
		CommonConfiguration common = new CommonConfiguration();
		
		try {
			common._driverClass = (String)props.get(JDBC_DRIVER_CLASS_PROP);
			common._url = (String)props.get(JDBC_URL_PROP);

			// Username / password
			common._username = (String)props.get(JDBC_USER_PROP);
			common._password = (String)props.get(JDBC_PWD_PROP);

			// Isolation level
			String isolationLevel = (String)props.get(Environment.ISOLATION);
			
			if ((isolationLevel != null) && (isolationLevel.trim().length() > 0)) {
				common._transactionIsolation = isolationLevel;
			}

			// Turn off autocommit (unless autocommit property is set)
			String autocommit = (String)props.get(AUTOCOMMIT);
			if ((autocommit != null) && (autocommit.trim().length() > 0)) {
				common._defaultAutocommit = Boolean.parseBoolean(autocommit);
			} else {
				common._defaultAutocommit = Boolean.FALSE;
			}

			// Pool size
			String poolSize = (String)props.get(Environment.POOL_SIZE);
			if ((poolSize != null) && (poolSize.trim().length() > 0) && (Integer.parseInt(poolSize) > 0)) {
				common._poolSize = Integer.parseInt(poolSize);
			}
			
			Properties driverProps = ConnectionProviderInitiator.getConnectionProperties(props);
			if (driverProps.size() > 0) {
				StringBuffer connectionProperties = new StringBuffer();
				for (Iterator<?> iter = driverProps.entrySet().iterator(); iter.hasNext();) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					connectionProperties.append(key).append('=').append(value);
					if (iter.hasNext()) {
						connectionProperties.append(';');
					}
				}
				
				common.setConnectionProperties(connectionProperties.toString());
			}

		} catch (Throwable t) { 
			LOG.error("Cannot configure {}: {}", this.getClass().getSimpleName(), t.getMessage(), t);
		}
		
		return common;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.ConnectionProvider#getConnection()
	 */
	abstract public Connection getConnection() throws SQLException;

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.ConnectionProvider#closeConnection(java.sql.Connection)
	 */
	final public void closeConnection(Connection conn) throws SQLException {
		try {
			conn.close();
		} finally {
			logStatistics();
		}
	}
	
	abstract protected void logStatistics();

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.ConnectionProvider#supportsAggressiveRelease()
	 */
	final public boolean supportsAggressiveRelease() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.service.spi.Wrapped#isUnwrappableAs(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	final public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.service.spi.Wrapped#unwrap(java.lang.Class)
	 */
	@Override
	final public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}
}