/**
 * 
 */
package org.vme.dao;

import java.util.List;

import org.fao.fi.vme.domain.dto.VmeDto;

/**
 * @author Fabrizio Sibeni
 *
 */
public interface VmeSearchDao  {
	public List<VmeDto> searchVme(long authority_id, long type_id, long criteria_id, int year, String text) throws Exception; 
	public List<VmeDto> getVmeById(long id, int year);
	public List<VmeDto> getVmeByInventoryIdentifier(String inv_id, int year);
	public List<VmeDto> getVmeByGeographicFeatureId(String geo_id, int year);
}
