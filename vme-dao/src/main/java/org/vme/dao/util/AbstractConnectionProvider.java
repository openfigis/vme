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
public abstract class AbstractConnectionProvider implements ConnectionProvider, Configurable {
	/** Field serialVersionUID */
	private static final long serialVersionUID = -1281741635401231503L;
	
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractConnectionProvider.class);
	
	protected static final String AUTOCOMMIT = "hibernate.connection.autocommit";

	protected static final String JDBC_DRIVER_CLASS_PROP = "javax.persistence.jdbc.driver";
	protected static final String JDBC_URL_PROP = "javax.persistence.jdbc.url";
	protected static final String JDBC_USER_PROP = "javax.persistence.jdbc.user";
	protected static final String JDBC_PWD_PROP = "javax.persistence.jdbc.password";
	
	public AbstractConnectionProvider() {
		super();
	}
	
	class CommonConfiguration {
		private String driverClass;
		private String url;
		private String username;
		private String password;
		
		private String transactionIsolation;
		private Boolean defaultAutocommit;
		private Integer poolSize;
		
		private String connectionProperties;
		
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
			return this.driverClass;
		}
		
		/**
		 * @param driverClass the 'driverClass' value to set
		 */
		public void setDriverClass(String driverClass) {
			this.driverClass = driverClass;
		}
		
		/**
		 * @return the 'url' value
		 */
		public String getUrl() {
			return this.url;
		}
		
		/**
		 * @param url the 'url' value to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		
		/**
		 * @return the 'username' value
		 */
		public String getUsername() {
			return this.username;
		}
		
		/**
		 * @param username the 'username' value to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		
		/**
		 * @return the 'password' value
		 */
		public String getPassword() {
			return this.password;
		}
		
		/**
		 * @param password the 'password' value to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}
		
		/**
		 * @return the 'transactionIsolation' value
		 */
		public String getTransactionIsolation() {
			return this.transactionIsolation;
		}
		
		/**
		 * @param transactionIsolation the 'transactionIsolation' value to set
		 */
		public void setTransactionIsolation(String transactionIsolation) {
			this.transactionIsolation = transactionIsolation;
		}
		
		/**
		 * @return the 'defaultAutocommit' value
		 */
		public Boolean getDefaultAutocommit() {
			return this.defaultAutocommit;
		}
		
		/**
		 * @param defaultAutocommit the 'defaultAutocommit' value to set
		 */
		public void setDefaultAutocommit(Boolean defaultAutocommit) {
			this.defaultAutocommit = defaultAutocommit;
		}
		
		/**
		 * @return the 'poolSize' value
		 */
		public Integer getPoolSize() {
			return this.poolSize;
		}
		
		/**
		 * @param poolSize the 'poolSize' value to set
		 */
		public void setPoolSize(Integer poolSize) {
			this.poolSize = poolSize;
		}

		/**
		 * @return the 'connectionProperties' value
		 */
		public String getConnectionProperties() {
			return this.connectionProperties;
		}

		/**
		 * @param driverProperties the 'connectionProperties' value to set
		 */
		public void setConnectionProperties(String connectionProperties) {
			this.connectionProperties = connectionProperties;
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
	protected final Properties customConfiguration(Map configurationValues) {
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

	protected abstract void cleanup() throws Throwable;
	
	protected abstract Properties asProperties(CommonConfiguration common);

	protected abstract String getCustomPrefix();

	protected abstract void configurePool(Properties configuration) throws Throwable;

	protected final void safePut(Properties props, String key, Object value) {
		if(value != null) {
			props.put(key, value.toString());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final CommonConfiguration commonConfiguration(Map props) {
		CommonConfiguration common = new CommonConfiguration();
		
		try {
			common.driverClass = (String)props.get(JDBC_DRIVER_CLASS_PROP);
			common.url = (String)props.get(JDBC_URL_PROP);

			// Username / password
			common.username = (String)props.get(JDBC_USER_PROP);
			common.password = (String)props.get(JDBC_PWD_PROP);

			// Isolation level
			String isolationLevel = (String)props.get(Environment.ISOLATION);
			
			if ((isolationLevel != null) && (isolationLevel.trim().length() > 0)) {
				common.transactionIsolation = isolationLevel;
			}

			// Turn off autocommit (unless autocommit property is set)
			String autocommit = (String)props.get(AUTOCOMMIT);
			if ((autocommit != null) && (autocommit.trim().length() > 0)) {
				common.defaultAutocommit = Boolean.parseBoolean(autocommit);
			} else {
				common.defaultAutocommit = Boolean.FALSE;
			}

			// Pool size
			String poolSize = (String)props.get(Environment.POOL_SIZE);
			if ((poolSize != null) && (poolSize.trim().length() > 0) && (Integer.parseInt(poolSize) > 0)) {
				common.poolSize = Integer.parseInt(poolSize);
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
	public abstract Connection getConnection() throws SQLException;

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.ConnectionProvider#closeConnection(java.sql.Connection)
	 */
	public final void closeConnection(Connection conn) throws SQLException {
		try {
			conn.close();
		} finally {
			logStatistics();
		}
	}
	
	protected abstract void logStatistics();

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.ConnectionProvider#supportsAggressiveRelease()
	 */
	public final boolean supportsAggressiveRelease() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.service.spi.Wrapped#isUnwrappableAs(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public final boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.service.spi.Wrapped#unwrap(java.lang.Class)
	 */
	@Override
	public final <T> T unwrap(Class<T> unwrapType) {
		return null;
	}
}