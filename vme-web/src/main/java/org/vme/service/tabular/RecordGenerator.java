package org.vme.service.tabular;

import java.lang.reflect.Method;
import java.util.List;

public interface RecordGenerator<F, S> {

	public void doFirstLevel(F p, List<Object> nextRecord);

	public void doSecondLevel(S p, List<Object> nextRecord);

	public Method getSecondLevelMethod();

	public String[] getHeaders();

}