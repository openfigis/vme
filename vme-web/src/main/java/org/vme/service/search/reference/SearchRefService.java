/**
 * 
 */
package org.vme.service.search.reference;

import org.vme.service.dto.VmeSearchRefRequestDto;
import org.vme.service.dto.VmeSearchRefResult;

/**
 * @author Fabrizio Sibeni
 *
 */
public interface SearchRefService {

	
	public VmeSearchRefResult search(VmeSearchRefRequestDto request);
	
}
