package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.junit.Test;

public class ProfileCorrectionTest {

	ProfileCorrection p = new ProfileCorrection();
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testCorrect() {
		List<ObjectCollection> ocList = prepareobjectCollectionList("1", "2", "3", "4");
		p.correct(ocList);
		assertEquals(2, ((Vme) ocList.get(0).getObjectList().get(0)).getProfileList().size());

		ocList = prepareobjectCollectionList("1", "1", "2", "2");
		p.correct(ocList);
		assertEquals(1, ((Vme) ocList.get(0).getObjectList().get(0)).getProfileList().size());

		ocList = prepareobjectCollectionList("1", null, "2", "2");
		p.correct(ocList);
		assertEquals(1, ((Vme) ocList.get(0).getObjectList().get(0)).getProfileList().size());

		ocList = prepareobjectCollectionList(null, null, null, null);
		p.correct(ocList);
		assertEquals(1, ((Vme) ocList.get(0).getObjectList().get(0)).getProfileList().size());

	}

	private List<ObjectCollection> prepareobjectCollectionList(String bio1, String bio2, String fys1, String fys2) {
		Profile p1 = new Profile();
		p1.setYear(2000);

		p1.setDescriptionBiological(checkNull(bio1));
		p1.setDescriptionPhisical(checkNull(fys1));

		Profile p2 = new Profile();
		p1.setYear(2001);
		p2.setDescriptionBiological(checkNull(bio2));
		p2.setDescriptionPhisical(checkNull(fys2));
		List<Profile> pList = new ArrayList<Profile>();
		pList.add(p1);
		pList.add(p2);

		Vme vme = new Vme();
		vme.setProfileList(pList);

		ObjectCollection oc = new ObjectCollection();
		oc.setClazz(Vme.class);
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(vme);
		oc.setObjectList(objectList);

		List<ObjectCollection> ocList = new ArrayList<ObjectCollection>();
		ocList.add(oc);
		return ocList;

	}

	private MultiLingualString checkNull(String text) {
		MultiLingualString m = null;
		if (text != null && !text.equals("")) {
			m = u.english(text);
		}
		return m;
	}
}
