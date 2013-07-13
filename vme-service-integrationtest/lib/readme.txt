Problem: 
Saving the ObservationXml with JPA causes this problem:
Oracle 10g clob jpa java.lang.AbstractMethodError: oracle.jdbc.driver.T4CPreparedStatement.setCharacterStream(ILjava/io/Reader

Solution:
ojdbc6.jar
Oracle Database 11g Release 2 (11.2.0.3) JDBC Drivers ojdbc6.jar (2,714,189 bytes) - Classes for use with JDK 1.6. 
	It contains the JDBC driver classes except classes for NLS support in Oracle Object and Collection types.
This driver formally does not match the version of Oracle but it does the job.
For maven the file has been renamed to  ojdbc-6.jar


Background:
FI is currently using Oracle 10g. The above mentioned problem could not be solved with these drivers:
ojdbc14-10_2_0_3_0.jar
ojdbc14.jar





