package org.fao.fi.vme.domain.change3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CorrectionTest {

	@Test
	public void testCorrection() {
		Correction c = new Correction(a);
		assertEquals(
				"VME notified under CM 22-06. Area closed to bottom fishing. VME recorded in CCAMLR VME Registry. http://www.ccamlr.org/en/data/ccamlr-vme-registry",
				c.getCorrectedString());
		assertTrue(c.isCorrected());
		assertFalse(c.isInConsistent());
	}

	String a = "VME notified under CM 22-06. Area closed to bottom fishing. VME recorded in CCAMLR VME Registry. http://www.ccamlr.org/en/data/ccamlr-vme-registry<!--[if gte mso 9]><xml>"
			+ " <w:WordDocument>"
			+ "  <w:View>Normal</w:View>"
			+ "  <w:Zoom>0</w:Zoom>"
			+ "  <w:TrackMoves/>"
			+ "  <w:TrackFormatting/>"
			+ "  <w:PunctuationKerning/>"
			+ "  <w:ValidateAgainstSchemas/>"
			+ "  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>"
			+ "  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>"
			+ "  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>"
			+ "  <w:DoNotPromoteQF/>"
			+ "  <w:LidThemeOther>EN-GB</w:LidThemeOther>"
			+ "  <w:LidThemeAsian>JA</w:LidThemeAsian>"
			+ "  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>"
			+ "  <w:Compatibility>"
			+ "   <w:BreakWrappedTables/>"
			+ "   <w:SnapToGridInCell/>"
			+ "   <w:WrapTextWithPunct/>"
			+ "   <w:UseAsianBreakRules/>"
			+ "   <w:DontGrowAutofit/>"
			+ "   <w:SplitPgBreakAndParaMark/>"
			+ "   <w:DontVertAlignCellWithSp/>"
			+ "   <w:DontBreakConstrainedForcedTables/>"
			+ "   <w:DontVertAlignInTxbx/>"
			+ "   <w:Word11KerningPairs/>"
			+ "   <w:CachedColBalance/>"
			+ "   <w:UseFELayout/>"
			+ "  </w:Compatibility>"
			+ "  <m:mathPr>"
			+ "   <m:mathFont m:vaCambria Math"
			+ "   <m:brkBin m:vabefore"
			+ "   <m:brkBinSub m:va&#45;-"
			+ "   <m:smallFrac m:vaoff"
			+ "   <m:dispDef/>"
			+ "   <m:lMargin m:va0"
			+ "   <m:rMargin m:va0"
			+ "   <m:defJc m:vacenterGroup"
			+ "   <m:wrapIndent m:va1440"
			+ "   <m:intLim m:vasubSup"
			+ "   <m:naryLim m:vaundOvr"
			+ "  </m:mathPr></w:WordDocument>"
			+ "</xml><![endif]--><!--[if gte mso 9]><xml>"
			+ "  DefSemiHidden= DefQFormat= DefPriority9"
			+ "  <w:LsdException Locked= Priority3 QFormat= NameTOC Heading"
			+ " </w:LatentStyles>"
			+ "</xml><![endif]--><!--[if gte mso 10]>"
			+ "<style>"
			+ " /* Style Definitions */"
			+ " table.MsoNormalTable"
			+ "	{mso-style-name:"
			+ "	mso-tstyle-rowband-size:0;"
			+ "	mso-tstyle-colband-size:0;"
			+ "	mso-style-noshow:yes;"
			+ "	mso-style-priority:99;"
			+ "	mso-style-qformat:yes;"
			+ "	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;"
			+ "	mso-para-margin-top:0cm;"
			+ "	mso-para-margin-right:0cm;"
			+ "	mso-para-margin-bottom:10.0pt;"
			+ "	mso-para-margin-left:0cm;"
			+ "	line-height:115%;"
			+ "	mso-pagination:widow-orphan;"
			+ "	font-size:11.0pt;"
			+ "	mso-ascii-font-family:Calibri;"
			+ "	mso-ascii-theme-font:minor-latin;"
			+ "	mso-hansi-font-family:Calibri;" + "	mso-hansi-theme-font:minor-latin;}" + "</style>" + "<![endif]-->";

}
