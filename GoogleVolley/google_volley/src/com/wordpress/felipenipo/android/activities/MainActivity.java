package com.wordpress.felipenipo.android.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wordpress.felipenipo.R;
import com.wordpress.felipenipo.network.NetworkQueue;
import com.wordpress.felipenipo.network.NetworkRequestCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	
	// Constants
	public static final String TAG = "MainActivity";
	
	// Members
	private NetworkQueue mNetworkQueue;
	private EditText mIdEditText;
	private Button mDoItButton;
	private Button mGetAllButton;
	private Button mGetItButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mNetworkQueue = NetworkQueue.getInstance();
        
        mIdEditText = (EditText) findViewById(R.id.id_et);
        
        mGetAllButton = (Button) findViewById(R.id.get_all_btn);
        mGetAllButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doGetArray(
		        	"",
		        	TAG,
		        	new NetworkRequestCallback<JSONArray>() {
		        		@Override
						public void onRequestResponse(JSONArray jsonObject) {
							Log.d(TAG, "GET ALL onRequestResponse!" + "\n" + jsonObject.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "GET ALL onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
        
        mDoItButton = (Button) findViewById(R.id.do_it_btn);
        mDoItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doPost(
		        	"",
		        	null,
		        	TAG,
		        	new NetworkRequestCallback<JSONObject>() {
		        		@Override
						public void onRequestResponse(JSONObject jsonObject) {
							Log.d(TAG, "POST onRequestResponse!" + "\n" + jsonObject.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "POST onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
        
        mGetItButton = (Button) findViewById(R.id.get_it_btn);
        mGetItButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mNetworkQueue.doGet(
		        	"" + mIdEditText.getText().toString(),
		        	TAG,
		        	new NetworkRequestCallback<JSONObject>() {
		        		@Override
						public void onRequestResponse(JSONObject jsonObject) {
							Log.d(TAG, "GET onRequestResponse!" + "\n" + jsonObject.toString());
						}
						@Override
						public void onRequestError(Exception error) {
							Log.d(TAG, "GET onRequestError!" + "\n" + error.getMessage());
						}
					});
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	mNetworkQueue.cancelRequestsByTag(TAG);
    }
}
