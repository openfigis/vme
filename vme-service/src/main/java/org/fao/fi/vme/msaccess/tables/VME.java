package org.fao.fi.vme.msaccess.tables;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.batch.sync2.mapping.xml.EnglishTextUtil;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.mapping.ValidityPeriodRule;

public class VME implements TableDomainMapper {
	int iD;
	String rfbId;
	String vmeId;
	int yearId;

	String vmeInventoryIdentifier;
	String vmeFeatureId;

	int vmeValidityStart;
	int vmeValidityEnd;
	String vmeGeoform;
	String vmeGeogArea1;
	String vmeGeogArea2;
	String vmeGeogAreaFAO;
	String vmeCoord;
	String vmeAreaType;
	String vmeDescriptionPhysical;
	String vmeDescriptionBiology;
	String vmeDescriptionImpact;

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public String getRFB_ID() {
		return rfbId;
	}

	public void setRFB_ID(String rfbId) {
		this.rfbId = rfbId;
	}

	public String getVME_ID() {
		return vmeId;
	}

	public void setVME_ID(String vmeId) {
		this.vmeId = vmeId;
	}

	public int getYear_ID() {
		return yearId;
	}

	public void setYear_ID(int yearId) {
		this.yearId = yearId;
	}

	public String getVME_Inventory_Identifier() {
		return vmeInventoryIdentifier;
	}

	public void setVME_Inventory_Identifier(String vmeInventoryIdentifier) {
		this.vmeInventoryIdentifier = vmeInventoryIdentifier;
	}

	public String getVME_Feature_ID() {
		return vmeFeatureId;
	}

	public void setVME_Feature_ID(String vmeFeatureId) {
		this.vmeFeatureId = vmeFeatureId;
	}

	public int getVME_Validity_Start() {
		return vmeValidityStart;
	}

	public void setVME_Validity_Start(int vmeValidityStart) {
		this.vmeValidityStart = vmeValidityStart;
	}

	public int getVME_Validity_End() {
		return vmeValidityEnd;
	}

	public void setVME_Validity_End(int vmeValidityEnd) {
		this.vmeValidityEnd = vmeValidityEnd;
	}

	public String getVME_Geoform() {
		return vmeGeoform;
	}

	public void setVME_Geoform(String vmeGeoform) {
		this.vmeGeoform = vmeGeoform;
	}

	public String getVME_GeogArea1() {
		return vmeGeogArea1;
	}

	public void setVME_GeogArea1(String vmeGeogArea1) {
		this.vmeGeogArea1 = vmeGeogArea1;
	}

	public String getVME_GeogArea2() {
		return vmeGeogArea2;
	}

	public void setVME_GeogArea2(String vmeGeogArea2) {
		this.vmeGeogArea2 = vmeGeogArea2;
	}

	public String getVME_GeogAreaFAO() {
		return vmeGeogAreaFAO;
	}

	public void setVME_GeogAreaFAO(String vmeGeogAreaFAO) {
		this.vmeGeogAreaFAO = vmeGeogAreaFAO;
	}

	public String getVME_Coord() {
		return vmeCoord;
	}

	public void setVME_Coord(String vmeCoord) {
		this.vmeCoord = vmeCoord;
	}

	public String getVME_Area_Type() {
		return vmeAreaType;
	}

	public void setVME_Area_Type(String vmeAreaType) {
		this.vmeAreaType = vmeAreaType;
	}

	public String getVME_Description_Physical() {
		return vmeDescriptionPhysical;
	}

	public void setVME_Description_Physical(String vmeDescriptionPhysical) {
		this.vmeDescriptionPhysical = vmeDescriptionPhysical;
	}

	public String getVME_Description_Biology() {
		return vmeDescriptionBiology;
	}

	public void setVME_Description_Biology(String vmeDescriptionBiology) {
		this.vmeDescriptionBiology = vmeDescriptionBiology;
	}

	public String getVME_Description_Impact() {
		return vmeDescriptionImpact;
	}

	public void setVME_Description_Impact(String vmeDescriptionImpact) {
		this.vmeDescriptionImpact = vmeDescriptionImpact;
	}

	@Override
	public Object map() {
		Vme o = new Vme();

		MultiLingualStringUtil u = new MultiLingualStringUtil();
		EnglishTextUtil etu = new EnglishTextUtil();

		o.setName(u.english(this.getVME_ID()));

		// commented, ms access is not anymore supported..
		// o.setAreaType(this.VME_Area_Type);
		// o.setCriteria(this.)
		// o.setGeoform(this.VME_Geoform);
		o.setGeoArea(u.english(this.vmeGeogArea1));

		// o.setGeoArea(this.VME_GeogArea1);

		// o.setGeographicLayerId(this.)
		o.setId(new Long(this.iD));
		// o.setName(this.get)
		// o.setRfmo(rfmo)

		ValidityPeriodRule r = new ValidityPeriodRule(this.vmeValidityStart, this.vmeValidityEnd);
		ValidityPeriod vp = new ValidityPeriod();

		// Intervention Erik van Ingen 5 June 2014. Commented because this logic
		// is not used anymore and therefore does not need to be updated.
		// vp.setBeginYear(r.getStart());
		// vp.setEndYear(r.getEnd());
		o.setValidityPeriod(vp);

		Profile p = new Profile();
		p.setDescriptionBiological(etu.english(this.vmeDescriptionBiology));
		p.setDescriptionImpact(etu.english(this.vmeDescriptionImpact));
		p.setDescriptionPhisical(etu.english(this.vmeDescriptionPhysical));
		p.setGeoform(etu.english(this.getVME_Geoform()));
		p.setYear(this.yearId);
		p.setVme(o);

		List<Profile> l = new ArrayList<Profile>();
		l.add(p);
		o.setProfileList(l);

		List<GeoRef> geoRefList = new ArrayList<GeoRef>();
		GeoRef geoRef = new GeoRef();
		// geoRef.setVme(o);
		geoRef.setGeographicFeatureID(this.vmeFeatureId);
		geoRef.setYear(this.yearId);
		geoRefList.add(geoRef);
		o.setGeoRefList(geoRefList);

		o.setInventoryIdentifier(this.vmeInventoryIdentifier);

		if (this.getRFB_ID() == null) {
			throw new VmeException("found vme without reference to rfb. Vme = " + vmeId + ", RFMO is ");
		}

		return o;
	}
}
