package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by dduggan.
 */

public class PeerContract implements BaseColumns {

    //    private static int peerTextColumn = -1;

    // TODO define column names, getters for cursors, setters for contentvalues

    public static final String _ID = "_id";

    public static final String NAME = "name";

    public static final String TIMESTAMP = "timestamp";

    public static final String ADDRESS = "address";


    public static String getPeerID(Cursor cursor) {
        int peerIDColumn = cursor.getColumnIndexOrThrow(_ID);
        return cursor.getString(peerIDColumn);
    }

    public static void putPeerID(ContentValues out, String peerID) {
        out.put(_ID, peerID);
    }

    public static String getPeerName(Cursor cursor) {
        int peerNameColumn = cursor.getColumnIndexOrThrow(NAME);
        return cursor.getString(peerNameColumn);
    }

    public static void putPeerName(ContentValues out, String peerName) {
        out.put(NAME, peerName);
    }

    public static String getPeerTimestamp(Cursor cursor) {
        int peerTimestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        return cursor.getString(peerTimestampColumn);
    }

    public static void putPeerTimestamp(ContentValues out, String peerTimestamp) {
        out.put(TIMESTAMP, peerTimestamp);
    }

    public static String getPeerAddress(Cursor cursor) {
        int peerAddressColumn = cursor.getColumnIndexOrThrow(ADDRESS);
        return cursor.getString(peerAddressColumn);
    }

    public static void putPeerAddress(ContentValues out, String peerAddress) {
        out.put(ADDRESS, peerAddress);
    }
}
