package at.cartoffel.remote;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ControlActivity extends Activity {
	Thread orders;
	static boolean pressed=false;
	TextView debugView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		Thread receive = new Thread(new WiFiCommunicator_Server(this));
		receive.start();
		
		debugView = (TextView) findViewById(R.id.debugView);
		
		setButtonListeners();

    }
	
	public void send(String order){
		orders = new Thread(new WiFiCommunicator_Client(order, this));
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
					debugView.setText("forward pressed");
					send("f");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					debugView.setText("");
					
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
					debugView.setText("backward pressed");
					send("b");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					debugView.setText("");
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
					debugView.setText("left pressed");
					ControlActivity.pressed=true;
					send("l");
					
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					debugView.setText("");
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
					debugView.setText("right pressed");
					ControlActivity.pressed=true;
					send("r");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					debugView.setText("");
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
