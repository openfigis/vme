package org.vme.service;


import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Authority;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

import au.com.bytecode.opencsv.CSVWriter;



/**
 * 
 * @author Roberto Empiri; Fabio Fiorellato
 * 
 */

public class CsvService {

	@Inject 
	private VmeSearchDao dao;
	
	@Inject @ConceptProvider
	private ReferenceDaoImpl refDao;
	
	public String createCsvFile(String authorityAcronym) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(baos);
		
		CSVWriter csvWriter = new CSVWriter(osw, ',', '"');
		
		List<VmeDto> vme = dao.searchVme(Long.parseLong(authorityAcronym), 0, 0, 0, "");

		VmeDto dto = null;
		
		for(int i=0;i<10;i++) {
			dto = new VmeDto();
			dto.setLocalName("Foo" + i);
			dto.setValidityPeriodFrom(2000 + i);
			dto.setValidityPeriodTo(2001 + i);

			vme.add(dto);
		}
		
		csvWriter.writeNext(new String[] { "Vme Name", "Validity Start", "Validity End" });

		for (VmeDto v : vme) {
			csvWriter.writeNext(new String[] { v.getLocalName(), String.valueOf(v.getValidityPeriodFrom()), String.valueOf(v.getValidityPeriodTo()) });
		}
			
		csvWriter.flush();
		csvWriter.close();
		
		return new String(baos.toByteArray(), "UTF-8");
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