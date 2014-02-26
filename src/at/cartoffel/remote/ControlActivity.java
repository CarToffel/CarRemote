package at.cartoffel.remote;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
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
	
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		Thread receive = new Thread(new WiFiCommunicator_Server(this));
		receive.start();
		
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
		btnForward.getBackground().setColorFilter(new LightingColorFilter(Color.rgb(128,132,107),0));
		btnForward.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("forward button","forward pressed");
					send("f");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					
				}
				return true;
			}
		});

		// Backward Button
		Button btnBackward = (Button) findViewById(R.id.btnBackward);
		btnBackward.getBackground().setColorFilter(new LightingColorFilter(Color.rgb(128,132,107),0));
		btnBackward.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("backward button","backward pressed");
					send("b");
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					
				}
				return true;
			}
		});
		
		// Left Button
		Button btnLeft = (Button) findViewById(R.id.btnLeft);
		btnLeft.getBackground().setColorFilter(new LightingColorFilter(Color.RED,0));
		btnLeft.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("left button","left pressed");
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
		btnRight.getBackground().setColorFilter(new LightingColorFilter(Color.RED,0));
		btnRight.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("right button","right pressed");
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
		btnStop.getBackground().setColorFilter(new LightingColorFilter(Color.rgb(128,132,107),0));
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
