/**
 * (c) 2014 FAO / UN (project: vme-dao)
 */
package org.vme.dao.util;


/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 25 Feb 2014 Fiorellato
 * Creation.
 *
 * @version 1.0
 * @since 25 Feb 2014
 */
public class DBCPConnectionProvider {
	// public class DBCPConnectionProvider extends AbstractConnectionProvider {
	// /** Field serialVersionUID */
	// private static final long serialVersionUID = -1281741635401231503L;
	//
	// private static final String CUSTOM_PREFIX = "hibernate.dbcp.";
	//
	// private BasicDataSource ds;
	//
	// public DBCPConnectionProvider() {
	// super();
	//
	// System.out.println("Erik");
	//
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see org.vme.dao.util.AbstractConnectionProvider#getCustomPrefix()
	// */
	// @Override
	// protected String getCustomPrefix() {
	// return CUSTOM_PREFIX;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * org.vme.dao.util.AbstractConnectionProvider#asProperties(org.vme.dao.
	// * util.AbstractConnectionProvider.CommonConfiguration)
	// */
	// @Override
	// protected Properties asProperties(CommonConfiguration common) {
	// Properties dbcpProperties = new Properties();
	//
	// // <property name="hibernate.dbcp.initialSize" value="5"/>
	// // <property name="hibernate.dbcp.maxIdle" value="5"/>
	// // <property name="hibernate.dbcp.minIdle" value="0"/>
	// // <property name="hibernate.dbcp.validationQuery"
	// // value="select 1 from dual"/>
	// // <property name="hibernate.dbcp.testWhileIdle" value="true"/>
	// // <property name="hibernate.dbcp.testOnBorrow" value="true"/>
	// // <property name="hibernate.dbcp.testOnReturn" value="true"/>
	// // <property name="hibernate.dbcp.timeBetweenEvictionRunsMillis"
	// // value="1800000"/>
	// // <property name="hibernate.dbcp.numTestsPerEvictionRun" value="3"/>
	// // <property name="hibernate.dbcp.minEvictableIdleTimeMillis"
	// // value="1800000"/>
	//
	// if (common != null) {
	// this.safePut(dbcpProperties, "driverClassName", common.getDriverClass());
	// this.safePut(dbcpProperties, "url", common.getUrl());
	// this.safePut(dbcpProperties, "username", common.getUsername());
	// this.safePut(dbcpProperties, "password", common.getPassword());
	// this.safePut(dbcpProperties, "defaultTransactionIsolation",
	// common.getTransactionIsolation());
	// this.safePut(dbcpProperties, "defaultAutoCommit",
	// common.getDefaultAutocommit());
	// this.safePut(dbcpProperties, "maxActive", common.getPoolSize());
	// this.safePut(dbcpProperties, "connectionProperties",
	// common.getConnectionProperties());
	// this.safePut(dbcpProperties, "connectionProperties",
	// common.getConnectionProperties());
	// }
	//
	// return dbcpProperties;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// org.vme.dao.util.AbstractConnectionProvider#configurePool(java.util.
	// * Properties)
	// */
	// @Override
	// protected void configurePool(Properties configuration) throws Exception {
	// if (LOG.isDebugEnabled()) {
	// StringWriter sw = new StringWriter();
	//
	// configuration.list(new PrintWriter(sw, true));
	//
	// LOG.debug(sw.toString());
	// }
	//
	// // Let the factory create the pool
	// ds = (BasicDataSource)
	// BasicDataSourceFactory.createDataSource(configuration);
	//
	// Connection conn = this.ds.getConnection();
	// conn.close();
	//
	// // Log pool statistics before continuing.
	// this.logStatistics();
	// }
	//
	// public Connection getConnection() throws SQLException {
	// Connection conn = null;
	// try {
	// conn = this.ds.getConnection();
	// } catch (SQLException e) {
	// LOG.error(e.getMessage(), e);
	// } finally {
	// logStatistics();
	// }
	// return conn;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see org.vme.dao.util.AbstractConnectionProvider#cleanup()
	// */
	// protected void cleanup() {
	// if (this.ds != null) {
	// try {
	// this.ds.close();
	// } catch (Exception e) {
	// LOG.error(e.getMessage(), e);
	// }
	// this.ds = null;
	// }
	// }
	//
	// protected void logStatistics() {
	// LOG.debug("Active: {} (maxTotal: {}) - Idle: {} (maxIdle: {})",
	// this.ds.getNumActive(), this.ds.getMaxTotal(),
	// this.ds.getNumIdle(), this.ds.getMaxIdle());
	// }
}
