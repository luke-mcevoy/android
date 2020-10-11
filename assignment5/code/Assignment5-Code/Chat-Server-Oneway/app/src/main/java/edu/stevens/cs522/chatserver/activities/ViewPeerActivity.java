package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.async.IQueryListener;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.managers.MessageManager;
import edu.stevens.cs522.chatserver.managers.PeerManager;
import edu.stevens.cs522.chatserver.managers.TypedCursor;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity implements IQueryListener<Message> {

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

        // TODO init the UI and initiate query of message database
        messageManager = new MessageManager(this);
        messageManager.getMessagesByPeerAsync(peer, this);

//        TextView username = (TextView)findViewById(R.id.view_user_name);
//        TextView lastSeen = (TextView)findViewById(R.id.view_timestamp);
//        TextView address = (TextView)findViewById(R.id.view_address);
//
//        username.setText(peer.name);
//        lastSeen.setText(peer.timestamp.toString());
//        address.setText(peer.address.toString());
    }

    @Override
    public void handleResults(TypedCursor<Message> results) {
        // TODO
        this.messageAdapter.swapCursor(results.getCursor());
    }

    @Override
    public void closeResults() {
        // TODO
        this.messageAdapter.swapCursor(null);
    }
}
