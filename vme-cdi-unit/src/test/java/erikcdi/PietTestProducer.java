package erikcdi;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

@Alternative
public class PietTestProducer {

	@Produces
	public Piet piet = new PietTest();

}
