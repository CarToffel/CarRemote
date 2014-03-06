package at.cartoffel.remote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ResourceBundle.Control;

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
	 * @param context
	 */
	public WifiReceiver(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		try {
			int port = 6666;

			// Create a socket to listen on the port.
			DatagramSocket dsocket = new DatagramSocket(port);

			// Create a buffer to read datagrams into. If a
			// packet is larger than this buffer, the
			// excess will simply be discarded!
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Now loop forever, waiting to receive packets and printing them.
			while (true) {
				// Wait to receive a datagram
				dsocket.receive(packet);

				// Convert the contents to a string, and display them
				String msg = new String(buffer, 0, packet.getLength());
				
				//Split the String containing the distances into one String for each side
				String[] distances = msg.split(",");
				final String frontDistance = distances[0];
				final String backDistance = distances[1];
				final String leftDistance = distances[2];
				final String rightDistance = distances[3];
				final String speed = distances[4];
				
				//Print out the distances for each side
				Log.d("front distance", frontDistance);
				ControlActivity.textview_frontDistance.post(new Runnable() {
					@Override
					public void run() {
						ControlActivity.textview_frontDistance.setText("Front Distance: " + frontDistance);
					}
				});
				
				Log.d("Back distance", backDistance);
				ControlActivity.textview_backDistance.post(new Runnable() {
					@Override
					public void run() {
						ControlActivity.textview_backDistance.setText("Back Distance:" + backDistance);
					}
				});
				
				Log.d("Left distance", leftDistance);
				ControlActivity.textview_leftDistance.post(new Runnable() {
					@Override
					public void run() {
						ControlActivity.textview_leftDistance.setText("Left Distance: " + leftDistance);
					}
				});
				
				Log.d("Right distance", rightDistance);
				ControlActivity.textview_rightDistance.post(new Runnable() {
					@Override
					public void run() {
						ControlActivity.textview_rightDistance.setText("Right Distance: " + rightDistance);
					}
				});
				
				Log.d("Speed", speed);
				ControlActivity.textview_speed.post(new Runnable() {
					@Override
					public void run() {
						ControlActivity.textview_speed.setText("Speed: " + speed);
					}
				});
				
				
				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
