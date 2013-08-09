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


Update:
--ojdbc-6.jar		2,714,189  bytes
--ojdbc6 (1).jar	3,389,454  bytes
--I just did dowload the Oracle 12g driver, it is also called ojdbc6.jar and renamed by the browser to ojdbc6 (1).jar. The size of these jars are not the same.
--Official documentation:
--Oracle Database 12c Release 1 JDBC Driver Download
--ojdbc6.jar (3,389,454 bytes) - For use with JDK 6; It contains the JDBC driver classes
--So I have reasons to believe that, even though they are called the same, the bigger driver is a more recent version.
--Anyway, the driver does not make a difference for the problem described here VmeAccessDbImportIntegrationTest.testProblemCouldNotInitializeACollection
-- so I decided not to use it.    








