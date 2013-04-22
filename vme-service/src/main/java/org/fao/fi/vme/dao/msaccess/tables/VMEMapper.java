package org.fao.fi.vme.dao.msaccess.tables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fao.fi.vme.dao.msaccess.VmeDaoException;

public class VMEMapper implements Mapper {

	String colums[] = { "ID", "RFB_ID", "VME_ID", "Year_ID", "VME_Validity_Start", "VME_Validity_End", "VME_Geoform",
			"VME_GeogArea1", "VME_GeogArea2", "VME_GeogAreaFAO", "VME_Coord", "VME_Area_Type(Name)", "VME_Status",
			"VME_Description_Physical", "VME_Description_Biology", "VME_Description_Impact" };

	@Override
	public Object generateObject(ResultSet rs) throws SQLException {
		VME o = new VME();
		Method ms[] = VME.class.getMethods();
		int i = 0;
		for (String column : colums) {
			for (Method method : ms) {
				String attributeName = method.getName().substring(3, method.getName().length());

				if (method.getName().startsWith("set") && column.equals(attributeName)) {
					System.out.println(attributeName);
					Class<?> clazz = method.getParameterTypes()[0];
					try {
						if (clazz == String.class) {
							method.invoke(o, rs.getString(column));
						} else {
							method.invoke(o, rs.getInt(column));
						}
						i++;
					} catch (IllegalArgumentException e) {
						throw new VmeDaoException(e);
					} catch (IllegalAccessException e) {
						throw new VmeDaoException(e);
					} catch (InvocationTargetException e) {
						throw new VmeDaoException(e);
					}
				}
			}
		}
		return o;
	}
}
