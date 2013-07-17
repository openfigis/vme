/**
 * 
 */
package org.vme.service.search.vme;

import org.vme.service.dto.VmeGetRequestDto;
import org.vme.service.dto.VmeSearchRequestDto;
import org.vme.service.dto.VmeSearchResult;

/**
 * @author Fabrizio Sibeni
 *
 */
public interface SearchService {

	
	public VmeSearchResult search(VmeSearchRequestDto request);
	
	public VmeSearchResult get(VmeGetRequestDto request);
	
}
