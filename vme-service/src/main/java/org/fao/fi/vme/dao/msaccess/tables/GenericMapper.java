package org.fao.fi.vme.dao.msaccess.tables;

import java.lang.reflect.Method;
import java.sql.ResultSet;

import org.fao.fi.vme.dao.msaccess.VmeDaoException;

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
					if (clazz == String.class) {
						method.invoke(object, rs.getString(attributeName));
					} else {
						method.invoke(object, rs.getInt(attributeName));
					}
				}
			}
			return object;
		} catch (Exception e) {
			throw new VmeDaoException(e);
		}
	}
}
