package erikcdi;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(CdiRunner.class)
public class CdiTest {

	@Inject
	Piet piet;

	@Test
	public void test() {
		assertNotNull(piet);
		assertTrue(piet instanceof PietProd);
		// assertTrue(piet instanceof PietTest);
	}

}
