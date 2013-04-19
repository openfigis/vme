package org.fao.fi.vme.dao.access;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper {

	Object generateObject(ResultSet rs, String[] strings) throws SQLException;

}
