package org.vme.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Vme;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

public class XlsService {

	@Inject
	private VmeDao vdao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	@Inject
	private WritableWorkbookFactory f;

	public ByteArrayInputStream createXlsFile(String authorityAcronym) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		/*
		 * Note: this for block removes vmes from other RFMO by recognising them
		 * from RFMO`s id
		 */

		List<Vme> vmeList = vdao.loadVmes();
		List<Vme> vmeListPerRfmo = new ArrayList<Vme>();

		for (Vme v : vmeList) {
			if (v.getRfmo().getId().equals(authorityAcronym)) {
				vmeListPerRfmo.add(v);
			}
		}

		WritableWorkbook ww = f.create(baos, vmeListPerRfmo);

		/*
		 * Note: this for block adds information and data to all worksheets of
		 * the returning file
		 */

		for (WritableSheet wSheet : ww.getSheets()) {
			f.fillWorkSheet(wSheet);
		}

		ww.write();
		ww.close();

		return new ByteArrayInputStream(baos.toByteArray());
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
