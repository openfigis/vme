package org.vme.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

import au.com.bytecode.opencsv.CSVWriter;



/**
 * 
 * @author Roberto Empiri; Fabio Fiorellato
 * 
 */

public class CsvService {

	@Inject
	private VmeDao vdao;
	
	@Inject @ConceptProvider
	private ReferenceDaoImpl refDao;
	
	@Inject
	MultiLingualStringUtil UTIL;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public String createCsvFile(String authorityAcronym) throws Exception {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(baos);
		CSVWriter csvWriter = new CSVWriter(osw, ',', '"');
	
		/* Note: 	this block handles wrong request from RFMO
		 * 		 	so they can access their file by querying by Id
		 * 			or the Acronym
		 */	
		
		long authorityId;
		
		
		try {
			authorityId = Long.parseLong(authorityAcronym);
		} catch(NumberFormatException e){
			log.info("authority {} is an acronym", authorityAcronym);
			authorityId = getAuthorityIdByAcronym(authorityAcronym);
		}
	
		/* Note: 	this for block removes vmes from other
		 * 			RFMO by reconising them from RFMO`s id
		 */
		
		List<Vme> vme = vdao.loadVmes();
		
		for (Vme v : vme) {
			if (!v.getRfmo().getId().equals(authorityId)){
				vme.remove(v);
			}
		}
		
		List<String[]> strings = stringBuilderFromCollection(vme);

		for (String[] s : strings) {
			csvWriter.writeNext(s);
		}
			
		csvWriter.flush();
		csvWriter.close();
		
		return new String(baos.toByteArray(), "UTF-8");
	}
	
	/** stringBuilderFromCollection(List<VmeDto> vmeDtoList, List<Vme> vmeList) is a method who manages 
	 * consistency of data from Vme and VmeDto
	 */
	
	public List<String[]> stringBuilderFromCollection(List<Vme> vmeList) {
		
		List<String[]> result = new ArrayList<String[]>();
		result.add(new String[] { "Vme Name","Geographical Area", "Area Type",
				"Validity Start", "Validity End","Criteria" , "SpecificMeasure", "Spec M init", "Spec M end" });

		for (Vme vme : vmeList) {
			List<SpecificMeasure> sm = vme.getSpecificMeasureList();
			result.add(new String[]{UTIL.getEnglish(vme.getName()), vme.getAreaType(), String.valueOf(vme.getValidityPeriod().getBeginYear()), 
					String.valueOf(vme.getValidityPeriod().getEndYear()), vme.getCriteria(), UTIL.getEnglish(sm.get(0).getVmeSpecificMeasure()), 
					String.valueOf(sm.get(0).getValidityPeriod().getBeginYear()), String.valueOf(sm.get(0).getValidityPeriod().getEndYear())});
			sm.remove(0);
			
			for (SpecificMeasure specificMeasure : sm) {
				result.add(new String[]{"","","","","","",UTIL.getEnglish(specificMeasure.getVmeSpecificMeasure()), String.valueOf(sm.get(0).getValidityPeriod().getBeginYear()),
						String.valueOf(sm.get(0).getValidityPeriod().getEndYear())});
			}
			
		}
		
		return result;
	}

	public long getAuthorityIdByAcronym(String authorityAcronym){
		
		List<Authority> authorities = refDao.getAllAuthorities();
		
		for (Authority authority : authorities) {
			if(authority.getAcronym().equals(authorityAcronym)){
				return authority.getId();
			}	
		}
		return 0;
	}
	

	
}