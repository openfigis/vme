/**
 * 
 */
package org.vme.service.search;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Fabrizio Sibeni
 * 
 */

public class ServiceResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7695264301652005085L;

	private ServiceRequest request;

	private List<T> resultList;

	/**
	 * @param request
	 */
	public ServiceResponse(ServiceRequest request) {
		super();
		this.request = request;
		resultList = new LinkedList<T>();
	}

	public int getTotalResult() {
		return resultList.size();
	}

	/**
	 * @return the resultList
	 */
	public List<T> getResultList() {
		return resultList;
	}

	/**
	 * @return the request
	 */
	public ServiceRequest getRequest() {
		return request;
	}

	public void addElement(T dto) {
		resultList.add(dto);
	}

	@SuppressWarnings("unchecked")
	public void addElements(List<?> dtos) {
		for (T dto : (List<T>) dtos) {
			resultList.add(dto);
		}
	}

	@SuppressWarnings("unchecked")
	public void addElements(Collection<?> dtos) {
		for (T dto : (Collection<T>) dtos) {
			resultList.add(dto);
		}
	}

}
