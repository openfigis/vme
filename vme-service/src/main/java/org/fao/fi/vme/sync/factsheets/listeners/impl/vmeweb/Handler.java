package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

public class Handler extends AsyncCompletionHandler<Response> {
	AsyncHttpClient c;

	Handler(AsyncHttpClient c) {
		this.c = c;
	}

	@Override
	public Response onCompleted(Response response) throws Exception {
		c.close();
		return null;
	}

}
