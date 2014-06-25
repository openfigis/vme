package org.vme.service.tabular;

import java.lang.reflect.Method;
import java.util.List;

public interface RecordGenerator<F, S, T> {
	
	void doFirstLevel(F p, List<Object> nextRecord);

	void doSecondLevel(S p, List<Object> nextRecord);

	void doThirdLevel(T p, List<Object> nextRecord);
	
	Method getSecondLevelMethod();
	
	Method getThirdLevelMethod();

	String[] getHeaders();

}