package edu.stevens.cs522.chatserver.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import edu.stevens.cs522.chatserver.contracts.BaseContract;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;

public class ChatProvider extends ContentProvider {

    public ChatProvider() {
    }

    private static final String AUTHORITY = BaseContract.AUTHORITY;

    private static final String MESSAGE_CONTENT_PATH = MessageContract.CONTENT_PATH;

    private static final String MESSAGE_CONTENT_PATH_ITEM = MessageContract.CONTENT_PATH_ITEM;

    private static final String PEER_CONTENT_PATH = PeerContract.CONTENT_PATH;

    private static final String PEER_CONTENT_PATH_ITEM = PeerContract.CONTENT_PATH_ITEM;


    private static final String DATABASE_NAME = "chat.db";

    private static final int DATABASE_VERSION = 1;

    private static final String MESSAGES_TABLE = "messages";

    private static final String PEERS_TABLE = "peers";

    // I added these
    private static final String MESSAGES_PEER_INDEX = "MessagesPeerIndex";
    private static final String PEER_NAME_INDEX = "PeerNameIndex";

    private static final String[] MESSAGE_PROJECTION = new String[]{
            MessageContract.ID,
            MessageContract.MESSAGE_TEXT,
            MessageContract.TIMESTAMP,
            MessageContract.SENDER,
            MessageContract.SENDER_ID
    };

    private static final String[] PEER_PROJECTION = new String[]{
            PeerContract.ID,
            PeerContract.NAME,
            PeerContract.TIMESTAMP,
            PeerContract.ADDRESS
    };

    // Create the constants used to differentiate between the different URI requests.
    private static final int MESSAGES_ALL_ROWS = 1;
    private static final int MESSAGES_SINGLE_ROW = 2;
    private static final int PEERS_ALL_ROWS = 3;
    private static final int PEERS_SINGLE_ROW = 4;

    public static class DbHelper extends SQLiteOpenHelper {

        private static final String PEER_CREATE =
                "CREATE TABLE " + PEERS_TABLE + " (" +
                        PeerContract.ID + " integer primary key," +
                        PeerContract.NAME + " text not null," +
                        PeerContract.TIMESTAMP + " long not null," +
                        PeerContract.ADDRESS + " text not null);";

        private static final String MESSAGE_CREATE =
                "CREATE TABLE " + MESSAGES_TABLE + " (" +
                        PeerContract.ID + " integer primary key," +
                        MessageContract.MESSAGE_TEXT + " text not null," +
                        MessageContract.TIMESTAMP + " long not null," +
                        MessageContract.SENDER + " text not null," +
                        MessageContract.SENDER_ID + " integer not null," +
                        "FOREIGN KEY (" + MessageContract.SENDER_ID + ") REFERENCES " +
                        PEERS_TABLE + "(" + PeerContract.ID + ") ON DELETE CASCADE );";

        private static final String CREATE_MESSAGES_PEER_INDEX =
                "CREATE INDEX " + MESSAGES_PEER_INDEX + " ON " + MESSAGES_TABLE + "(" + MessageContract.SENDER_ID + ");";

        private static final String CREATE_PEER_NAME_INDEX =
                "CREATE INDEX " + PEER_NAME_INDEX + " ON " + PEERS_TABLE + "(" + PeerContract.NAME + ");";


        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO initialize database tables
            db.execSQL(MESSAGE_CREATE);
            db.execSQL(CREATE_MESSAGES_PEER_INDEX);
            db.execSQL(PEER_CREATE);
            db.execSQL(CREATE_PEER_NAME_INDEX);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO upgrade database if necessary
            if (oldVersion < newVersion) {
                db.execSQL("PRAGMA foreign_key = ON");
                db.execSQL("DROP TABLE IF EXISTS " + MESSAGES_TABLE);
                db.execSQL("DROP TABLE IF EXISTS " + PEERS_TABLE);
                onCreate(db);
            }
        }
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        // Initialize your content provider on startup.
        dbHelper = new DbHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        return true;
    }

    // Used to dispatch operation based on URI
    private static final UriMatcher uriMatcher;

    // uriMatcher.addURI(AUTHORITY, CONTENT_PATH, OPCODE)
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, MESSAGE_CONTENT_PATH, MESSAGES_ALL_ROWS);
        uriMatcher.addURI(AUTHORITY, MESSAGE_CONTENT_PATH_ITEM, MESSAGES_SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, PEER_CONTENT_PATH, PEERS_ALL_ROWS);
        uriMatcher.addURI(AUTHORITY, PEER_CONTENT_PATH_ITEM, PEERS_SINGLE_ROW);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                return MessageContract.CONTENT_PATH;
            case MESSAGES_SINGLE_ROW:
                return MessageContract.CONTENT_PATH_ITEM;
            case PEERS_ALL_ROWS:
                return PeerContract.CONTENT_PATH;
            case PEERS_SINGLE_ROW:
                return PeerContract.CONTENT_PATH_ITEM;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                // TODO: Implement this to handle requests to insert a new message.
                // Make sure to notify any observers
                long messagesRow = db.insert(MESSAGES_TABLE, null, values);
                if (messagesRow > 0) {
                    Uri instanceUri = MessageContract.CONTENT_URI(messagesRow);
                    ContentResolver contentResolver = getContext().getContentResolver();
                    contentResolver.notifyChange(instanceUri, null);
                    return instanceUri;
                }
            case PEERS_ALL_ROWS:
                // TODO: Implement this to handle requests to insert a new peer.
                // Make sure to notify any observers
                long peersRow = db.insert(PEERS_TABLE, null, values);
                if (peersRow > 0) {
                    Uri instanceUri = PeerContract.CONTENT_URI(peersRow);
                    ContentResolver contentResolver = getContext().getContentResolver();
                    contentResolver.notifyChange(instanceUri, null);
                    return instanceUri;
                }
            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                // TODO: Implement this to handle query of all messages.
                return db.query(MESSAGES_TABLE,
                        MESSAGE_PROJECTION,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            case PEERS_ALL_ROWS:
                // TODO: Implement this to handle query of all peers.
                return db.query(PEERS_TABLE,
                        PEER_PROJECTION,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            case MESSAGES_SINGLE_ROW:
                // TODO: Implement this to handle query of a specific message.
                selection = MessageContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(MessageContract.getId(uri))};
                return db.query(PEERS_TABLE,
                        MESSAGE_PROJECTION,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            case PEERS_SINGLE_ROW:
                // TODO: Implement this to handle query of a specific peer.
                selection = PeerContract.ID + "=?";
                selectionArgs = new String[]{String.valueOf(PeerContract.getId(uri))};
                return db.query(PEERS_TABLE,
                        PEER_PROJECTION,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                return db.update(MESSAGES_TABLE, values, selection, selectionArgs);
            case PEERS_ALL_ROWS:
                return db.update(PEERS_TABLE, values, selection, selectionArgs);
            case MESSAGES_SINGLE_ROW:
                selection = MessageContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(MessageContract.getId(uri))};
                return db.update(MESSAGES_TABLE, values, selection, selectionArgs);
            case PEERS_SINGLE_ROW:
                selection = PeerContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(PeerContract.getId(uri))};
                return db.update(PEERS_TABLE, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MESSAGES_ALL_ROWS:
                return db.delete(MESSAGES_TABLE, selection, selectionArgs);
            case PEERS_ALL_ROWS:
                return db.delete(PEERS_TABLE, selection, selectionArgs);
            case MESSAGES_SINGLE_ROW:
                selection = MessageContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(MessageContract.getId(uri))};
                return db.delete(MESSAGES_TABLE, selection, selectionArgs);
            case PEERS_SINGLE_ROW:
                selection = PeerContract._ID + "=?";
                selectionArgs = new String[]{String.valueOf(PeerContract.getId(uri))};
                return db.delete(PEERS_TABLE, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}
