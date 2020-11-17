/*********************************************************************

    Chat server: accept chat messages from clients.
    
    Sender chatName and GPS coordinates are encoded
    in the messages, and stripped off upon receipt.

    Copyright (c) 2017 Stevens Institute of Technology

**********************************************************************/
package edu.stevens.cs522.chat.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.entities.ChatMessage;
import edu.stevens.cs522.chat.managers.TypedCursor;
import edu.stevens.cs522.chat.rest.ChatHelper;
import edu.stevens.cs522.chat.settings.Settings;
import edu.stevens.cs522.chat.services.ResultReceiverWrapper;

public class RegisterActivity extends Activity implements OnClickListener, ResultReceiverWrapper.IReceive {

	final static public String TAG = RegisterActivity.class.getCanonicalName();
		
    /*
     * Widgets for server Uri, chat name, register button.
     */
    private EditText serverUriText;

    private EditText userNameText;

    private Button registerButton;

    /*
     * Helper for Web service
     */
    private ChatHelper helper;

    /*
     * For receiving ack when registered.
     */
    private ResultReceiverWrapper registerResultReceiver;
	
	/*
	 * Called when the activity is first created. 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        /**
         * We only get to register once.
         */
		if (Settings.isRegistered(this)) {
			finish();
            return;
		}

        setContentView(R.layout.register);

        // TODO instantiate helper for service
        helper = new ChatHelper(this);


        // TODO initialize registerResultReceiver
        registerResultReceiver = new ResultReceiverWrapper(new Handler());


        serverUriText = (EditText) findViewById(R.id.chat_server_text);
        serverUriText.setText(getString(R.string.server_uri_default));

        userNameText = (EditText) findViewById(R.id.chat_name_text);
        userNameText.setText(getString(R.string.user_name_default));

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

    }

	public void onResume() {
        super.onResume();
        registerResultReceiver.setReceiver(this);
    }

    public void onPause() {
        super.onPause();
        registerResultReceiver.setReceiver(null);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    /*
     * Callback for the REGISTER button.
     */
    public void onClick(View v) {
        if (!Settings.isRegistered(this) && helper != null) {

            String serverUri = serverUriText.getText().toString();

            String userName = userNameText.getText().toString();

            // TODO use helper to register
            helper.register(Uri.parse(serverUri), userName, registerResultReceiver);   // TODO need URI not String for 1st param


            // End todo

        } else {

            Toast.makeText(this, "Already Registered!", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    /**
     * Callback to confirm registration success or failure.
     *
     * If success, the settings are updated in the request processor.
     */
    public void onReceiveResult(int resultCode, Bundle data) {
        switch (resultCode) {
            case RESULT_OK:
                // Show a success toast message
                Toast.makeText(this, "Registered!", Toast.LENGTH_LONG).show();
                break;
            default:
                // TODO show a failure toast message
                Toast.makeText(this, "Failure!", Toast.LENGTH_LONG).show();
                break;
        }
    }

}