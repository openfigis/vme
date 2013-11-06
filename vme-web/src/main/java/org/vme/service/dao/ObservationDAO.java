/**
 * 
 */
package org.vme.service.dao;

import java.util.List;

import org.vme.service.dto.obs.ObservationDto;

/**
 * @author Fabrizio Sibeni
 *
 */
public interface ObservationDAO  {
	
	public List<ObservationDto> searchObservations(long authority_id, long type_id, long criteria_id, int year, String text) throws Exception; 
	public List<ObservationDto> getObservationById(long id, int year);
	public List<ObservationDto> getObservationByInevntoryIdentifier(String inv_id, int year);
	public List<ObservationDto> getObservationByGeographicFeatureId(String geo_id, int year);
}
