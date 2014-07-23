package org.vme.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider;
import com.sun.jersey.core.provider.jaxb.AbstractJAXBElementProvider;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

//@Provider
//@Produces(MediaType.APPLICATION_XML)
public class Erik extends AbstractJAXBElementProvider {

	// Delay construction of factory
	private final Injectable<SAXParserFactory> spf;

	public Erik(Injectable<SAXParserFactory> spf, Providers ps) {
		super(ps);

		this.spf = spf;
	}

	public Erik(Injectable<SAXParserFactory> spf, Providers ps, MediaType mt) {
		super(ps, mt);

		this.spf = spf;
	}

	@Produces("application/xml")
	@Consumes("application/xml")
	public static final class App extends XMLJAXBElementProvider {
		public App(@Context Injectable<SAXParserFactory> spf, @Context Providers ps) {
			super(spf, ps, MediaType.APPLICATION_XML_TYPE);
		}
	}

	@Produces("text/xml")
	@Consumes("text/xml")
	public static final class Text extends XMLJAXBElementProvider {
		public Text(@Context Injectable<SAXParserFactory> spf, @Context Providers ps) {
			super(spf, ps, MediaType.TEXT_XML_TYPE);
		}
	}

	@Produces("*/*")
	@Consumes("*/*")
	public static final class General extends XMLJAXBElementProvider {
		public General(@Context Injectable<SAXParserFactory> spf, @Context Providers ps) {
			super(spf, ps);
		}

		@Override
		protected boolean isSupported(MediaType m) {
			return m.getSubtype().endsWith("+xml");
		}
	}

	protected final JAXBElement<?> readFrom(Class<?> type, MediaType mediaType, Unmarshaller u, InputStream entityStream)
			throws JAXBException {
		return u.unmarshal(getSAXSource(spf.getValue(), entityStream), type);
	}

	protected final void writeTo(JAXBElement<?> t, MediaType mediaType, Charset c, Marshaller m,
			OutputStream entityStream) throws JAXBException {

		// Intervention Erik van Ingen.
		m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
			@Override
			public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
				out.write(ch, start, length);
			}
		});

		m.marshal(t, entityStream);
	}

}