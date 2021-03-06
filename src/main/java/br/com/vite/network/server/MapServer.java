package br.com.vite.network.server;

import br.com.midnight.model.Peer;
import br.com.midnight.server.TCPServer;
import br.com.vite.network.client.MapClientProtocol;

public class MapServer extends TCPServer {

	public static final int PORT = 13131;
	
	MapServerProtocol protocol;
	
	public MapServer() {
		super(PORT);
		
		this.name = "Veete Server";

		handshaker = new MapHandshaker();
		
		protocol = new MapServerProtocol(MapClientProtocol.PREFIX_VEETE);

		addProtocol(MapClientProtocol.PREFIX_VEETE, protocol);
	}
	
	@Override
	public void start() {
		super.start();
	}
	
	@Override
	public void joinPeer(Peer peer) {
		System.out.println("HandShakePeer "+peer.getSessionID()+" connected.");
		protocol.addPeer(peer);			
	}
	
}
