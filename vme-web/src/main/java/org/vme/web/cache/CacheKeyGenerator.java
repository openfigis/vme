package org.vme.web.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.fao.fi.vme.VmeException;

public class CacheKeyGenerator {

	public String generateKey(String interceptedMethodName, Object[] parameters) {

		String key = interceptedMethodName;
		for (Object o : parameters) {
			if (o instanceof Integer || o instanceof String) {
				key = key + o;
			} else {
				Method methods[] = o.getClass().getDeclaredMethods();
				for (Method method : methods) {
					try {
						if (method.getName().startsWith("get")) {
							key = key + method.invoke(o);
						}
					} catch (IllegalAccessException e) {
						throw new VmeException(e);
					} catch (IllegalArgumentException e) {
						throw new VmeException(e);
					} catch (InvocationTargetException e) {
						throw new VmeException(e);
					}
				}
			}
		}
		return key;

	}

}
