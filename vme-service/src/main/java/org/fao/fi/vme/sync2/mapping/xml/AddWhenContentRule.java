package org.fao.fi.vme.sync2.mapping.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Jaxb elements only need to be filled when there is actual content.
 * 
 * 
 * If one of the content objects has content, add the element to the list.
 * 
 * @author Erik van Ingen
 * 
 */
public class AddWhenContentRule {

	private List<Object> objectListToCheck = new ArrayList<Object>();
	private List<Object> elementList = new ArrayList<Object>();

	/**
	 * if one of the content objects has a value, all elements will be added to the list.
	 * 
	 * @param content
	 * @return
	 */
	public AddWhenContentRule check(Object content) {
		this.objectListToCheck.add(content);
		return this;
	}

	/**
	 * The object which potentially can be added to the list.
	 * 
	 * @param element
	 * @return
	 */
	public AddWhenContentRule beforeAdding(Object element) {
		elementList.add(element);
		return this;
	}

	/***
	 * 
	 * the list which needs to be extend with new objects.
	 * 
	 * @param listToExtend
	 */
	public void to(List<Object> listToExtend) {
		boolean add = false;
		for (Object content : objectListToCheck) {
			if (content != null) {
				// do fine grained check on literals
				if (content instanceof String) {
					if (!StringUtils.isBlank((String) content)) {
						add = true;
					}
				} else {
					add = true;
				}
			}
		}
		if (add) {
			for (Object element : elementList) {
				if (element != null) {
					listToExtend.add(element);
				}
			}
		}

	}
}
