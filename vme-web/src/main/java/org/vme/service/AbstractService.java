package org.vme.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

abstract class AbstractService {

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;
	
	public List<Vme> filterVmePerRfmo(List<Vme> vmeList, String authorityAcronym){
		List<Vme> vmeListPerRfmo = new ArrayList<Vme>();
		
		for (Vme v : vmeList) {
			if (v.getRfmo().getId().equals(authorityAcronym)) {
				vmeListPerRfmo.add(v);
			}
		}
		
		return vmeListPerRfmo;
	}
	
	public List<Vme> filterVmePerScope(List<Vme> vmeList, String scope){
		List<Vme> vmeListPerScope = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			try {
				if (refDao.getReferenceByID(VmeScope.class, vme.getScope()).getName().equals(scope)) {
					vmeListPerScope.add(vme);
				}
			} catch (ReferenceServiceException e) {
				throw new VmeException(e);
			}
		}
		return vmeListPerScope;
		
	}
	
	public long getAuthorityIdByAcronym(String authorityAcronym) {

		List<Authority> authorities = refDao.getAllAuthorities();

		for (Authority authority : authorities) {
			if (authority.getAcronym().equals(authorityAcronym)) {
				return authority.getId();
			}
		}
		return 0;
	}
	
	public String dataString() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		return dateFormat.format(cal.getTime());
	}
}
