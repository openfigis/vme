package org.fao.fi.vme.msaccess.formatter;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MeetingDateParserTest {

	String testDates[] = { "17-21 Sep 2012", "1-14 Jun 2012", "19-23 Sep 2005", "1-15 Jun 2006", "18-22 Sep 2006",
			"7-21 Jun 2007", "24-28 Sep 2007", "5-19 Jun 2008", "22-26 Sep 2008", "22-30 Oct 2008", "4-18 Jun 2009",
			"3-16 Jun 2010", "20-24 Sep 2010", "3-16 Jun 2011", "19-23 Sep 2011", "19-23 Sep 2005", "18-22 Sep 2006",
			"24-28 Sep 2007", "30 Apr - 7 May 2008", "22-26 Sep 2008", "21-25 Sep 2009", "20-24 Sep 2010",
			"19-23 Sep 2011", "3-6 Oct 2005", "2-5 Oct 2006", "8-11 Oct 2007", "6-9 Oct 2008", "5-8 Oct 2009",
			"11-15 Oct 2010", "10-14 Oct 2011", "28-30 Sep 2005", "27-29 Sep 2006", "4-5 Oct 2007", "2-3 Oct 2008",
			"30 Sep - 2 Oct 2009", "4-9 Oct 2010", "28 Sep - 7 Oct 2011", "30-31 Dec 2099" };

	@Test
	public void testMeetingDateParser() {
		for (String date : testDates) {
			MeetingDateParser p = new MeetingDateParser(date);
			assertNotNull(p.getStart());
			assertNotNull(p.getEnd());

		}

	}

}
