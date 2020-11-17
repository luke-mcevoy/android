package edu.stevens.cs522.chat.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import edu.stevens.cs522.chat.entities.Peer;

/**
 * Created by dduggan.
 */

public class PeerContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Peer");;

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    // TODO define column names, getters for cursors, setters for contentvalues
    public static final String NAME = "name";

    public static final String TIMESTAMP = "timestamp";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String ID = _ID;

    private static int nameColumn = -1;

    public static String getName(Cursor cursor) {
        if (nameColumn < 0) {
            nameColumn = cursor.getColumnIndexOrThrow(NAME);
        }
        return cursor.getString(nameColumn);
    }

    public static void putName(ContentValues out, String name) {
        out.put(NAME, name);
    }

    private static int timestampColumn = -1;

    public static long getTimestamp(Cursor cursor) {
        if (timestampColumn < 0) {
            timestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(timestampColumn);
    }

    public static void putTimestamp(ContentValues out, long timestamp) {
        out.put(TIMESTAMP, timestamp);
    }

    private static int longitudeColumn = -1;

    public static Double getLongitude(Cursor cursor) {
        if (longitudeColumn < 0) {
            longitudeColumn = cursor.getColumnIndexOrThrow(LONGITUDE);
        }
        return cursor.getDouble(longitudeColumn);
    }

    public static void putLongitude(ContentValues out, Double longitude) {
        out.put(LONGITUDE, longitude);
    }

    private static int latitudeColumn = -1;

    public static Double getLatitude(Cursor cursor) {
        if (latitudeColumn < 0) {
            latitudeColumn = cursor.getColumnIndexOrThrow(LATITUDE);
        }
        return cursor.getDouble(latitudeColumn);
    }

    public static void putLatitude(ContentValues out, Double latitude) {
        out.put(LATITUDE, latitude);
    }

    private static int IDColumn = -1;

    public static long getID(Cursor cursor) {
        if (IDColumn < 0) {
            IDColumn = cursor.getColumnIndexOrThrow(ID);
        }
        return cursor.getInt(IDColumn);
    }

    public static void putID(ContentValues out, long id) {
        out.put(ID, id);
    }


}
