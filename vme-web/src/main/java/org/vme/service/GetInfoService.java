package org.vme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.webservice.SpecificMeasureList;
import org.vme.dao.transaction.VmeDaoTransactional;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.SpecificMeasureDto;
import org.vme.service.dto.VmeDto;
import org.vme.service.dto.VmeResponse;
import org.vme.service.dto.VmeSmResponse;
import org.vme.web.cache.Cached;

public class GetInfoService extends AbstractService {

	@Inject
	private VmeDaoTransactional vDao;

	@Inject
	private DtoTranslator translator;

	@Cached
	public VmeSmResponse vmeIdentifier2SpecificMeasure(String vmeIdentifier, int vmeYear) {

		VmeSmResponse vmeSmResponse = new VmeSmResponse(UUID.randomUUID());
		List<SpecificMeasureDto> specificMeasureList = new ArrayList<SpecificMeasureDto>();
		List<Vme> vmeList = vDao.loadVmes();
		filterVmePerInventoryIdentifier(vmeList, vmeIdentifier);
		if (!vmeList.isEmpty()) {
			VmeDto vmeDto = translator.doTranslate4Vme(vmeList.get(0), vmeYear);

			vmeSmResponse.setVmeId(vmeDto.getVmeId());
			vmeSmResponse.setInventoryIdentifier(vmeDto.getInventoryIdentifier());
			vmeSmResponse.setVmeType(vmeDto.getVmeType());
			vmeSmResponse.setGeoArea(vmeDto.getGeoArea());
			vmeSmResponse.setLocalName(vmeDto.getLocalName());
			vmeSmResponse.setOwner(vmeDto.getOwner());

			if (vmeYear <= 2004 && vmeYear != 0) {
				vmeSmResponse.setNoObsNote(vmeYear);
			}

			if (vmeYear == 0) {
				vmeSmResponse.setNote("These are all the Specific Measure for the Vme");
				for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
					specificMeasureList.add(translator.doTranslate4Sm(sm));
				}
			}

			if (vmeYear > 2004) {
				while (specificMeasureList.isEmpty() && vmeYear > 2004) {
					for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
						if (sm.getYear() == vmeYear) {
							specificMeasureList.add(translator.doTranslate4Sm(sm));
						}
					}

					if (specificMeasureList.isEmpty() && vmeSmResponse.getNote() == null) {
						vmeSmResponse.setNoObsNote(vmeYear);
					}
					vmeYear--;
				}
			}

		}
		if (specificMeasureList.isEmpty()) {
			specificMeasureList.add(new SpecificMeasureDto());
		}

		vmeSmResponse.setResponseList(specificMeasureList);
		return vmeSmResponse;
	}

	@Cached
	public VmeResponse ownerScope2Vmes(String owner, String scope, int year) {

		VmeResponse vmeResponse = new VmeResponse(UUID.randomUUID());

		List<Vme> vmeListPerRfmo = filterVmePerRfmo(vDao.loadVmes(), owner);
		List<Vme> vmeListPerScope = filterVmePerScope(vmeListPerRfmo, scope);
		List<VmeDto> vmeDtoList = new ArrayList<VmeDto>();

		if (year <= 2004 && year != 0) {
			vmeResponse.setNoObsNote(year);
		}

		if (year == 0) {
			vmeResponse.setNote("These are all the Vmes for the specified Rfmo and the specified Scope");
			for (Vme vme : vmeListPerScope) {
				VmeDto vmeDto = translator.doTranslate4Vme(vme, year);
				vmeDtoList.add(vmeDto);
			}
		}

		if (year > 2004) {
			while (vmeDtoList.isEmpty() && year > 2004) {
				for (Vme vme : vmeListPerScope) {
					VmeDto vmeDto = translator.doTranslate4Vme(vme, year);
					if (vmeDto.getYear() == year) {
						vmeDtoList.add(vmeDto);
					}
				}
				if (vmeDtoList.isEmpty() && vmeResponse.getNote() == null) {
					vmeResponse.setNoObsNote(year);
				}
				year--;
			}
		}

		vmeResponse.setVmeDto(vmeDtoList);
		return vmeResponse;
	}

	@Cached
	public SpecificMeasureList vmeIdentifier2SpecificmeasureXML(String vmeIdentifier, int year) {

		SpecificMeasureList smList = new SpecificMeasureList();

		List<Vme> vmeList = vDao.loadVmes();
		filterVmePerInventoryIdentifier(vmeList, vmeIdentifier);

		for (Vme vme : vmeList) {
			for (SpecificMeasure sm : vme.getSpecificMeasureList()) {
				smList.getSpecificMeasures().add(translator.doTranslate4SmType(sm));
			}
		}
		return smList;
	}

}