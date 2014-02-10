/**
 * 
 */
package org.vme.service.dao;

import java.util.List;

import org.fao.fi.vme.domain.dto.VmeDto;

/**
 * @author Fabrizio Sibeni
 *
 */
public interface VmeSearchDao  {
	public List<VmeDto> searchObservations(long authority_id, long type_id, long criteria_id, int year, String text) throws Exception; 
	public List<VmeDto> getObservationById(long id, int year);
	public List<VmeDto> getObservationByInventoryIdentifier(String inv_id, int year);
	public List<VmeDto> getObservationByGeographicFeatureId(String geo_id, int year);
}
