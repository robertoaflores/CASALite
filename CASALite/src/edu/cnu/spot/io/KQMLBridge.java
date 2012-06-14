package edu.cnu.spot.io;

import java.util.Enumeration;
import java.util.Vector;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;

import edu.cnu.casaLite.io.AbstractMessageStream;
import edu.cnu.casaLite.message.KQMLParser;
import edu.cnu.casaLite.message.IMessage;

public class KQMLBridge {
	private final Vector  adapters;
	private       boolean init;

	private class KQMLAdapter extends AbstractMessageStream {
		public KQMLAdapter() {
			super( KQMLParser.getInstance() );
			init = false;
		}
		public void open() {
			adapters.addElement( this );
			init = true;
		}
		public boolean isInitialized() {
			return init;
		}
		public String getScheme() {
			return "methodCall";
		}
		public String getHost() {
			long   address = RadioFactory.getRadioPolicyManager().getIEEEAddress();
			String host    = IEEEAddress.toDottedHex( address );
			return host;
		}
		public int getPort() {
			return 0;
		}
		public void close() {
			if (init) {
				adapters.removeElement( this );
			}
		}
		public void sendMessage(IMessage aMessage) {
			Enumeration i = adapters.elements();
			while (i.hasMoreElements()) {
				KQMLAdapter adapter = (KQMLAdapter) i.nextElement();
				if (adapter != this) {
					String string = aMessage.toString();
					adapter.queueMessage( string );
				}
			}
		}
	}
	
	public KQMLBridge() {
		adapters = new Vector();
	}
	public AbstractMessageStream getStream() {
		return new KQMLAdapter();
	}
}
