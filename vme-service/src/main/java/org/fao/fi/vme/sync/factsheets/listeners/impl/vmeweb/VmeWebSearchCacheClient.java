package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import java.io.IOException;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

/**
 * VmeWebSearchCacheClient in order to clear the cache, when needed.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

@Dependent
public class VmeWebSearchCacheClient {

	public static final String RESOURCE = "/webservice/cache-delete/";

	public static final String MESSAGE = "VME_CACHE_DELETED_SUCCESS";

	/**
	 * An instance of this class will cache every HTTP 1.1 connections and close them when the
	 * AsyncHttpClientConfig.getIdleConnectionTimeoutInMs() expires. This object can hold many persistent connections to
	 * different host.
	 * 
	 * Therefore there is also the @SuppressWarnings("resource"), because the client is closed by this setting after
	 * REQUEST_TIMEOUT_IN_MS
	 * 
	 */
	public static final int REQUEST_TIMEOUT_IN_MS = 2000;

	protected static final Logger LOG = LoggerFactory.getLogger(VmeWebSearchCacheClient.class);
	private static final String ERRORMESSAGE = "Did not manage to clear the cach in vme-web search properly";

	@Inject
	VmeWebServer vmeWebServer;

	/**
	 * launches a asynchronous request in order to have the cache cleared.
	 */
	public void process(@Observes VmeModelChange vmeModelChange) {
		@SuppressWarnings("resource")
		final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(new AsyncHttpClientConfig.Builder()
				.setRequestTimeoutInMs(REQUEST_TIMEOUT_IN_MS).build());

		try {
			String get = vmeWebServer.getServer() + RESOURCE;
			LOG.info("About to launch async request to clear the cache on " + get);

			asyncHttpClient.prepareGet(get).execute(new AsyncCompletionHandler<Response>() {
				@Override
				public Response onCompleted(Response response) throws Exception {
					if (!response.getResponseBody().equals(MESSAGE)) {
						LOG.error("This message was expected but not received: " + MESSAGE);
					}
					LOG.info("CacheDeleteHandler received this message after a cache delete request was launched to vme-web search : "
							+ response.getResponseBody());
					return response;
				}

				@Override
				public void onThrowable(Throwable t) {
					LOG.error("Something wrong happened. ", t);
				}
			});

			LOG.debug("VmeWebSearchCacheClient clear request launched!");
		} catch (IOException e) {
			LOG.error(ERRORMESSAGE, e);
		}
	}
}
