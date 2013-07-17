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

public class VmeSearchResult {

	
	private VmeRequestDto request;
	
	private List<VmeSearchDto> resultList;

	/**
	 * @param request
	 */
	public VmeSearchResult(VmeRequestDto request) {
		super();
		this.request = request;
		resultList = new LinkedList<VmeSearchDto>();
	}
	
	
	public int getTotalResult(){
		return resultList.size();
	}
	
	
	/**
	 * @return the resultList
	 */
	public List<VmeSearchDto> getResultList() {
		return resultList;
	}





	public void addElement(VmeSearchDto vmeDto){
		resultList.add(vmeDto);
	}
	
	
	
}
