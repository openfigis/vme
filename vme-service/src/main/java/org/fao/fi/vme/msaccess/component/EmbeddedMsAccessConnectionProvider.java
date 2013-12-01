package org.fao.fi.vme.msaccess.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.enterprise.inject.Alternative;

@Alternative
public class EmbeddedMsAccessConnectionProvider extends MsAccessConnectionProvider {
	final static private String DEFAULT_MS_ACCESS_DB_RESOURCE = "VME_DB_production.accdb";
	
	private String _resource = DEFAULT_MS_ACCESS_DB_RESOURCE;
	
	public EmbeddedMsAccessConnectionProvider() {
		super();
	}

	public EmbeddedMsAccessConnectionProvider(String resource) {
		super();
		
		this._resource = resource;
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.msaccess.component.AbstractMsAccessConnectionProvider#getDBLocation()
	 */
	@Override
	protected String getDBLocation() {
		InputStream is = null;
		FileOutputStream fos = null;
		
		try {
			String[] parts = this._resource.split("\\.", -1);
			
			File temp = File.createTempFile(parts[0], "." + ( parts.length == 1 ? "tmp" : parts[1] ));
			
			temp.deleteOnExit();
			
			LOG.info("Copying embedded resource {} to {}...", this._resource, temp.getAbsolutePath());

			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(this._resource);
			
			if(is == null) throw new FileNotFoundException("Resource " + this._resource + " is not available in current classpath");
			
			fos = new FileOutputStream(temp);
			
			byte[] buffer = new byte[65536];
			
			int copied = 0;
			int len = -1;
			
			while((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				
				copied += len;
				
				LOG.info("{} bytes copied so far...", copied);

				fos.flush();
			}
			
			LOG.info("{} total bytes copied...", copied);
			
			fos.flush();
			
			return temp.getAbsolutePath();
		} catch(FileNotFoundException FNFe) {
			throw new RuntimeException(FNFe);
		} catch (Throwable t) {
			throw new RuntimeException("Unable to copy embedded VME access file " + DEFAULT_MS_ACCESS_DB_RESOURCE + " to temp file: " + t.getClass().getSimpleName() + " [ " + t.getMessage() + " ]", t);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (Throwable tt) {
					LOG.warn("Unable to close input stream", tt);
				}
			}
			
			if(fos != null) {
				try {
					fos.close();
				} catch (Throwable tt) {
					LOG.warn("Unable to close file output stream", tt);
				}
			}
		}
	}
}
