package at.cartoffel.remote;

import android.os.Bundle;
import android.app.Activity;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		//Start thread for receiving messanges from Arduino Microcontroller
		Thread receive = new Thread(new WifiReceiver(this));
		receive.start();
		
		setButtonListeners();

    }
	
	public void send(String order){
		orders = new Thread(new WifiSender(order, this));
		orders.start();
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
