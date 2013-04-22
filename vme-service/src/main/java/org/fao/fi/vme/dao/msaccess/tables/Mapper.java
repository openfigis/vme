package org.fao.fi.vme.dao.msaccess.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper {

	Object generateObject(ResultSet rs) throws SQLException;

}
