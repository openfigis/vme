package org.vme.service.tabular;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fao.fi.vme.VmeException;

public class TabularGenerator {

	public <F, S, T> List<List<Object>> generate(List<F> objectList, RecordGenerator<F, S, T> r){
		List<List<Object>> tabular = new ArrayList<List<Object>>();
		List<Object> firstRecord = new ArrayList<Object>(Arrays.asList(r.getHeaders()));
		tabular.add(firstRecord);
		if (!objectList.isEmpty()) {
			return generateTabular(tabular, objectList, r);
		} else {
			return tabular;		
		}
	}

	private <F, S, T> List<List<Object>> generateTabular(List<List<Object>> tabular, List<F> firstList, RecordGenerator<F, S, T> r) {
		for (F firstLevelObject : firstList) {
			try {
				@SuppressWarnings("unchecked")
				List<S> secondLevelList = (List<S>) r.getSecondLevelMethod().invoke(firstLevelObject);
				if (secondLevelList != null && !secondLevelList.isEmpty()) {
					for (S secondLevelObject : secondLevelList) {
						if (r.getThirdLevelMethod() != null) {
							@SuppressWarnings("unchecked")
							List<T> thirdLevelList = (List<T>) r.getThirdLevelMethod().invoke(secondLevelObject);
							if (thirdLevelList != null
									&& !thirdLevelList.isEmpty()) {
								for (T thirdLevelObject : thirdLevelList) {
									List<Object> nextRecord = new ArrayList<Object>();
									r.doFirstLevel(firstLevelObject,nextRecord);
									r.doSecondLevel(secondLevelObject,nextRecord);
									r.doThirdLevel(thirdLevelObject,nextRecord);
									tabular.add(nextRecord);
								}
							} else {
								List<Object> nextRecord = new ArrayList<Object>();
								r.doFirstLevel(firstLevelObject, nextRecord);
								r.doSecondLevel(secondLevelObject,nextRecord);
								tabular.add(nextRecord);
								fillUp(nextRecord, r.getHeaders().length);
							}
						} else {
							List<Object> nextRecord = new ArrayList<Object>();
							r.doFirstLevel(firstLevelObject, nextRecord);
							r.doSecondLevel(secondLevelObject, nextRecord);
							tabular.add(nextRecord);
							fillUp(nextRecord, r.getHeaders().length);
						}
					}
				} else {
					List<Object> nextRecord = new ArrayList<Object>();
					r.doFirstLevel(firstLevelObject, nextRecord);
					tabular.add(nextRecord);
					fillUp(nextRecord, r.getHeaders().length);
				}
			} catch (IllegalArgumentException e) {
				throw new VmeException(e);
			} catch (IllegalAccessException e) {
				throw new VmeException(e);
			} catch (InvocationTargetException e) {
				throw new VmeException(e);
			}
		}
		return tabular;
	}

	private void fillUp(List<Object> nextRecord, int length) {
		for (int i = nextRecord.size(); i < length; i++) {
			nextRecord.add(null);
		}
	}

}
