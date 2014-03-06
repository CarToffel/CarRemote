package at.cartoffel.remote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Class for recieving data from an Arduino microcontroller via UDP
 * 
 * @author Samuel Hammer
 * 
 */
public class WifiReceiver implements Runnable {

	Context context;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public WifiReceiver(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		try {
			int port = 8888;

			// Create a socket to listen on the port.
			DatagramSocket socket = new DatagramSocket(port);

			// Create a buffer to read datagrams into. If a
			// packet is larger than this buffer, the
			// excess will simply be discarded!
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Now loop forever, waiting to receive packets and printing them.
			while (true) {
				// Wait to receive a datagram
				socket.receive(packet);

				// Convert the contents to a string, and display them
				String msg = new String(buffer, 0, packet.getLength());

				String output = packet.getAddress().getHostName() + ": " + msg;
				
				Log.d("message", output);
				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
