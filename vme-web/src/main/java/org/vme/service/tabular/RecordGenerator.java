package org.vme.service.tabular;

import java.lang.reflect.Method;
import java.util.List;

public interface RecordGenerator<F, S, T> {
	
	public void doFirstLevel(F p, List<Object> nextRecord);

	public void doSecondLevel(S p, List<Object> nextRecord);

	public void doThirdLevel(T p, List<Object> nextRecord);
	
	public Method getSecondLevelMethod();
	
	public Method getThirdLevelMethod();

	public String[] getHeaders();

}