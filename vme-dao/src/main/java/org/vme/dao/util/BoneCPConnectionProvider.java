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
// public class BoneCPConnectionProvider extends AbstractConnectionProvider {
public class BoneCPConnectionProvider {
	// /** Field serialVersionUID */
	// private static final long serialVersionUID = -1281741635401231503L;
	//
	// private static final String CUSTOM_PREFIX = "hibernate.bonecp.";
	//
	// private BoneCP ds;
	// private Statistics stats;
	//
	// public BoneCPConnectionProvider() {
	// super();
	// }
	//
	// /* (non-Javadoc)
	// * @see org.vme.dao.util.AbstractConnectionProvider#getCustomPrefix()
	// */
	// @Override
	// protected String getCustomPrefix() {
	// return CUSTOM_PREFIX;
	// }
	//
	// /* (non-Javadoc)
	// * @see
	// org.vme.dao.util.AbstractConnectionProvider#asProperties(org.vme.dao.util.AbstractConnectionProvider.CommonConfiguration)
	// */
	// @Override
	// protected Properties asProperties(CommonConfiguration common) {
	// Properties boneCPProperties = new Properties();
	//
	// if(common != null) {
	// this.safePut(boneCPProperties, "jdbcDriver", common.getDriverClass());
	// this.safePut(boneCPProperties, "jdbcUrl", common.getUrl());
	// this.safePut(boneCPProperties, "username", common.getUsername());
	// this.safePut(boneCPProperties, "password", common.getPassword());
	// this.safePut(boneCPProperties, "driverProperties",
	// common.getConnectionProperties());
	// this.safePut(boneCPProperties, "defaultAutoCommit",
	// common.getDefaultAutocommit());
	// this.safePut(boneCPProperties, "defaultTransactionIsolation",
	// common.getTransactionIsolation());
	// this.safePut(boneCPProperties, "maxConnectionsPerPartition",
	// common.getPoolSize());
	// }
	//
	// return boneCPProperties;
	// }
	//
	// /* (non-Javadoc)
	// * @see
	// org.vme.dao.util.AbstractConnectionProvider#configurePool(java.util.Properties)
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
	// Class.forName(configuration.getProperty("jdbcDriver"));
	//
	// BoneCPConfig config = new BoneCPConfig(configuration);
	//
	// this.ds = new BoneCP(config);
	//
	// if(config.isStatisticsEnabled()) {
	// this.stats = new Statistics(this.ds);
	// }
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
	// } catch(SQLException e) {
	// LOG.error(e.getMessage(), e);
	// } finally {
	// logStatistics();
	// }
	// return conn;
	// }
	//
	// /* (non-Javadoc)
	// * @see org.vme.dao.util.AbstractConnectionProvider#cleanup()
	// */
	// protected void cleanup() {
	// if(this.ds != null) {
	// try {
	// this.ds.close();
	// } catch (Exception e2) {
	// LOG.error(e2.getMessage(), e2);
	// }
	//
	// this.ds = null;
	// }
	// }
	//
	// protected void logStatistics() {
	// if(this.stats != null) {
	// LOG.debug("Connections requested: {} - Connection wait time (avg): {}",
	// this.stats.getConnectionsRequested(),
	// this.stats.getConnectionWaitTimeAvg());
	// }
	// }
}
