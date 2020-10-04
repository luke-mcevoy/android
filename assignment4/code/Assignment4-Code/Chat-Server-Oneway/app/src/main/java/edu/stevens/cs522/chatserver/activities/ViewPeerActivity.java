package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.providers.ChatProvider;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String PEER_KEY = "peer";


    // I added this
    private SimpleCursorAdapter peerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        Log.i("DEBUG", "*** Peer address is " + peer.address);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO init the UI
        TextView username = (TextView)findViewById(R.id.view_user_name);
        TextView lastSeen = (TextView)findViewById(R.id.view_timestamp);
        TextView address = (TextView)findViewById(R.id.view_address);

        // How to populate textviews?
        Log.i("DEBUG", "*** Name is " + peer.name);
        username.setText(peer.name);
        Log.i("DEBUG", "*** Timestamp name is " + peer.timestamp.toString());
        lastSeen.setText(peer.timestamp.toString());
        String tmp = "/127.0.0.1";
        Log.i("DEBUG", "*** Peer address is " + peer.address);
        address.setText(tmp);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // TODO use a CursorLoader to initiate a query on the database
        // Filter messages with the sender id
        return new CursorLoader(this,
                PeerContract.CONTENT_URI,
                null,
                PeerContract.NAME + "=?",
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        // TODO populate the UI with the result of querying the provider
        this.peerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO reset the UI when the cursor is empty
        this.peerAdapter.swapCursor(null);
    }

}
