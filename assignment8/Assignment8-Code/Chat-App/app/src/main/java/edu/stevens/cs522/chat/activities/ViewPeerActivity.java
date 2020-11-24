package edu.stevens.cs522.chat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.entities.Peer;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity {

    public static final String PEER_KEY = "peer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI
        TextView username = findViewById(R.id.view_user_name);
        username.setText(peer.name);

        TextView timestamp = findViewById(R.id.view_timestamp);
        timestamp.setText(peer.timestamp.toString());

        TextView latitude = findViewById(R.id.view_latitude);
        latitude.setText(peer.latitude.toString());

        TextView longitude = findViewById(R.id.view_longitude);
        longitude.setText(peer.longitude.toString());

    }

}
