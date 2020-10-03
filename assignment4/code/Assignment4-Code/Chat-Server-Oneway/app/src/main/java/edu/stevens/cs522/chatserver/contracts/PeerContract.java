package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.InetAddress;

import static edu.stevens.cs522.chatserver.contracts.BaseContract.withExtendedPath;

/**
 * Created by dduggan.
 */

public class PeerContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Peer");

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    // TODO define column names, getters for cursors, setters for contentvalues

    public static final String _ID = "_id";

    public static final String NAME = "name";

    public static final String TIMESTAMP = "timestamp";

    public static final String ADDRESS = "address";


    private static int peerIDColumn = -1;

    public static long getID(Cursor cursor) {
        if (peerIDColumn < 0) {
            peerIDColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getLong(peerIDColumn);
    }

    public static void putID(ContentValues out, String peerID) {
        out.put(_ID, peerID);
    }


    public static int peerNameColumn = -1;

    public static String getName(Cursor cursor) {
        if (peerNameColumn < 0) {
            peerNameColumn = cursor.getColumnIndexOrThrow(NAME);
        }
        return cursor.getString(peerNameColumn);
    }

    public static void putName(ContentValues out, String peerName) {
        out.put(NAME, peerName);
    }


    public static int peerTimestampColumn = -1;

    public static long getTimestamp(Cursor cursor) {
        if (peerTimestampColumn < 0) {
            peerTimestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(peerTimestampColumn);
    }

    public static void putTimestamp(ContentValues out, long peerTimestamp) {
        out.put(TIMESTAMP, peerTimestamp);
    }


    public static int peerAddressColumn = -1;

    public static byte[] getAddress(Cursor cursor) {
        if (peerAddressColumn < 0) {
            peerAddressColumn = cursor.getColumnIndexOrThrow(ADDRESS);
        }
        return cursor.getBlob(peerAddressColumn);
    }

    public static void putAddress(ContentValues out, byte[] peerAddress) {
        out.put(ADDRESS, peerAddress);
    }

}
