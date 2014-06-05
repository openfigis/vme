package org.vme.service.tabular.record;

import java.lang.reflect.Method;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class AbstractRecord {
	
	protected MultiLingualStringUtil u = new MultiLingualStringUtil();

	protected Method getMethod(Class<?> clazz, String methodName) {
		try {
			return clazz.getMethod(methodName);
		} catch (NoSuchMethodException e) {
			throw new VmeException(e);
		} catch (SecurityException e) {
			throw new VmeException(e);
		}
	}
}
