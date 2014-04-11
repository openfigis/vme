package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import java.io.IOException;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * VmeWebSearchCacheClient in order to clear the cache, when needed.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Singleton
public class VmeWebSearchCacheClient {

	// private String server = "http://localhost:8081/";
	// private String resource = "vme-web/webservice/cache-delete/";
	private String server;
	private String resource;

	public static final String MESSAGE = "VME_CACHE_DELETED_SUCCESS";

	static final protected Logger LOG = LoggerFactory.getLogger(VmeWebSearchCacheClient.class);
	static final private String errorMessage = "Did not manage to clear the cach in vme-web search properly";

	/**
	 * launches a asynchronous request in order to have the cache cleared.
	 */
	public void process(@Observes VmeModelChange vmeModelChange) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(this.server + this.resource);
		final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		try {
			String get = server + resource;
			asyncHttpClient.prepareGet(get).execute(new AsyncCompletionHandler<Response>() {
				@Override
				public Response onCompleted(Response response) throws Exception {
					if (!response.getResponseBody().equals(MESSAGE)) {
						LOG.error("This message was expected but not received: " + MESSAGE);
					}
					LOG.debug("CacheDeleteHandler received this message after a cache delete request was launched to vme-web search : "
							+ MESSAGE);
					asyncHttpClient.close();
					return response;
				}

				@Override
				public void onThrowable(Throwable t) {
					LOG.error("Something wrong happened. ");
				}
			});

			// asyncHttpClient.close();
			LOG.debug("VmeWebSearchCacheClient clear request launched!");
		} catch (IOException e) {
			LOG.error(errorMessage, e);
		}
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
