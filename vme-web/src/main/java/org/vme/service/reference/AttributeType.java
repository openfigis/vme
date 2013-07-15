/**
 * 
 */
package org.vme.service.reference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fao.fi.vme.domain.MultiLingualString;





/**
 * Concept attribute type.
 * @author  Francesco
 */
public class AttributeType implements Serializable {
	private static final long serialVersionUID = 1821005114580326621L;
	private final static String TYPE_STRING = "STRING";
	private final static String TYPE_MULTILINGUAL_STRING = "MULTILINGUAL_STRING";
	private final static String TYPE_DECIMAL = "DECIMAL";
	private final static String TYPE_SMALLINT = "SMALLINT";
	private final static String TYPE_INTEGER = "INTEGER";
	private final static String TYPE_DOUBLE = "DOUBLE";
	private final static String TYPE_REAL = "REAL";
	private final static String TYPE_BOOLEAN = "BOOLEAN";
	private final static String TYPE_DATE = "DATE";
	
	/**
	 * The string attribute type constant.
	 */
	public final static AttributeType STRING = new AttributeType(TYPE_STRING);
	/**
	 * The multilingual string type constant.
	 */
	public final static AttributeType MULTILINGUAL_STRING = new AttributeType(TYPE_MULTILINGUAL_STRING);
	/**
	 * The decimal type constant.
	 */
	public final static AttributeType DECIMAL = new AttributeType(TYPE_DECIMAL);
	/**
	 * The smallint type constant.
	 */
	public final static AttributeType SMALLINT = new AttributeType(TYPE_SMALLINT);
	/**
	 * The numeric type constant.
	 */
	public final static AttributeType INTEGER = new AttributeType(TYPE_INTEGER);
	/**
	 * The float type constant.
	 */
	public final static AttributeType DOUBLE = new AttributeType(TYPE_DOUBLE);
	/**
	 * The real type constant.
	 */
	public final static AttributeType REAL = new AttributeType(TYPE_REAL);
	/**
	 * The boolean type constant.
	 */
	public final static AttributeType BOOLEAN = new AttributeType(TYPE_BOOLEAN);
	/**
	 * The date type constant.
	 */
	public final static AttributeType DATE = new AttributeType(TYPE_DATE);
	
	private final static List<String> TYPE_KEYS = Collections.unmodifiableList(Arrays.asList(TYPE_STRING, TYPE_MULTILINGUAL_STRING, TYPE_DECIMAL, TYPE_SMALLINT, TYPE_INTEGER, TYPE_DOUBLE, TYPE_REAL, TYPE_BOOLEAN, TYPE_DATE));
	/**
	 * The set of defined attribute types.
	 */
	public final static Set<AttributeType> TYPES = Collections.unmodifiableSet(new HashSet<AttributeType>(Arrays.asList(STRING, MULTILINGUAL_STRING, DECIMAL, SMALLINT, INTEGER, DOUBLE, REAL, BOOLEAN, DATE)));
	// The runtime types
	private final static Map<String, Class<?>> RUNTIME_TYPES = new HashMap<String , Class<?>>() {{
	    put(TYPE_STRING, String.class);
	    put(TYPE_MULTILINGUAL_STRING, MultiLingualString.class);
	    put(TYPE_DECIMAL, BigDecimal.class);
	    put(TYPE_SMALLINT, Short.class);
	    put(TYPE_INTEGER, Long.class);
	    put(TYPE_DOUBLE, Double.class);
	    put(TYPE_REAL, Float.class);
	    put(TYPE_BOOLEAN, Boolean.class);
	    put(TYPE_DATE, Date.class);
	}}; 
	// The runtime types
	private final static Map<String, Integer> JDBC_TYPES = new HashMap<String , Integer>() {{
	    put(TYPE_STRING, Types.VARCHAR);
	    put(TYPE_MULTILINGUAL_STRING, Types.VARCHAR);
	    put(TYPE_DECIMAL, Types.DECIMAL);
	    put(TYPE_SMALLINT, Types.SMALLINT);
	    put(TYPE_INTEGER, Types.BIGINT);
	    put(TYPE_DOUBLE, Types.DOUBLE);
	    put(TYPE_REAL, Types.REAL);
	    put(TYPE_BOOLEAN, Types.SMALLINT);
	    put(TYPE_DATE, Types.DATE);
	}}; 
	
	private String type;
	
	/**
	 * Constructor
	 * @param type, the string representation
	 */
	private AttributeType(String type) {
		this.type = type;
	}
	
	/**
	 * Return the string representation.
	 * @return the corresponding string representation
	 */
	private String getType() {
		return type;
	}
	
	/**
	 * Return the attribute type corresponding to a given string representation.
	 * @param type
	 * @return the string representation
	 */
	public static AttributeType valueOf(String type) {
		if (TYPE_KEYS.contains(type)) {
			return new AttributeType(type);
		}
		throw new IllegalArgumentException("Invalid type: " + type);
	}
	
	
	/**
	 * Return the string representation of the attribute type.
	 */
	//@Override
	public String toString() {
		return getType();
	}
	
	/**
	 * Return the Java runtime type corresponding to the attribute type
	 * @return the Java class
	 */
	public Class<?> getRuntimeType() {
		return RUNTIME_TYPES.get(type);
	}
	
	/**
	 * Return the JDBC type corresponding to the attribute type
	 * @return the JDBC type
	 */
	public int getJdbcType() {
		return JDBC_TYPES.get(type);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AttributeType)) {
			return false;
		}
		AttributeType other = (AttributeType) obj;
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
	
	
}
