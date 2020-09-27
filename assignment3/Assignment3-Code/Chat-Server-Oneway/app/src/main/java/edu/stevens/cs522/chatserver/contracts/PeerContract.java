package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by dduggan.
 */

public class PeerContract implements BaseColumns {

    // TODO define column names, getters for cursors, setters for contentvalues

    public static final String _ID = "_id";

    public static final String NAME = "name";

    public static final String TIMESTAMP = "timestamp";

    public static final String ADDRESS = "address";


    private static int peerIDColumn = -1;
    public static long getPeerID(Cursor cursor) {
        if (peerIDColumn < 0) {
            peerIDColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getLong(peerIDColumn);
    }
    public static void putPeerID(ContentValues out, long peerID) {
        out.put(_ID, peerID);
    }


    private static int peerNameColumn = -1;
    public static String getPeerName(Cursor cursor) {
        if (peerNameColumn < 0) {
            peerNameColumn = cursor.getColumnIndexOrThrow(NAME);
        }
        return cursor.getString(peerNameColumn);
    }
    public static void putPeerName(ContentValues out, String peerName) {
        out.put(NAME, peerName);
    }


    private static int peerTimestampColumn = -1;
    public static String getPeerTimestamp(Cursor cursor) {
        if (peerTimestampColumn < 0) {
            peerTimestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getString(peerTimestampColumn);
    }
    public static void putPeerTimestamp(ContentValues out, Date peerTimestamp) {
        out.put(TIMESTAMP, String.valueOf(peerTimestamp));
    }


    private static int peerAddressColumn = -1;
    public static String getPeerAddress(Cursor cursor) {
        if (peerAddressColumn < 0) {
            peerAddressColumn = cursor.getColumnIndexOrThrow(ADDRESS);
        }
        return cursor.getString(peerAddressColumn);
    }
    public static void putPeerAddress(ContentValues out, InetAddress peerAddress) {
        out.put(ADDRESS, String.valueOf(peerAddress));
    }
}
