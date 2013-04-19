package org.fao.fi.vme.dao.access;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.fao.fi.vme.domain.VmeObservation;

public class VmeObservationMapper implements Mapper {

	@Override
	public Object generateObject(ResultSet rs, String[] columns) throws SQLException {
		VmeObservation o = new VmeObservation();
		for (int i = 0; i < columns.length; i++) {
			switch (i) {
			case 1:
				o.setAreaType(rs.getString(columns[i]));
			case 2:
				o.setAreaType(rs.getString(columns[i]));
			}

		}
		return o;
	}
}
