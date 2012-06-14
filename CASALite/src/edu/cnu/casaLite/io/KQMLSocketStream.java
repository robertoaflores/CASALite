package edu.cnu.casaLite.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.cnu.casaLite.message.IMessage;
import edu.cnu.casaLite.message.KQMLParser;
import edu.cnu.casaLite.message.MapMessage;

public class KQMLSocketStream extends AbstractMessageStream {
	private ServerSocket server;
	private boolean      init;
	private boolean      closed;
	private int          port;
	
	public KQMLSocketStream(int aPort) {
		super( KQMLParser.getInstance() );
		init   = false;
		port   = aPort;
	}
	public void open() {
		closed = false;
		new Thread( new Runnable() {
			public void run() {
				try {
					server = new ServerSocket( port );
					init   = true;
					while (true) {
						Socket          socket = server.accept();
						DataInputStream in     = new DataInputStream( socket.getInputStream() );
						String          string = in.readUTF();

						queueMessage( string );
					} 
				}
				catch (IOException e) {
					if (!closed) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	// it must always be overwritten in subclasses
	public boolean isInitialized() {
		return init;
	}
	public String getScheme() {
		return "socket";
	}
	public int getPort() {
		return server.getLocalPort();
	}
	public String getHost() {
		return server.getInetAddress().getHostAddress();
	}

	public void close() {
		try {
			server.close();
			closed = true;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(IMessage aMessage) {
		MapMessage    message = (MapMessage) aMessage;
		String        str     = message.get( "receiver" );
		URLDescriptor url     = new URLDescriptor( str );
		int           port    = url.getPort();
		String        host    = url.getHost();

		try {
			Socket           socket = new Socket( host, port );
			DataOutputStream out    = new DataOutputStream( socket.getOutputStream() );

			str = aMessage.toString();
			out.writeUTF( str );

			socket.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
