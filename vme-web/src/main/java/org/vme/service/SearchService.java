package org.vme.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

public class SearchService {
	
	@Inject
	private VmeDao vDao;
	
	@Inject
	private VmeSearchDao vSearchDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;
	
	protected MultiLingualStringUtil u = new MultiLingualStringUtil();

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Object findByVmeIdentifier(String vme_Identifier, int vme_Year){
		
		List<VmeDto> vmeDtoList = vSearchDao.getVmeByInventoryIdentifier(vme_Identifier, vme_Year);
		List<SpecificMeasure> specMeasureList = new ArrayList<SpecificMeasure>();


		for (VmeDto vmeDto : vmeDtoList) {
			specMeasureList.addAll(vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList());
		}

		ArrayList<SpecificMeasure> responseList = new ArrayList<SpecificMeasure>(); // list filled with objects

		responseList.addAll(specMeasureList);
		JSONArray jsonArray = new JSONArray();


		for (int j = 0; j< responseList.size(); j++) {
			jsonArray.put(getJSONObject(responseList.get(j)));
		}

		return jsonArray;
	}
	
	public JSONObject getJSONObject(SpecificMeasure specificMeasure) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Id", specificMeasure.getId());
            obj.put("Text", u.getEnglish(specificMeasure.getVmeSpecificMeasure()));
            obj.put("Year", specificMeasure.getYear());
            obj.put("Validity period start", specificMeasure.getValidityPeriod().getBeginDate());
            obj.put("Validity period end", specificMeasure.getValidityPeriod().getEndDate());
        } catch (JSONException e) {
        	this.log.error("DefaultListItem.toString JSONException: "+e.getMessage(), e);
        }
        return obj;
    }
	
}
