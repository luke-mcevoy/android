package edu.stevens.cs522.chatserver.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.UnknownHostException;

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

    private static final String _ID = "_id";

    private static final String PEER_FK = "peer_fk";

    private static final String MESSAGES_PEER_INDEX = "MessagesPeerIndex";

    private static final String PEER_NAME_INDEX = "PeerNameIndex";

    private static final String FK_ON = "PRAGMA foreign_key = ON";

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;


    public static class DatabaseHelper extends SQLiteOpenHelper {


        private static final String PEER_CREATE =
                "CREATE TABLE " + PEER_TABLE + " (" +
                        _ID + " integer primary key," +
                        PeerContract.NAME + " text not null," +
                        PeerContract.TIMESTAMP + " long not null," +
                        PeerContract.ADDRESS + " text not null);";

        private static final String MESSAGE_CREATE =
                "CREATE TABLE " + MESSAGE_TABLE + " (" +
                        _ID + " integer primary key," +
                        MessageContract.MESSAGE_TEXT + " text not null," +
                        MessageContract.TIMESTAMP + " long not null," +
                        MessageContract.SENDER + " text not null," +
                        PEER_FK + " integer not null," +
                        "FOREIGN KEY (" + PEER_FK + ") REFERENCES " +
                        PEER_TABLE + "(" + _ID + ") ON DELETE CASCADE );";

        private static final String CREATE_MESSAGES_PEER_INDEX =
                "CREATE INDEX " + MESSAGES_PEER_INDEX + " ON " + MESSAGE_TABLE + "(" + PEER_FK + ");";

        private static final String CREATE_PEER_NAME_INDEX =
                "CREATE INDEX " + PEER_NAME_INDEX + " ON " + PEER_TABLE + "(" + PeerContract.NAME + ");";

        /*
        private static final String PEER_CREATE =
                "CREATE TABLE IF NOT EXISTS " + PEER_TABLE + " (" +
                PeerContract._ID + " long primary key autoincrement," +
                PeerContract.NAME + " text not null," +
                PeerContract.TIMESTAMP + " text not null," +
                PeerContract.ADDRESS + " text not null);";

        private static final String MESSAGE_CREATE =
                "CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE + " (" +
                        MessageContract._ID + " long primary key autoincrement," +
                        MessageContract.MESSAGE_TEXT + " text not null," +
                        MessageContract.TIMESTAMP + " text not null," +
                        MessageContract.SENDER + " text not null," +
                        MessageContract.SENDER_ID + " long not null);";
         */

//        private static final String DATABASE_CREATE =
//                "CREATE TABLE IF NOT EXISTS " + PEER_TABLE + " (" +
//                PeerContract._ID + " long primary key," +
//                PeerContract.NAME + " text not null," +
//                PeerContract.TIMESTAMP + " text not null," +
//                PeerContract.ADDRESS + " text not null);" +
//
//                "CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE + " (" +
//                MessageContract._ID + " long primary key," +
//                MessageContract.MESSAGE_TEXT + " text not null," +
//                MessageContract.TIMESTAMP + " text not null," +
//                MessageContract.SENDER + " text not null," +
//                MessageContract.SENDER_ID + " text not null);";
        // TODO

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO
            db.execSQL(MESSAGE_CREATE);
            db.execSQL(CREATE_MESSAGES_PEER_INDEX);
            db.execSQL(PEER_CREATE);
            db.execSQL(CREATE_PEER_NAME_INDEX);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO
            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " + newVersion);
            db.execSQL(FK_ON);
            db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + PEER_TABLE);
            onCreate(db);
        }
    }


    public ChatDbAdapter(Context _context) {
        dbHelper = new DatabaseHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        // TODO
        db = dbHelper.getWritableDatabase();
        db.execSQL(FK_ON);
    }

    public Cursor fetchAllMessages() {
        // TODO
        String[] messageProjection;
        messageProjection = new String[]{_ID, MessageContract.MESSAGE_TEXT, MessageContract.TIMESTAMP, MessageContract.SENDER};
        db.execSQL(FK_ON);
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
        peerProjection = new String[]{_ID, PeerContract.NAME, PeerContract.TIMESTAMP, PeerContract.ADDRESS};
        db.execSQL(FK_ON);
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
        String[] projection = {_ID, PeerContract.NAME, PeerContract.TIMESTAMP, PeerContract.ADDRESS};
        String selection = _ID + " = ?";
        String[] selectionArgs = {Long.toString(peerId)};
        db.execSQL(FK_ON);
        Cursor result = db.query(PEER_TABLE, projection, selection, selectionArgs, null, null, null);
        result.moveToFirst();
        try {
            return new Peer(result);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

//        String[] peerProjection = new String[]{_ID, PeerContract.NAME, PeerContract.TIMESTAMP, PeerContract.ADDRESS};
//        String selection = _ID + " = ?";
//        String[] selectionArgs = {Long.toString(peerId)};
//        db.execSQL(FK_ON);
//
//        Cursor result = db.query(PEER_TABLE,
//                peerProjection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null
//                );
//        result.moveToFirst();
//        return new Peer(result);
        return null;
    }

    public Cursor fetchMessagesFromPeer(Peer peer) {
        // TODO
        String[] messageProjection = new String[]{_ID, MessageContract.MESSAGE_TEXT, MessageContract.TIMESTAMP, MessageContract.SENDER};
        String selection = MessageContract._ID + " = ?";
        String[] selectionArgs = {peer.name};
        return db.query(MESSAGE_TABLE,
                messageProjection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    public long persist(Message message) throws SQLException {
        // TODO
        ContentValues contentValues = new ContentValues();
        message.writeToProvider(contentValues);
        contentValues.put(PEER_FK, message.senderId);
        return db.insert(MESSAGE_TABLE, null, contentValues);

        /*
        String[] projection = {_ID, MessageContract.MESSAGE_TEXT};
        String selection = MessageContract.MESSAGE_TEXT + " = ?";
        String[] selectionArgs = {message.messageText};

        Cursor cursor = db.query(MESSAGE_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.getCount() == 0) {
            return db.insert(MESSAGE_TABLE, null, contentValues);
        } else {
            cursor.moveToFirst();
            selection = MessageContract._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(MessageContract.getMessageID(cursor))};
            db.update(MESSAGE_TABLE, contentValues, selection, selectionArgs);
            return MessageContract.getMessageID(cursor);
        }
         */
    }

    /**
     * Add a peer record if it does not already exist; update information if it is already defined.
     */
    public long persist(Peer peer) throws SQLException {
        // TODO
        ContentValues contentValues = new ContentValues();
        peer.writeToProvider(contentValues);

        String[] projection = {_ID, PeerContract.NAME};
        String selection = PeerContract.NAME + " = ?";
        String[] selectionArgs = {peer.name};
        db.execSQL(FK_ON);
        Cursor cursor = db.query(PEER_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.getCount() == 0) {
            return db.insert(PEER_TABLE, null, contentValues);
        } else {
            cursor.moveToFirst();
            selection = _ID + " = ?";
            selectionArgs = new String[]{String.valueOf(PeerContract.getPeerID(cursor))};
            db.update(PEER_TABLE, contentValues, selection, selectionArgs);
            return PeerContract.getPeerID(cursor);
        }
    }

    public void close() {
        // TODO
        db.close();
    }
}