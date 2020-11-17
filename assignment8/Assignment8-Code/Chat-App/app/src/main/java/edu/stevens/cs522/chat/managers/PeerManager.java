package edu.stevens.cs522.chat.managers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import edu.stevens.cs522.chat.async.AsyncContentResolver;
import edu.stevens.cs522.chat.async.IContinue;
import edu.stevens.cs522.chat.async.IEntityCreator;
import edu.stevens.cs522.chat.async.IQueryListener;
import edu.stevens.cs522.chat.async.IQueryListener;
import edu.stevens.cs522.chat.async.ISimpleQueryListener;
import edu.stevens.cs522.chat.async.SimpleQueryBuilder;
import edu.stevens.cs522.chat.contracts.PeerContract;
import edu.stevens.cs522.chat.entities.Peer;


/**
 * Created by dduggan.
 */

public class PeerManager extends Manager<Peer> {

    private static final int LOADER_ID = 2;

    private static final IEntityCreator<Peer> creator = new IEntityCreator<Peer>() {
        @Override
        public Peer create(Cursor cursor) {
            return new Peer(cursor);
        }
    };

    public PeerManager(Context context) {
        super(context, creator, LOADER_ID);
    }

    public void getAllPeersAsync(IQueryListener<Peer> listener) {
        // TODO use QueryBuilder to complete this
        String[] projection = new String[]{PeerContract._ID, PeerContract.NAME, PeerContract.TIMESTAMP, PeerContract.LATITUDE, PeerContract.LONGITUDE};
        executeQuery(PeerContract.CONTENT_URI, projection, null, null, PeerContract.NAME + " ASC", listener);
    }

    public void getPeerAsync(long id, final IContinue<Peer> callback) {
        // TODO need to check that peer is not null (not in database)
        executeSimpleQuery(PeerContract.CONTENT_URI(id), null, null, null, new ISimpleQueryListener<Peer>() {
            @Override
            public void handleResults(List<Peer> results) {
                if (!results.isEmpty()) {
                    callback.kontinue(results.get(0));
                } else {
                    callback.kontinue(null);
                }
            }
        });
    }

    public void persistAsync(final Peer peer, final IContinue<Long> callback) {
        // TODO need to ensure the peer is not already in the database
        final AsyncContentResolver cr = getAsyncResolver();
        final ContentValues out = new ContentValues();
        peer.writeToProvider(out);
        cr.insertAsync(PeerContract.CONTENT_URI, out,
                new IContinue<Uri>() {
                    @Override
                    public void kontinue(Uri uri) {
                        if (uri == null) {
                            String select = PeerContract.NAME + " = ?";
                            String[] selectArgs = new String[]{peer.name};
                            cr.updateAsync(PeerContract.CONTENT_URI, select, selectArgs);
                            callback.kontinue((long) -1);
                        } else {
                            callback.kontinue(PeerContract.getId(uri));
                        }
                    }
                });
    }

    public long persist(Peer peer) {
        // TODO synchronous version that executes on background thread (in service)
        final ContentResolver cr = getSyncResolver();
        final ContentValues contentValues = new ContentValues();
        peer.writeToProvider(contentValues);
        if (peer.id < 0) {
            Uri uri = cr.insert(PeerContract.CONTENT_URI, contentValues);
            Long id = Long.parseLong(uri.getLastPathSegment());
            peer.id = id;
        } else {
            String where = PeerContract.ID + "=?";
            String[] projection = new String[]{peer.id + ""};
            cr.update(PeerContract.CONTENT_URI,
                    contentValues,
                    where,
                    projection);

        }
        return peer.id;
    }

}
