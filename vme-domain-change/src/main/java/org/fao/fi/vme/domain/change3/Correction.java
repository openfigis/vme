package org.fao.fi.vme.domain.change3;

public class Correction {

	boolean corrected = false;
	boolean inconsistent;

	private static final String TABOE = "<!--";

	String correctedString;

	public Correction(String text) {
		System.out.println(text);
		int start = text.indexOf(TABOE);
		int end = text.lastIndexOf("-->") + 3;

		inconsistent = start > 0 ^ end > 0;

		while (text.contains(TABOE)) {
			text = applyCorrection(text);
			corrected = true;
		}

	}

	String applyCorrection(String text) {
		int start = text.indexOf("<!--");
		int end = text.lastIndexOf("-->") + 3;

		inconsistent = start > 0 ^ end > 0;

		if (start > 0 && end > 0) {
			// correct
			String corruption = text.substring(start, end);
			this.correctedString = text.replace(corruption, "");
			System.out.println(text);
			System.out.println(corruption);
			System.out.println(correctedString);
		}

		return correctedString;
	}

	public boolean isCorrected() {
		return corrected;
	}

	public boolean isInConsistent() {
		return inconsistent;
	}

	public String getCorrectedString() {
		return correctedString;
	}

}
