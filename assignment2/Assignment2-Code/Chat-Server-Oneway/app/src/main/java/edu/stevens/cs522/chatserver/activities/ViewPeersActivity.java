package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Peer;


public class ViewPeersActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String PEERS_KEY = "peers";

    private ArrayList<Peer> peers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peers);

        peers = getIntent().getParcelableArrayListExtra(PEERS_KEY);

        // TODO display the list of peers, set this activity as onClick listener
        ListView peersListView = (ListView)findViewById(R.id.peer_list);
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < peers.size(); i++) {
            names.add(peers.get(i).name);
        }
        ArrayAdapter<String> peersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        peersListView.setAdapter(peersAdapter);
        peersListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
         * Clicking on a peer brings up details
         */
        Peer peer = peers.get(position);
        Intent intent = new Intent(this, ViewPeerActivity.class);
        intent.putExtra(ViewPeerActivity.PEER_KEY, peer);
        startActivity(intent);
    }
}
