package org.fao.fi.vme.domain.model;

/**
 * 
 * The VME model has business and reference data objects. This interface helps
 * to distinguish them more explicit.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 * @param <T>
 */

public interface ReferenceDataObject<T> {

	public T getId();

}
