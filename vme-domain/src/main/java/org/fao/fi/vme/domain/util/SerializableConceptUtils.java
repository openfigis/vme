/**
 * (c) 2014 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 27 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 27 Feb 2014
 */
public class SerializableConceptUtils {
	final static public String SEPARATOR = ":";
	final static public String NULL_REPLACEMENT = "$$__NULL__$$";
	
	final static public String toString(Serializable toConvert) {
		return ( toConvert == null ? NULL_REPLACEMENT : toConvert.toString() ).replaceAll("\\" + SEPARATOR, "$$__" + SEPARATOR + "__$$");
	}
	
	final static public String toString(Serializable... toConvert) {
		StringBuilder builder = new StringBuilder();
		
		for(Serializable in : toConvert)
			builder.append(toString(in)).append(SEPARATOR);
		
		return builder.toString().replaceAll("\\" + SEPARATOR + "$", "");
	}
	
	final static public String[] parts(String toSplit) {
		Collection<String> parts = new ArrayList<String>();
		
		for(String in : toSplit.split("\\" + SEPARATOR, -1)) {
			if(NULL_REPLACEMENT.equals(in))
				parts.add(null);
			else
				parts.add(in);
		}
		
		return parts.toArray(new String[parts.size()]);
	}
}