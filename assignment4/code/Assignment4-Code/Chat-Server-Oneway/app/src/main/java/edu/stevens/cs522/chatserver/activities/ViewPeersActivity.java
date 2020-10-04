package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Peer;


public class ViewPeersActivity extends Activity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    /*
     * TODO See ChatServer for example of what to do, query peers database instead of messages database.
     */

    private SimpleCursorAdapter peerAdapter;

    // I added this
    static final private int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peers);

        // TODO initialize peerAdapter with empty cursor (null)
        getLoaderManager().initLoader(LOADER_ID, null, this);

        String[] from = {PeerContract.NAME};
        int[] to = {android.R.id.text1};
        peerAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);

        ListView peerList = (ListView)findViewById(R.id.peer_list);
        peerList.setOnItemClickListener(this);
        peerList.setAdapter(peerAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
         * Clicking on a peer brings up details
         */
        Cursor cursor = peerAdapter.getCursor();
        if (cursor.moveToPosition(position)) {
            Intent intent = new Intent(this, ViewPeerActivity.class);
            Peer peer = new Peer(cursor);
            Log.i("DEBUG", "*** BEFORE in ViewPeersActivity peer address is: " + peer.address);
            intent.putExtra(ViewPeerActivity.PEER_KEY, peer);
            Log.i("DEBUG", "Opening View Peer Activity of this peer: " + peer.name);
            Log.i("DEBUG", "*** In ViewPeersActivity peer address is: " + peer.address);
            startActivity(intent);
        } else {
            throw new IllegalStateException("Unable to move to position in cursor: "+ position);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // TODO use a CursorLoader to initiate a query on the database
        return new CursorLoader(this,
                PeerContract.CONTENT_URI,
                null,
                null,
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
