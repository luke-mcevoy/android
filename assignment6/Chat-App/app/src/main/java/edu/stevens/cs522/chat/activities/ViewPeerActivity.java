package edu.stevens.cs522.chat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import edu.stevens.cs522.chat.R;
import edu.stevens.cs522.chat.entities.Peer;
import edu.stevens.cs522.chat.managers.MessageManager;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity {

    public static final String PEER_KEY = "peer";

    private SimpleCursorAdapter messageAdapter;

    private MessageManager messageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI
//        messageManager = new MessageManager(this);
//        messageManager.getAllMessagesAsync(this);

        TextView username = (TextView)findViewById(R.id.view_user_name);
        TextView lastSeen = (TextView)findViewById(R.id.view_timestamp);
        TextView address = (TextView)findViewById(R.id.view_address);

        username.setText(peer.name);
        lastSeen.setText(peer.timestamp.toString());
        String add = "/127.0.0.1";
        address.setText(add);
    }

}
