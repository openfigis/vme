package org.fao.fi.vme.dao.msaccess;

import java.lang.reflect.Method;
import java.sql.ResultSet;


/**
 * generate an object, given a resultset
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class GenericMapper {

	public Object generateObject(ResultSet rs, Class<?> clazz) {

		try {
			Object object = clazz.newInstance();
			Method ms[] = clazz.getMethods();
			for (Method method : ms) {
				String attributeName = method.getName().substring(3, method.getName().length());
				if (method.getName().startsWith("set")) {
					System.out.println(attributeName);
					clazz = method.getParameterTypes()[0];
					System.out.println(clazz);
					if (clazz == String.class) {
						try {
							method.invoke(object, rs.getString(attributeName));
						} catch (java.sql.SQLException e) {
							e.printStackTrace();
							method.invoke(object, rs.getBytes(attributeName));
						}
					}
					if (clazz == Integer.class) {
						method.invoke(object, rs.getString(attributeName));
					}

				}
			}
			return object;
		} catch (Exception e) {
			throw new VmeDaoException(e);
		}
	}
}
