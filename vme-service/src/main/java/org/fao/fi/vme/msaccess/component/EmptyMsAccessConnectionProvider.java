package org.fao.fi.vme.msaccess.component;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.VmeException;

@Alternative
public class EmptyMsAccessConnectionProvider extends MsAccessConnectionProvider {

	@Override
	protected String getDBLocation() {
		throw new VmeException("The empty ms-access provider should not be effectively used");
	}
}