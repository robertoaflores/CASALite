package edu.cnu.spot.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.io.StreamConnection;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;

import edu.cnu.casaLite.io.AbstractMessageStream;
import edu.cnu.casaLite.message.KQMLMessage;
import edu.cnu.casaLite.message.KQMLParser;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.message.IMessage;

public class KQMLRadioStream extends AbstractMessageStream {
	private final int              broadcastingPort;
	private       StreamConnection stream;
	private       DataInputStream  input;
	private       DataOutputStream output;
	private       boolean          broadcasting;
	private       boolean          init;
	private       int              port;
	private       boolean          closed;
	
	public KQMLRadioStream(int aBroadcastingPort) {
		super( KQMLParser.getInstance() );
		init             = false;
		closed           = true;
		broadcastingPort = aBroadcastingPort;
	}
	
	public void open() {
		final int aConnectingPort = 52;
		closed       = false;
		broadcasting = true;
		new Thread( new Runnable() {
			public void run() {
				try {
//					System.out.println("[receiver] opening @ "+ broadcastingPort +"..." );
					DatagramConnection listen = (DatagramConnection) Connector.open( "radiogram://:" + broadcastingPort );
					Datagram           dataIn = listen.newDatagram( listen.getMaximumLength() );

//					System.out.println("[receiver] listening @ "+ broadcastingPort +"..." );
					listen.receive ( dataIn );
					String string  = dataIn.readUTF();
//					System.out.println("[receiver] received: " + string );

					MapMessage message      = (MapMessage) KQMLMessage.fromString( string );
					String     performative = message.get( "performative" );
					String     content      = message.get( "content" );
					
					if (performative.equals( "invite" )) {
						message.set( "performative", "agree" );

						Datagram dataOut = listen.newDatagram( listen.getMaximumLength() );
						dataOut.setAddress( dataIn );
						dataOut.writeUTF  ( message.toString() );

//						System.out.println( "[receiver] sending: "+ message.toString() );
						listen.send ( dataOut );
					}
//					System.out.println("[receiver] closing..." );
					listen.close();
					
					String address =  dataIn.getAddress();
		            long   from    = IEEEAddress.toLong( address );
		            
		            port = Integer.parseInt( content );

//					System.out.println("[stream] opening "+ address + ":" + port +"..." );
					stream = (StreamConnection) Connector.open( "radiostream://" + from + ":" + port );
					input  = stream.openDataInputStream();
					output = stream.openDataOutputStream();

					new Thread( new Runnable() {
						public void run() {
							try {
								while (true) {
//									System.out.println("[stream] listening..." );
									String string = input.readUTF();
//									System.out.println("[stream] received: " + string );
									queueMessage( string );
								}
							}
							catch (Exception e) {
								if (!closed) {
									e.printStackTrace();
								}
							}
						}
					}).start();
					
					broadcasting = false;
					init         = true;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		try {
//			System.out.println( "[broadcast] opening @ "+ broadcastingPort +"..." );
			DatagramConnection broadcast = (DatagramConnection) Connector.open( "radiogram://broadcast:" + broadcastingPort );
			Datagram           dataOut   = broadcast.newDatagram( broadcast.getMaximumLength() );

			MapMessage         message   = new KQMLMessage();
			message.set( "performative", "invite" );
			message.set( "content",      "" + aConnectingPort );
			dataOut.writeUTF( message.toString() );

			while (broadcasting) {
//				System.out.println( "[broadcast @ "+ broadcastingPort +"] sending: "+ message.toString() );
				broadcast.send ( dataOut );
				Utils    .sleep( 2000 );
			}
//			System.out.println( "[broadcast] closing." );
			broadcast.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isInitialized() {
		return init;
	}

	public String getScheme() {
		return "radiostream";
	}
	public String getHost() {
		long   address = RadioFactory.getRadioPolicyManager().getIEEEAddress();
		String host    = IEEEAddress.toDottedHex( address );
		return host;
	}
	public int getPort() {
		return port;
	}

	public void close() {
		try {
			closed = true;
			if (init) {
//				System.out.println("[stream] closing..." );
				input .close();
				output.close();
				stream.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(IMessage aMessage) {
		try {
			String message = aMessage.toString();
//			System.out.println( "[stream] sending: "+ message );
			output.writeUTF( message );
			output.flush();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
