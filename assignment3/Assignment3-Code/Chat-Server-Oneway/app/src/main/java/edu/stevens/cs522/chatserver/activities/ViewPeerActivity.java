package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.databases.ChatDbAdapter;
import edu.stevens.cs522.chatserver.entities.Peer;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity {

    public static final String PEER_ID_KEY = "peer-id";

    private ChatDbAdapter chatDbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        long peerId = getIntent().getLongExtra(PEER_ID_KEY, -1);
        if (peerId < 0) {
            throw new IllegalArgumentException("Expected peer id as intent extra");
        }

        // TODO init the UI
        chatDbAdapter = new ChatDbAdapter(this);
        chatDbAdapter.open();

        Peer fetchedPeer = chatDbAdapter.fetchPeer(peerId);

        TextView username = (TextView)findViewById(R.id.view_user_name);
        TextView lastSeen = (TextView)findViewById(R.id.view_timestamp);
        TextView address = (TextView)findViewById(R.id.view_address);

        username.setText(fetchedPeer.name);
        Log.i("Test", "fetchedPeer ViewPeerActivity timestamp" + fetchedPeer.timestamp +
                " " + fetchedPeer.name + " " + fetchedPeer.address);
        lastSeen.setText(fetchedPeer.timestamp.toString());
        address.setText(fetchedPeer.address.getHostAddress());

        chatDbAdapter.close();
    }

}
