/**
 * 
 */
package org.vme.service.dto;

/**
 * @author SIBENI
 *
 */
public interface VmeRequestDto {
	/**
	 * @return the uuid of the request
	 */
	public String getUuid();
	
	
	/**
	 * @return the year
	 */
	public int getYear();

	/**
	 * @return <code>true</code> if request has the year defined, <code>false</code> otherwise
	 */
	public boolean hasYear();
	
	
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) ;
	
	
}
