package at.cartoffel.remote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.content.Context;
import android.widget.Toast;

public class WiFiCommunicator_Client implements Runnable {
	private Context context;
	private DatagramSocket socket;
	private InetAddress ip;
	private int port;
	private DatagramPacket packet;
	private String data;
	private boolean pressed;

	// Constructor
	public WiFiCommunicator_Client(String data, Context context) {
		try {
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName("10.0.0.99");
			setPort(8888);
			setData(data);
			setContext(context);
			setPressed(true);
		} catch (SocketException e) {
			Toast.makeText(context, "Socket Error! " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		} catch (UnknownHostException e) {
			Toast.makeText(context, "Unknown Host! " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void run() {
		while (ControlActivity.rightpressed==true || ControlActivity.leftpressed==true) {
			try {
				byte[] m = data.getBytes();
				packet = new DatagramPacket(m, data.length(), this.ip,
						this.port);
				socket.send(packet);
				
				Thread.sleep(100);
			} catch (IOException e) {
				Toast.makeText(context, "Sending failed!", Toast.LENGTH_LONG)
						.show();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		data = "";
	}

	// Getters and Setters
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public DatagramPacket getPacket() {
		return packet;
	}

	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public boolean getPressed() {
		return this.pressed;

	}

}