package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.os.Bundle;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.databases.ChatDbAdapter;

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

    }

}
