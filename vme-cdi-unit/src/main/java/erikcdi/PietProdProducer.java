package erikcdi;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

@Alternative
public class PietProdProducer {

	@Produces
	public Piet piet = new PietProd();

}
