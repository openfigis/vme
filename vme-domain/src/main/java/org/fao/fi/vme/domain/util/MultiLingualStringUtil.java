package org.fao.fi.vme.domain.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.MultiLingualString;

/**
 * Utility class in order to generate a multilingual strings
 * 
 * @author Erik van Ingen
 * 
 */
public class MultiLingualStringUtil {

	// index of the word starting after the get of the set in the method name.
	private static int XET = 3;
	// index of the first parameter of the setter
	private static int SET_INDEX = 0;
	// number of parameters of a setter of a bean
	private static int LENGTH = 1;

	public MultiLingualString english(String text) {
		MultiLingualString l = new MultiLingualString();
		Map<Integer, String> stringMap = new HashMap<Integer, String>();
		stringMap.put(Lang.EN, text);
		l.setStringMap(stringMap);
		return l;
	}

	public void addFrench(MultiLingualString u, String text) {
		u.getStringMap().put(Lang.FR, text);
	}

	/**
	 * Return English string if there is, else null.
	 * 
	 * @param multiLingualString
	 * @return
	 */
	public String getEnglish(MultiLingualString multiLingualString) {
		String english = null;
		if (multiLingualString != null) {
			english = multiLingualString.getStringMap().get(Lang.EN);
			if (StringUtils.isBlank(english)) {
				english = null;
			}
		}
		return english;
	}

	/**
	 * Replace the English text with a new text.
	 * 
	 * The MultiLingualString needs to contain English text, in order to be able to be replaced.
	 * 
	 * @param destination
	 * @param newText
	 */
	public void replaceEnglish(MultiLingualString destination, String newText) {
		if (destination.getStringMap() == null) {
			destination.setStringMap(this.english(newText).getStringMap());
		} else {
			destination.getStringMap().put(Lang.EN, newText);
		}

		// if (destination.getStringMap() == null || destination.getStringMap().get(Lang.EN) == null) {
		// ("The MultiLingualString did not contain any text, it should contain it in order to be able to be replaced. StringMap == "
		// + destination.getStringMap() + ".");
		// }
	}

	/**
	 * Utility for using JPA in combination with MultiLingualString. It copies the values not the objects.
	 * 
	 * Be aware that this supports only the English language yet.
	 * 
	 * 
	 * @param source
	 * @param destination
	 */
	public void copyMultiLingual(Object source, Object destination) {
		Method[] methods = source.getClass().getMethods();
		for (Method getMethod : methods) {
			if (getMethod.getReturnType().equals(MultiLingualString.class) && getMethod.getParameterTypes().length == 0) {
				for (Method setMethod : methods) {
					if (getMethod.getName().substring(XET).equals(setMethod.getName().substring(XET))
							&& setMethod.getParameterTypes().length == LENGTH
							&& setMethod.getParameterTypes()[SET_INDEX].equals(MultiLingualString.class)) {
						try {
							MultiLingualString s = (MultiLingualString) getMethod.invoke(source);
							MultiLingualString d = (MultiLingualString) getMethod.invoke(destination);
							if (s == null ^ d == null) {
								setMethod.invoke(destination, s);
							}
							if (s != null && d != null) {
								// A source string and a destination object has been found.
								if (s.getStringMap() != null && this.getEnglish(s) != null) {
									this.replaceEnglish(d, this.getEnglish(s));
								} else {
									// invoke setMethod with null
									MultiLingualString nullObject = null;
									setMethod.invoke(destination, nullObject);
								}
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new VmeException(e);
						}
					}
				}
			}
		}
	}
}
