/**
 * 
 */
package org.vme.service.dto;

import java.util.LinkedList;
import java.util.List;



/**
 * @author Fabrizio Sibeni
 *
 */

public class VmeServiceResult {

	
	private VmeSearchRequestDto request;
	
	private List<VmeDto> resultList;

	/**
	 * @param request
	 */
	public VmeServiceResult(VmeSearchRequestDto request) {
		super();
		this.request = request;
		resultList = new LinkedList<VmeDto>();
	}
	
	
	public int getTotalResult(){
		return resultList.size();
	}
	
	
	/**
	 * @return the resultList
	 */
	public List<VmeDto> getResultList() {
		return resultList;
	}





	public void addElement(VmeDto vmeDto){
		resultList.add(vmeDto);
	}
	
	
	
}
