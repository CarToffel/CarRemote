package at.cartoffel.remote;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class ControlActivity extends Activity {
	Thread orders;
	
	static boolean pressed = false;
	
	static TextView textview_frontDistance;
	static TextView textview_backDistance;
	static TextView textview_leftDistance;
	static TextView textview_rightDistance;
	static TextView textview_speed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		//Start thread for receiving messanges from Arduino Microcontroller
		Thread receive = new Thread(new WifiReceiver(this));
		receive.start();
		
		setButtonListeners();
		
		textview_frontDistance = (TextView) findViewById(R.id.textview_frontDistance);
		textview_frontDistance.setTextColor(Color.WHITE);
		textview_backDistance = (TextView) findViewById(R.id.textview_backDistance);
		textview_backDistance.setTextColor(Color.WHITE);
		textview_leftDistance = (TextView) findViewById(R.id.textview_leftDistance);
		textview_leftDistance.setTextColor(Color.WHITE);
		textview_rightDistance = (TextView) findViewById(R.id.textview_rightDistance);
		textview_rightDistance.setTextColor(Color.WHITE);
		textview_speed = (TextView) findViewById(R.id.textview_speed);
		textview_speed.setTextColor(Color.WHITE);

		//Update the TextViews for distances every 500ms 
		Timer updateTimer = new Timer("sensorUpdate");
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				updateUI();
			}
		}, 0, 500);

    }
	
	public void send(String order){
		orders = new Thread(new WifiSender(order, this));
		orders.start();
	}
	
	/**
	 * Updates the UI so that the proper distance is shown
	 */
	public void updateUI()
	{	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ControlActivity.textview_frontDistance.invalidate();
				ControlActivity.textview_backDistance.invalidate();
				ControlActivity.textview_leftDistance.invalidate();
				ControlActivity.textview_rightDistance.invalidate();
			}
		});
	}
	
	/**
	 * Sets the TouchListeners which handle events of the Buttons
	 */
	public void setButtonListeners() {
		// Forward Button
		Button btnForward = (Button) findViewById(R.id.btnForward);
		btnForward.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("forward button", "forward pressed");
					ControlActivity.pressed = true;
					send("f");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					ControlActivity.pressed = false;
				}
				return true;
			}
		});

		// Backward Button
		Button btnBackward = (Button) findViewById(R.id.btnBackward);
		btnBackward.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("backward button", "backward pressed");
					ControlActivity.pressed = true;
					send("b");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					ControlActivity.pressed = false;
				}
				return true;
			}
		});
		
		// Left Button
		Button btnLeft = (Button) findViewById(R.id.btnLeft);
		btnLeft.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("left button", "left pressed");
					ControlActivity.pressed=true;
					send("l");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					ControlActivity.pressed=false;
				}
				return true;
			}
		});
		
		// Right Button
		Button btnRight = (Button) findViewById(R.id.btnRight);
		btnRight.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("right button", "right pressed");
					ControlActivity.pressed=true;
					send("r");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					ControlActivity.pressed=false;
				}
				return true;
			}
		});
		
		//Stop Button
		Button btnStop = (Button) findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("stop button", "stop pressed");
				send("s");
			}
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}
}
