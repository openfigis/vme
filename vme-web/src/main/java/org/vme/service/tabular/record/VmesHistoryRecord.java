package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class VmesHistoryRecord extends AbstractRecord implements RecordGenerator<Rfmo, VMEsHistory, Empty> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
	}

	@Override
	public void doSecondLevel(VMEsHistory p, List<Object> nextRecord) {
		nextRecord.add(p.getYear());
		nextRecord.add(u.getEnglish(p.getHistory()));
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Rfmo.class, "getHasVmesHistory");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "VMEs" };
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Method getThirdLevelMethod() {
		// TODO Auto-generated method stub
		return null;
	}

}
