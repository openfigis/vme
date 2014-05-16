package org.vme.service;


import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.VmeSearchDao;
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
	
	@Inject 
	private VmeSearchDao searchDao;
	
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
	
		/* Note: 	VmeSearchDao 	--> Vme Name, Vme year, Vme geoRef, Vme startY, Vme endY
		 * 			VmeDao 			--> Vme AreaType, Vme SpecMeasure
		 */
		
		List<VmeDto> vmeDto = searchDao.searchVme(authorityId, 0, 0, 0, "");
		List<Vme> vme = vdao.loadVmes();
		
		for (Vme v : vme) {
			if (!v.getRfmo().getId().equals(authorityId)){
				vme.remove(v);
			}
		}
		
		VmeDto dto = null;
		
		for(int i=0;i<10;i++) {
			dto = new VmeDto();
			dto.setLocalName("Foo" + i);
			dto.setValidityPeriodFrom(2000 + i);
			dto.setValidityPeriodTo(2001 + i);

			vmeDto.add(dto);
		}
		
		List<String[]> strings = stringBuilderFromCollection(vmeDto, vme);

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
	
	private List<String[]> stringBuilderFromCollection(List<VmeDto> vmeDtoList, List<Vme> vmeList) {
		
		List<String[]> result = new ArrayList<String[]>();
		result.add(new String[] { "Vme Name", "Year", "Geographical Area", "Area Type",
				"Validity Start", "Validity End", "SpecificMeasure" });

		for (Vme vme : vmeList) {
			
			long vmeId = vme.getId();
			
			while (vmeDtoList.iterator().hasNext()) {
				VmeDto vmeDto = vmeDtoList.iterator().next();
				if(vmeId == vmeDto.getVmeId()){
					result.addAll((stringBuilderFromSingleVme(vme, vmeDto)));
				}
			}
			
		}
		
		return result;
	}

	/** once stringBuilderFromCollection(List<VmeDto> vmeDtoList, List<Vme> vmeList) checked the consistency of 
	 * the two elements data from both list are added into a list who next will be added to the .csv file
	 */
	
	private List<String[]> stringBuilderFromSingleVme(Vme vme, VmeDto vmeDto) {
		
		List<String[]> result = new ArrayList<String[]>();
		List<SpecificMeasure> sm = vme.getSpecificMeasureList();
		
		result.add(new String[]{vmeDto.getLocalName(), String.valueOf(vmeDto.getYear()), vmeDto.getGeoArea(), 
				vme.getAreaType(), String.valueOf(vmeDto.getValidityPeriodFrom()), String.valueOf(vmeDto.getValidityPeriodTo()),
				UTIL.getEnglish(sm.get(0).getVmeSpecificMeasure())});
		sm.remove(0);
		
		for (SpecificMeasure specificMeasure : sm) {
			result.add(new String[]{"","","","","","",UTIL.getEnglish(specificMeasure.getVmeSpecificMeasure())});
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