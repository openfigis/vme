package org.fao.fi.vme.dao.msaccess.tables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fao.fi.vme.dao.msaccess.VmeDaoException;

abstract class GenericMapper implements Mapper {

	String colums[];
	Object object;

	@Override
	public Object generateObject(ResultSet rs) throws SQLException {

		Method ms[] = VME.class.getMethods();
		for (String column : colums) {
			for (Method method : ms) {
				String attributeName = method.getName().substring(3, method.getName().length());
				if (method.getName().startsWith("set") && column.equals(attributeName)) {
					System.out.println(attributeName);
					Class<?> clazz = method.getParameterTypes()[0];
					try {
						if (clazz == String.class) {
							method.invoke(object, rs.getString(column));
						} else {
							method.invoke(object, rs.getInt(column));
						}
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
		return object;
	}
}
