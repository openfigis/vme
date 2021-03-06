package org.fao.fi.vme.domain.change4;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * Some test data needs to be cleaned.
 * 
 * 
 * @author Erik van Ingen
 *
 */
public class CleanMexico {

	private static String MEXICO = "Het Mexicaanse telecomconcern América Móvil heeft een bod gedaan om KPN over te nemen. Het bedrijf van multimiljardair Carlos Slim heeft al een belang van bijna 30 procent in KPN en wil het bedrijf nu in zijn geheel inlijven. Wat moet de op een na rijkste man ter wereld met de Koninklijke PTT Nederland?  Chef van de economieredactie van de Volkskrant Xander van Uffelen denkt dat Carlos Slim enigszins verbolgen is over het feit dat KPN de Duitse tak E-Plus van de hand wil doen. KPN wilde E-Plus verkopen aan het Spaanse Telefónica, een van de grote concurrenten van Slim in Zuid-Amerika.   'Door een overname van KPN door América Móvil kan hij dat voorkomen. Want KPN staat met E-Plus redelijk sterk in Duitsland, maar als ze die tak kwijtraken, wordt het schier onmogelijk om er nog voet aan de grond te krijgen voor Slim. En het is de grootste telecommarkt van Europa, dus hij moest iets doen.'  De Duitse markt kent een aantal grote spelers die niet voor overname is aanmerking komen. De Britten zitten er met hun Vodafone, de Duitsers zelf met T-Mobile en het Spaanse Telefónica heeft er ook een aanzienlijk aandeel.   Rijkste man ter wereld Slim (73) verloor vorig jaar de titel 'rijkste man ter wereld' aan Microsoft-oprichter Bill Gates. Een van de redenen dat Slim zijn titel weer moest inleveren was de belegging in KPN in 2012. Hij moest al snel een 1 miljard euro bijstorten om KPN van de ondergang te redden.  Analisten dachten dat dat Slim's America Móvil daarna geen geld meer zou hebben om KPN in zijn geheel over te nemen. Het zou voor de overname te veel geld moeten lenen. Maar de analisten krijgen ongelijk en Móvil biedt 2,40 euro per aandeel, dat komt neer op zo'n 7,2 miljard euro in totaal.  América Móvil is ook al actief in Oostenrijk en probeert nu dus ook via Nederland en de buitenlandse takken van KPN zijn imperium in Europa te laten landen.Het Mexicaanse telecomconcern América Móvil heeft een bod gedaan om KPN over te nemen. Het bedrijf van multimiljardair Carlos Slim heeft al een belang van bijna 30 procent in KPN en wil het bedrijf nu in zijn geheel inlijven. Wat moet de op een na rijkste man ter wereld met de Koninklijke PTT Nederland?  Chef van de economieredactie van de Volkskrant Xander van Uffelen denkt dat Carlos Slim enigszins verbolgen is over het feit dat KPN de Duitse tak E-Plus van de hand wil doen. KPN wilde E-Plus verkopen aan het Spaanse Telefónica, een van de grote concurrenten van Slim in Zuid-Amerika.   'Door een overname van KPN door América Móvil kan hij dat voorkomen. Want KPN staat met E-Plus redelijk sterk in Duitsland, maar als ze die tak kwijtraken, wordt het schier onmogelijk om er nog voet aan de grond te krijgen voor Slim. En het is de grootste telecommarkt van Europa, dus hij moest iets doen.'  De Duitse markt kent een aantal grote spelers die niet voor overname is aanmerking komen. De Britten zitten er met hun Vodafone, de Duitsers zelf met T-Mobile en het Spaanse Telefónica heeft er ook een aanzienlijk aandeel.   Rijkste man ter wereld Slim (73) verloor vorig jaar de titel 'rijkste man ter wereld' aan Microsoft-oprichter Bill Gates. Een van de redenen dat Slim zijn titel weer moest inleveren was de belegging in KPN in 2012. Hij moest al snel een 1 miljard euro bijstorten om KPN van de ondergang te redden.  Analisten dachten dat dat Slim's America Móvil daarna geen geld meer zou hebben om KPN in zijn geheel over te nemen. Het zou voor de overname te veel geld moeten lenen. Maar de analisten krijgen ongelijk en Móvil biedt 2,40 euro per aandeel, dat komt neer op zo'n 7,2 miljard euro in totaal.  América Móvil is ook al actief in Oostenrijk en probeert nu dus ook via Nederland en de buitenlandse takken van KPN zijn imperium in Europa te laten landen. Het Mexicaanse telecomconcern América Móvil heeft een bod gedaan om KPN over te nemen. Het bedrijf van multimiljardair Carlos Slim heeft al een belang van bijna 30 procent in KPN en wil het bedrijf nu in zijn geheel inlijven. Wat moet de op een na rijkste man ter wereld met de Koninklijke PTT Nederland?  Chef van de economieredactie van de Volkskrant Xander van Uffelen denkt dat Carlos Slim enigszins verbolgen is over het feit dat KPN de Duitse tak E-Plus van de hand wil doen. KPN wilde E-Plus verkopen aan het Spaanse Telefónica, een van de grote concurrenten van Slim in Zuid-Amerika.   'Door een overname van KPN door América Móvil kan hij dat voorkomen. Want KPN staat met E-Plus redelijk sterk in Duitsland, maar als ze die tak kwijtraken, wordt het schier onmogelijk om er nog voet aan de grond te krijgen voor Slim. En het is de grootste telecommarkt van Europa, dus hij moest iets doen.'  De Duitse markt kent een aantal grote spelers die niet voor overname is aanmerking komen. De Britten zitten er met hun Vodafone, de Duitsers zelf met T-Mobile en het Spaanse Telefónica heeft er ook een aanzienlijk aandeel.   Rijkste man ter wereld Slim (73) verloor vorig jaar de titel 'rijkste man ter wereld' aan Microsoft-oprichter Bill Gates. Een van de redenen dat Slim zijn titel weer moest inleveren was de belegging in KPN in 2012. Hij moest al snel een 1 miljard euro bijstorten om KPN van de ondergang te redden.  Analisten dachten dat dat Slim's America Móvil daarna geen geld meer zou hebben om KPN in zijn geheel over te nemen. Het zou voor de overname te veel geld moeten lenen. Maar de analisten krijgen ongelijk en Móvil biedt 2,40 euro per aandeel, dat komt neer op zo'n 7,2 miljard euro in totaal.  América Móvil is ook al actief in Oostenrijk en probeert nu dus ook via Nederland en de buitenlandse takken van KPN zijn imperium in Europa te laten landen.  ";

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Inject
	VmeDao vmeDao;

	public void fix() {

		List<MultiLingualString> l = vmeDao.loadObjects(MultiLingualString.class);
		int fixes = 0;
		for (MultiLingualString m : l) {
			if (m.getStringMap() != null) {
				if (u.getEnglish(m) != null && u.getEnglish(m).equals(MEXICO)) {
					u.replaceEnglish(m, "");
					vmeDao.merge(m);
					fixes++;
				}
			}
		}
		String message = "Total amount of MultiLingualStrings " + l.size() + ". Number of fixes applied: " + fixes
				+ ".";
		System.out.println(message);
	}
}
