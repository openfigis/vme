package org.fao.fi.vme.backup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Vme;

/**
 * Experimenting with a backup for VME.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeBackup {

	private String fileName;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void run(List<Vme> vmeList) {

		try {
			// Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(vmeList);
			out.close();
		} catch (IOException e) {
			throw new VmeException(e);
		}
	}

}
