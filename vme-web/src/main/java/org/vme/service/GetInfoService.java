package org.vme.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.SpecificMeasureDto;

public class GetInfoService {

	@Inject
	private VmeDao vDao;

	@Inject
	private VmeSearchDao vSearchDao;

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl refDao;

	@Inject
	private
	DtoTranslator translator = new DtoTranslator();

	//	Once testing period ends this.UTIL will be removed
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();

	public List<SpecificMeasureDto> findInfo(String vmeIdentifier){

		List<SpecificMeasureDto> resultList = new ArrayList<SpecificMeasureDto>();

		try {
			for (VmeDto vmeDto : vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, 0)) {
				for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
					resultList.add(translator.doTranslate4Sm(sm));
				}
			}
		} catch (NullPointerException e){
			throw new VmeException(e);
		} finally {

			/*
			 * Note: just for testing in my local
			 */

			SpecificMeasure specificMeasure = new SpecificMeasure();

			specificMeasure.setYear(1900);
			specificMeasure.setVmeSpecificMeasure(UTIL.english("A specific measure for the year " + 1900));
			specificMeasure.setValidityPeriod(ValidityPeriodMock.create(1900, 1900 + 1));
			specificMeasure.setInformationSource(InformationSourceMock.create());
			specificMeasure.setReviewYear(1900 + 1);
			specificMeasure.setVme(VmeMock.create());
			resultList.add(translator.doTranslate4Sm(specificMeasure));

			/*
			 * Note: end of test part
			 */


		}
		
		return resultList;
	}

	public List<SpecificMeasureDto> findInfo(String vmeIdentifier, int vmeYear) {

		List<SpecificMeasureDto> resultList = new ArrayList<SpecificMeasureDto>();
		try {	
			for (VmeDto vmeDto : vSearchDao.getVmeByInventoryIdentifier(vmeIdentifier, vmeYear)) {
				for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
					resultList.add(translator.doTranslate4Sm(sm));
				}
			}
		} catch (NullPointerException e){
			throw new VmeException(e);
		} finally {

			/*
			 * Note: just for testing in my local
			 */

			SpecificMeasure specificMeasure = new SpecificMeasure();

			specificMeasure.setYear(1900);
			specificMeasure.setVmeSpecificMeasure(UTIL.english("A specific measure for the year " + 1900));
			specificMeasure.setValidityPeriod(ValidityPeriodMock.create(1900, 1900 + 1));
			specificMeasure.setInformationSource(InformationSourceMock.create());
			specificMeasure.setReviewYear(1900 + 1);
			specificMeasure.setVme(VmeMock.create());
			resultList.add(translator.doTranslate4Sm(specificMeasure));
			resultList.add(translator.doTranslate4Sm(specificMeasure));
			resultList.add(translator.doTranslate4Sm(specificMeasure));

			/*
			 * Note: end of test part
			 */


		}
		
		return resultList;
	}
}