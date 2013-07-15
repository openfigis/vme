package org.fao.fi.figis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the REF_WATER_AREA database table.
 * 
 */
@Entity
@Table(name = "REF_WATER_AREA", schema = "figis")
public class RefWaterArea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 548777554544950655L;

	@Id
	@Column(name = "CD_WATER_AREA")
	private long id;

	private Long area;

	@Column(name = "AREA_SIZE")
	private Long areaSize;

	@Column(name = "CD_ISO3_CODE")
	private String iso3Code;

	@Column(name = "CD_META")
	private Integer meta;

	@Column(name = "CD_UN_CODE")
	private Long unCode;

	@Column(name = "CD_WATER_AREA_TYPE")
	private Long waterAreaType;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Column(name = "GRP_IND")
	private String grpInd;

	@Column(name = "MAX_LAT")
	private BigDecimal maxLat;

	@Column(name = "MAX_LONG")
	private BigDecimal maxLong;

	@Column(name = "MIN_LAT")
	private BigDecimal minLat;

	@Column(name = "MIN_LONG")
	private BigDecimal minLong;

	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getAreaSize() {
		return areaSize;
	}

	public void setAreaSize(Long areaSize) {
		this.areaSize = areaSize;
	}

	public String getIso3Code() {
		return iso3Code;
	}

	public void setIso3Code(String iso3Code) {
		this.iso3Code = iso3Code;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

	public Long getUnCode() {
		return unCode;
	}

	public void setUnCode(Long unCode) {
		this.unCode = unCode;
	}

	public Long getWaterAreaType() {
		return waterAreaType;
	}

	public void setWaterAreaType(Long waterAreaType) {
		this.waterAreaType = waterAreaType;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getGrpInd() {
		return grpInd;
	}

	public void setGrpInd(String grpInd) {
		this.grpInd = grpInd;
	}

	public BigDecimal getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(BigDecimal maxLat) {
		this.maxLat = maxLat;
	}

	public BigDecimal getMaxLong() {
		return maxLong;
	}

	public void setMaxLong(BigDecimal maxLong) {
		this.maxLong = maxLong;
	}

	public BigDecimal getMinLat() {
		return minLat;
	}

	public void setMinLat(BigDecimal minLat) {
		this.minLat = minLat;
	}

	public BigDecimal getMinLong() {
		return minLong;
	}

	public void setMinLong(BigDecimal minLong) {
		this.minLong = minLong;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefWaterArea other = (RefWaterArea) obj;
		if (id != other.id)
			return false;
		return true;
	}

}