package edu.stevens.cs522.chatserver.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;

/**
 * Created by dduggan.
 */

public class ChatDbAdapter {

    private static final String DATABASE_NAME = "messages.db";

    private static final String MESSAGE_TABLE = "messages";

    private static final String PEER_TABLE = "peers";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;


    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE =
                "CREATE TABLE " + PEER_TABLE + " (" +
                        PeerContract._ID + "INTEGER PRIMARY KEY," +
                        PeerContract.NAME + "TEXT," +
                        PeerContract.TIMESTAMP + "TEXT," +
                        PeerContract.ADDRESS + "TEXT)";
                 // TODO

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(db);
        }
    }


    public ChatDbAdapter(Context _context) {
        dbHelper = new DatabaseHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        // TODO
        db = dbHelper.getWritableDatabase();
    }

    public Cursor fetchAllMessages() {
        // TODO
        String[] messageProjection;
        messageProjection = new String[]{MessageContract.MESSAGE_TEXT, MessageContract.TIMESTAMP, MessageContract.SENDER};
        return db.query(MESSAGE_TABLE,
                messageProjection,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor fetchAllPeers() {
        // TODO
        String[] peerProjection;
        peerProjection = new String[]{PeerContract.NAME};
        return db.query(PEER_TABLE,
                peerProjection,
                null,
                null,
                null,
                null,
                null);
    }

    public Peer fetchPeer(long peerId) {
        // TODO
        Peer peer = new Peer();
        // Return cursor to row and populate instance of Peer
        return peer;
    }

    public Cursor fetchMessagesFromPeer(Peer peer) {
        // TODO
        String[] messageFromPeerProjection = {};
        String messageFromPeerSelection = "";
//        messageFromPeerSelection = TITLE + "=" + title;
        String[] messageFromPeerSelectionArgs = {};
//        messageFromPeerSelectionArgs = { title };
        return db.query(DATABASE_NAME,
                messageFromPeerProjection,
                messageFromPeerSelection,
                messageFromPeerSelectionArgs,
                null,
                null,
                null);
    }

    public long persist(Message message) throws SQLException {
        // TODO
        throw new IllegalStateException("Unimplemented: persist message");
    }

    /**
     * Add a peer record if it does not already exist; update information if it is already defined.
     */
    public long persist(Peer peer) throws SQLException {
        // TODO
        throw new IllegalStateException("Unimplemented: persist peer");
    }

    public void close() {
        // TODO
        db.close();
    }
}