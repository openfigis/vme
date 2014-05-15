/**
 * (c) 2014 FAO / UN (project: fi-security-server)
 */
package org.fao.fi.vme.rsg.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.enterprise.inject.Alternative;

import org.fao.fi.security.server.javax.filters.origin.support.AbstractIPIdentifier;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 15 May 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 15 May 2014
 */ 
@Alternative
public class FAOProxyIPIdentifier extends AbstractIPIdentifier {
	private String FAOProxyIPRegexp = "199.46.21.[0-9]{1,3}";
	
	/**
	 * @return the 'fAOProxyIP' value
	 */
	public String getFAOProxyIPRegexp() {
		return this.FAOProxyIPRegexp;
	}

	/**
	 * @param FAOProxyIPRegexp the 'FAOProxyIPRegexp' value to set
	 */
	public void setFAOProxyIPRegexp(String FAOProxyIPRegexp) {
		if(FAOProxyIPRegexp == null)
			throw new IllegalArgumentException("The set FAO proxy IP Regexp cannot be NULL");
		
		this.FAOProxyIPRegexp = FAOProxyIPRegexp;
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.security.server.javax.filters.origin.IPIdentifier#excludeRemoteHost(java.lang.String)
	 */
	@Override
	public boolean excludeRemoteHost(String remoteHost) {
		return "0:0:0:0:0:0:0:1".equals(remoteHost);
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.security.server.javax.filters.origin.IPIdentifier#includeXFF(java.lang.String)
	 */
	@Override
	public boolean includeXFF(String XFF) {
		return XFF != null;
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.security.server.javax.filters.origin.support.AbstractIPIdentifier#filter(java.lang.String[])
	 */
	@Override
	protected String[] filter(String[] IPs) {
		Collection<String> filtered = new ArrayList<String>();
		
		for(String IP : IPs) {
			if(!Pattern.matches(this.FAOProxyIPRegexp, IP))
				filtered.add(IP);
		}
		
		return filtered.toArray(new String[filtered.size()]);
	}
}
