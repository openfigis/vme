package org.vme.service.dto;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class SpecificMeasureDto {

	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private ValidityPeriodUtil VUTIL = new ValidityPeriodUtil();
	
	public String text;
	public int year;
	public int vpStart;
	public int vpEnd;
	public String sourceURL;
	
	
	public SpecificMeasureDto(SpecificMeasure sm) {
		this.text = UTIL.getEnglish(sm.getVmeSpecificMeasure());
		this.year = sm.getYear();
		this.vpStart = VUTIL.getBeginYear(sm.getValidityPeriod());
		this.vpEnd = VUTIL.getEndYear(sm.getValidityPeriod());
		this.sourceURL = sm.getInformationSource().getUrl().toExternalForm();
	}

}
