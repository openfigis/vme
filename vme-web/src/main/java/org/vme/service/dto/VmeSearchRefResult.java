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

public class VmeSearchRefResult {

	
	private VmeSearchRefRequestDto request;
	
	private List<VmeSearchRefDto> resultList;

	/**
	 * @param request
	 */
	public VmeSearchRefResult(VmeSearchRefRequestDto request) {
		super();
		this.request = request;
		resultList = new LinkedList<VmeSearchRefDto>();
	}
	
	
	public int getTotalResult(){
		return resultList.size();
	}
	
	
	/**
	 * @return the resultList
	 */
	public List<VmeSearchRefDto> getResultList() {
		return resultList;
	}





	public void addElement(VmeSearchRefDto dto){
		resultList.add(dto);
	}
	
	public void addElements(List<VmeSearchRefDto> dtos){
		for (VmeSearchRefDto vmeSearchRefDto : dtos) {
			resultList.add(vmeSearchRefDto);
		}
	}
	
	
}
