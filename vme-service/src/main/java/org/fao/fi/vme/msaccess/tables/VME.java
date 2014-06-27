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

	public String getRfbId() {
		return rfbId;
	}

	public void setRfbId(String rfbId) {
		this.rfbId = rfbId;
	}

	public String getVmeId() {
		return vmeId;
	}

	public void setVmeId(String vmeId) {
		this.vmeId = vmeId;
	}

	public int getYearID() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getVmeInventoryIdentifier() {
		return vmeInventoryIdentifier;
	}

	public void setVmeInventoryIdentifier(String vmeInventoryIdentifier) {
		this.vmeInventoryIdentifier = vmeInventoryIdentifier;
	}

	public String getVmeFeatureId() {
		return vmeFeatureId;
	}

	public void setVmeFeatureId(String vmeFeatureId) {
		this.vmeFeatureId = vmeFeatureId;
	}

	public int getVmeValidityStart() {
		return vmeValidityStart;
	}

	public void setVmeValidityStart(int vmeValidityStart) {
		this.vmeValidityStart = vmeValidityStart;
	}

	public int getVmeValidityEnd() {
		return vmeValidityEnd;
	}

	public void setVmeValidityEnd(int vmeValidityEnd) {
		this.vmeValidityEnd = vmeValidityEnd;
	}

	public String getVmeGeoform() {
		return vmeGeoform;
	}

	public void setVmeGeoform(String vmeGeoform) {
		this.vmeGeoform = vmeGeoform;
	}

	public String getVmeGeogArea1() {
		return vmeGeogArea1;
	}

	public void setVmeGeogArea1(String vmeGeogArea1) {
		this.vmeGeogArea1 = vmeGeogArea1;
	}

	public String getVmeGeogArea2() {
		return vmeGeogArea2;
	}

	public void setVmeGeogArea2(String vmeGeogArea2) {
		this.vmeGeogArea2 = vmeGeogArea2;
	}

	public String getVmeGeogAreaFAO() {
		return vmeGeogAreaFAO;
	}

	public void setVmeGeogAreaFAO(String vmeGeogAreaFAO) {
		this.vmeGeogAreaFAO = vmeGeogAreaFAO;
	}

	public String getVmeCoord() {
		return vmeCoord;
	}

	public void setVmeCoord(String vmeCoord) {
		this.vmeCoord = vmeCoord;
	}

	public String getVmeAreaType() {
		return vmeAreaType;
	}

	public void setVmeAreaType(String vmeAreaType) {
		this.vmeAreaType = vmeAreaType;
	}

	public String getVmeDescriptionPhysical() {
		return vmeDescriptionPhysical;
	}

	public void setVmeDescriptionPhysical(String vmeDescriptionPhysical) {
		this.vmeDescriptionPhysical = vmeDescriptionPhysical;
	}

	public String getVmeDescriptionBiology() {
		return vmeDescriptionBiology;
	}

	public void setVmeDescriptionBiology(String vmeDescriptionBiology) {
		this.vmeDescriptionBiology = vmeDescriptionBiology;
	}

	public String getVmeDescriptionImpact() {
		return vmeDescriptionImpact;
	}

	public void setVmeDescriptionImpact(String vmeDescriptionImpact) {
		this.vmeDescriptionImpact = vmeDescriptionImpact;
	}

	@Override
	public Object map() {
		Vme o = new Vme();

		MultiLingualStringUtil u = new MultiLingualStringUtil();
		EnglishTextUtil etu = new EnglishTextUtil();

		o.setName(u.english(this.getVmeId()));

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
		p.setGeoform(etu.english(this.getVmeGeoform()));
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

		if (this.getRfbId() == null) {
			throw new VmeException("found vme without reference to rfb. Vme = " + vmeId + ", RFMO is ");
		}

		return o;
	}
}
