package org.fao.fi.vme.sync2.mapping.xml;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.purl.dc.elements._1.Title;

public class FigisDocBuilder {

	ObjectFactory f = new ObjectFactory();

	public void specificMeasures(FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	public void vmeHistory(FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	public void rfmoHistory(FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	public void profile(FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	public void generalMeasures(FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

	}

	/**
	 * VME Identifier fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:FigisID
	 * 
	 * name fi:FIGISDoc/fi:VME/fi:VMEIdent/dc:Title
	 * 
	 * geographicLayerId fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:WaterAreaRef/fi:ForeignID@CodeSystem="vme"/@Code
	 * 
	 * TODO areaType fi:FIGISDoc/fi:VME/fi:VMEIdent/VMEType/@Value
	 * 
	 * criteria fi:FIGISDoc/fi:VME/fi:VMEIdent/VMECriteria/@Value
	 * 
	 * ValidityPeriod/beginYear fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:Range@Type="Time"/fi:Min
	 * 
	 * ValidityPeriod/endYear fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:Range@Type="Time"/fi:Max
	 * 
	 * @param vme
	 *            *
	 * 
	 * @param figisDoc
	 */
	public void vme(Vme vmeDomain, FIGISDoc figisDoc) {
		VMEIdent vmeIdent = new VMEIdent();

		FigisID figisID = new FigisID();
		figisID.setContent(vmeDomain.getId().toString());

		Title title = new Title();
		title.setContent(vmeDomain.getName().getStringMap().get(Lang.EN));

		WaterAreaRef waterAreaRef = new WaterAreaRef();

		ForeignID foreignID = new ForeignID();
		foreignID.setCodeSystem(Figis.CODE_SYSTEM);
		foreignID.setCode(vmeDomain.getGeoRefList().get(0).getGeographicFeatureID());
		waterAreaRef.getFigisIDsAndForeignIDs().add(foreignID);

		Min min = f.createMin();
		min.setContent(vmeDomain.getValidityPeriod().getBeginYear().toString());
		JAXBElement<Min> minJAXBElement = f.createRangeMin(f.createMin());

		Max max = f.createMax();
		max.setContent(vmeDomain.getValidityPeriod().getEndYear().toString());
		JAXBElement<Max> maxJAXBElement = f.createRangeMax(f.createMax());

		Range range = f.createRange();

		range.getContent().add(minJAXBElement);
		range.getContent().add(maxJAXBElement);

		VMECriteria vmeCriteria = new VMECriteria();
		vmeCriteria.setValue(vmeDomain.getCriteria());

		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(figisID);
		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(title);
		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(waterAreaRef);
		vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(vmeCriteria);
		// vmeIdent.getFigisIDsAndWaterAreaRevesAndOrgReves().add(range);

		VME vme = new VME();
		vme.setVMEIdent(vmeIdent);
		figisDoc.setVME(vme);

	}

	public void year(FIGISDoc figisDoc, Object year) {
		figisDoc.getVME().getVMEIdent().getFigisIDsAndWaterAreaRevesAndOrgReves().add(year);
	}

}
