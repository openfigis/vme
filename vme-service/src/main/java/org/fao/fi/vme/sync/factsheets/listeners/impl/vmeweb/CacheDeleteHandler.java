package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;

/**
 * 
 * Handler in order to pick up the asynchronously launched request to clear the
 * cache.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public class CacheDeleteHandler extends AsyncCompletionHandler<Response> {
	static final private Logger LOG = LoggerFactory.getLogger(CacheDeleteHandler.class);

	public static final String MESSAGE = "VME_CACHE_DELETED_SUCCESS";

	/**
	 * WARN - Unexpected error on close java.lang.IllegalStateException: Must
	 * not be called from a I/O-Thread to prevent deadlocks! at
	 * org.jboss.netty.channel
	 * .socket.nio.AbstractNioSelector.shutdown(AbstractNioSelector.java:396) at
	 * org.jboss.netty.channel.socket.nio.NioWorker.shutdown(NioWorker.java:36)
	 * at org.jboss.netty.channel.socket.nio.AbstractNioWorkerPool.shutdown(
	 * 
	 */

	@Override
	public Response onCompleted(Response response) throws Exception {
		if (!response.getResponseBody().equals(MESSAGE)) {
			LOG.error("This message was expected but not received: " + MESSAGE);
		}
		LOG.debug("CacheDeleteHandler received this message after a cache delete request was launched to vme-web search : "
				+ MESSAGE);

		return response;
	}
}
