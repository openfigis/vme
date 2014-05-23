package org.vme.service.tabular;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.service.tabular.record.SpecificMeasureRecord;
import org.vme.service.tabular.record.VmeProfileRecord;

public class TabularGenerator {

	public List<List<Object>> generateVmeProfile(List<Vme> vmeList) {
		RecordGenerator<Vme, Profile, Empty> r = new VmeProfileRecord();
		return generateTabular(vmeList, r);

	}

	public List<List<Object>> generateSpecificMeasure(List<Vme> vmeList) {
		RecordGenerator<Vme, SpecificMeasure, Empty> r = new SpecificMeasureRecord();
		return null;
	};

	public List<List<Object>> generateGeneralMeasure() {
		return null;
	};

	public List<List<Object>> generateHistory() {
		return null;
	};

	public List<List<Object>> generateInfoSource() {
		return null;
	};

	public List<List<Object>> generateGeoRef() {
		return null;
	};

	private <F, S, T> List<List<Object>> generateTabular(List<F> firstList, RecordGenerator<F, S, T> r) {
		List<List<Object>> tabular = new ArrayList<List<Object>>();
		List<Object> firstRecord = new ArrayList<Object>(Arrays.asList(r.getHeaders()));
		tabular.add(firstRecord);
		for (F v : firstList) {
			try {
				@SuppressWarnings("unchecked")
				List<S> secondLevelList = (List<S>) r.getSecondLevelMethod().invoke(v);

				if (secondLevelList != null && !secondLevelList.isEmpty()) {
					for (S secondLevelObject : secondLevelList) {

						List<Object> nextRecord = new ArrayList<Object>();
						r.doFirstLevel(v, nextRecord);
						r.doSecondLevel(secondLevelObject, nextRecord);
						tabular.add(nextRecord);
					}
				} else {
					List<Object> nextRecord = new ArrayList<Object>();
					r.doFirstLevel(v, nextRecord);
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
