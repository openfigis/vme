package org.vme.dao.transaction;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.config.vme.VmeDB;

/**
 * 
 * @author Roberto Empiri
 *
 */

@Transactional 
@Interceptor
public class TransactionInterceptor {

	@Inject
	@VmeDB
	private EntityManager em;
	
	private Logger log = LoggerFactory.getLogger(TransactionInterceptor.class);
	
//	private UserTransaction tx;

//	@Inject
//	@VmeDB
//	private Instance<Transaction> txInstance;

	@AroundInvoke
	public Object manageTransaction(final InvocationContext context) throws Exception{
		
		if(context.getMethod().isAnnotationPresent(Transactional.class) || 
				context.getClass().isAnnotationPresent(Transactional.class)) {
			
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
//			Transaction tx = txInstance.get();

			log.trace("in transaction for {}", context.getMethod());
			long time = System.currentTimeMillis();

			Object result = context.proceed();

			log.trace("transaction for {} complete in {} ms.", context.getMethod(),System.currentTimeMillis()-time);

			tx.commit();
			
			return result;
		} else {
			return context.proceed();
		}

	}


}
