package org.fao.fi.vme.domain.test;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.MediaReference;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class MediaReferenceMock {

	static MultiLingualStringUtil u = new MultiLingualStringUtil();

	public static final String TITLE = "Turkey has begun to close some of its border crossings with Syria after about 130,000 Kurdish refugees entered the country over the weekend. On Sunday Turkish security forces clashed with Kurds protesting in solidarity with the refugees. Some protesters were reportedly trying to go to Syria to fight Islamic State (IS). Most refugees are from Kobane, a town threatened by the advancing militants. IS has taken over large swathes of Iraq and Syria in recent months. Before the latest influx, there were already more than one million Syrian refugees in Turkey. They have fled since the start of the uprising against Syrian President Bashar al-Assad three years ago. Some of the new arrivals are being sheltered in overcrowded schools, as Turkey struggles to cope with the influx. 'Huge numbers' On Friday Turkey opened a 30km (19-mile) section of the border to Syrians fleeing the town of Kobane, also known as Ayn al-Arab. But on Monday only two out of nine border posts in the area remained open, the UN refugee agency UNHCR said.";
	public static final String DESCRIPTION = "JAKARTA, Indonesia — Theo Hesegem was carrying a foreigner on his motorbike when a pair of police intelligence officers pulled up behind him and ordered him to stop. It was midday in Wamena, a small city in the highlands of West Papua, Indonesia’s easternmost region and the only one foreign journalists need a special permit to visit. “Mr. Theo, where are you coming from?” the officers asked. Hesegem, a human-rights activist, explained that he had been asked to give the woman a ride by the head of the local indigenous people’s council, Areki Wanimbo. Hesegem had been visiting Wanimbo’s office when the woman, Valentine Bourrat, arrived with another French citizen, Thomas Dandois, Hesegem said. What the three of them had discussed, Hesegem didn’t know, but he had been happy to drive Bourrat back to her hotel. “We’re on heightened alert in Wamena,” the officers said, referring to a recent spate of violence in the area. The previous week, two policemen had been killed in a shoot-out with the West Papua National Liberation Army, or TPN-PB, a diffuse association of guerrilla groups that for decades have waged a low-level insurgency against Indonesian rule. “Just bring her back to the hotel,” the officers told Hesegem. “We might need to call her in for questioning.”";
	public static final String CREDITS = "The slow death of the no-logo movement Abercrombie & Fitch's decision to go unbranded completes the commodification of the DIY era September 22, 2014 6:00AM ET by Oliver Bateman   @MoustacheClubUS In late August, Abercrombie & Fitch CEO Mike Jeffries announced that the company would remove its once-ubiquitous logo from most of its clothing, thereby “tak[ing] the North American logo business to practically nothing.” The move comes after nearly a decade of consumer backlash, much of it generated by Jeffries’ exclusionary rhetoric about overweight customers and the company’s racially discriminatory business practices, on the heels of 10 straight quarters of declining sales.  That Abercrombie’s brand is toxic goes without saying. There are slews of essays criticizing the company, including one I published earlier this year about managing a store and being forced to remove an unattractive staff member from rotation. But Jeffries’ decision to join the ranks of minimalist, fast-fashion retailers such as H&M and Urban Outfitters deserves closer scrutiny. That one of the last of the voraciously logo-driven clothing companies is choosing to de-emphasize its logo underscores a profound change in the relationship between commodity-desiring consumers and the corporations that create and manipulate those desires — and signifies the death knell of the avowedly anti-corporate no-logo movement of the 1990s.  That demise has its roots, unsurprisingly, in the birth of modern consumer culture in the early 1920s. During that decade, according to (PDF) historian Stuart Ewen, a combination of nationwide communication networks, vastly improved systems of mass production and state-of-the-art advertising techniques transformed consumption from the humdrum acquisition of necessary goods and services into a transcendent experience; it was self-actualization through shopping.  By the 1950s, that transformation was complete. Corporate conglomerates provided the goods and services desired by family purchasing units, able to acquire newly “necessary” items such as dishwashers and microwaves at department stores and other retailers. With each passing generation, just-invented luxuries increasingly came to be regarded as necessities. Cellular phones, personal computers and athletic footwear all started as expensive novelties and quickly became line items in the average American family’s budget.  In turn, idealistic representatives ranging from Ken Kesey and the Merry Pranksters in the 1960s to the post-hardcore band Fugazi in the late 1980s reacted to the perceived sterility around them by attempting to forge alternative do-it-yourself (DIY) lifestyles: Kesey by traveling the country to spread the gospel of personal freedom through drug experimentation and Fugazi by maintaining exclusive control of the sale and distribution of its music. Such efforts met with some amount of success, as cultural critic Thomas Frank explains in “The Conquest of Cool: Business Culture, Counterculture and the Rise of Hip Consumerism.” But Madison Avenue was a quick copycat, and all too soon the bohemian, self-oriented style such radicals pioneered went from “adversarial to hegemonic,” with hipness mutating “from [the] native language of the alienated to that of advertising.”";

	public static MediaReference create() {
		MediaReference r = new MediaReference();
		r.setTitle(u.english(TITLE));
		r.setDescription(u.english(DESCRIPTION));
		r.setCredits(u.english(CREDITS));
		r.setType(MediaTypeMock.ID);
		return r;
	}

	public static List<MediaReference> createList() {
		List<MediaReference> list = new ArrayList<MediaReference>();
		list.add(create());
		return list;
	}

}