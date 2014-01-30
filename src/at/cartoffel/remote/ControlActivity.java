package at.cartoffel.remote;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		
		Thread receive = new Thread(new WiFiCommunicator_Server(this));
		receive.start();
		
		final TextView debugView = (TextView) findViewById(R.id.debugView);
		
		Button button = (Button) findViewById(R.id.btnForward);
        button.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	
               if(event.getAction() == MotionEvent.ACTION_DOWN){
                	debugView.setText("forward pressed");
                	send("f");
                	debugView.setText(debugView.getText()+" 1 ");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                	debugView.setText("");
                	orders.stop();
                }
                
                return true;
            }
        });

    }
	
	public void send(String order){
		orders = new Thread(new WiFiCommunicator_Client(order, this));
		orders.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btnLeft:
//			Thread turnLeft = new Thread(new WiFiCommunicator_Client("l", this));
//            turnLeft.start();
//            break;
//            
//		case R.id.btnRight:
//			Thread turnRight = new Thread(new WiFiCommunicator_Client("r", this));
//			turnRight.start();
//            break;
//            
//		case R.id.btnForward:
//			Thread forward = new Thread(new WiFiCommunicator_Client("f", this));
//			forward.start();
//            break;
//            
//		case R.id.btnBackward:
//			Thread backward = new Thread(new WiFiCommunicator_Client("b", this));
//			backward.start();
//            break;
//            
//		case R.id.btnStop:
//			Thread stop = new Thread(new WiFiCommunicator_Client("s", this));
//			stop.start();
//			break;
//
//		}
//	}
	
	

}
