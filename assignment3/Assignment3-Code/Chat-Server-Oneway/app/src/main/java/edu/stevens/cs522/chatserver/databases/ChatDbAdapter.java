package edu.stevens.cs522.chatserver.databases;

import android.content.ContentValues;
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
                        PeerContract._ID + "long primary key," +
                        PeerContract.NAME + "text not null," +
                        PeerContract.TIMESTAMP + "text not null," +
                        PeerContract.ADDRESS + "text not null);"  +

                "CREATE TABLE " + MESSAGE_TABLE + " (" +
                        MessageContract._ID + "long primary key," +
                        MessageContract.MESSAGE_TEXT + "text not null," +
                        MessageContract.TIMESTAMP + "text not null," +
                        MessageContract.SENDER + "text not null," +
                        MessageContract.SENDER_ID + "text not null);";

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
        db.execSQL("PRAGMA foreign_key = ON");
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
        String[] peerProjection = new String[]{PeerContract._ID, PeerContract.NAME, PeerContract.TIMESTAMP, PeerContract.ADDRESS};
        String selection = PeerContract._ID + " = " + peerId;
        return new Peer(db.query(PEER_TABLE,
                peerProjection,
                selection,
                null,
                null,
                null,
                null
                ));

        // Return cursor to row and populate instance of Peer
    }

    public Cursor fetchMessagesFromPeer(Peer peer) {
        // TODO
        String[] messageProjection = new String[]{MessageContract._ID, MessageContract.MESSAGE_TEXT, MessageContract.TIMESTAMP, MessageContract.SENDER, MessageContract.SENDER_ID};
        String selection = MessageContract._ID + " = " + peer.id;
        return db.query(MESSAGE_TABLE,
                messageProjection,
                selection,
                null,
                null,
                null,
                null
        );

    }

    public long persist(Message message) throws SQLException {
        // TODO
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageContract.MESSAGE_TEXT, message.messageText);
        contentValues.put(MessageContract.SENDER, message.sender);
        contentValues.put(MessageContract.SENDER_ID, message.senderId);
        contentValues.put(MessageContract.TIMESTAMP, message.timestamp.toString());
        db.insertOrThrow(MESSAGE_TABLE, null, contentValues);
        throw new IllegalStateException("Unimplemented: persist message");
    }

    /**
     * Add a peer record if it does not already exist; update information if it is already defined.
     */
    public long persist(Peer peer) throws SQLException {
        // TODO
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PeerContract.NAME, peer.name);
        contentValues.put(PeerContract.ADDRESS, peer.address.toString());
        contentValues.put(PeerContract.TIMESTAMP, peer.timestamp.toString());
        db.insertOrThrow(PEER_TABLE, null, contentValues);
        throw new IllegalStateException("Unimplemented: persist peer");
    }

    public void close() {
        // TODO
        db.close();
    }
}