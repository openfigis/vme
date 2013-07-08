package org.fao.fi.vme.msaccess.component;

import java.lang.reflect.Method;
import java.sql.ResultSet;

import org.fao.fi.vme.VmeException;

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
					clazz = method.getParameterTypes()[0];
					if (clazz == String.class) {
						// try {
						method.invoke(object, rs.getString(attributeName));
						// } catch (java.sql.SQLException e) {
						// e.printStackTrace();
						// method.invoke(object, rs.getBytes(attributeName));
						// }
					}
					if (clazz == int.class) {
						method.invoke(object, rs.getInt(attributeName));
					}
					if (clazz != String.class && clazz != int.class) {
						throw new VmeException("This type is not yet supported :" + clazz.getSimpleName());
					}
				}
			}
			return object;
		} catch (Exception e) {
			throw new VmeException(e);
		}
	}
}
