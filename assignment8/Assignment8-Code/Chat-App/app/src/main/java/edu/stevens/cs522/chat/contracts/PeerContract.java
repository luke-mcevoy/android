package edu.stevens.cs522.chat.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import edu.stevens.cs522.chat.entities.Peer;

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
    public static final String NAME = "name";

    public static final String TIMESTAMP = "timestamp";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    private static final String _ID = "_id";

    private int nameColumn = -1;

    public String getName(Cursor cursor) {
        if (nameColumn < 0) {
            nameColumn = cursor.getColumnIndexOrThrow(NAME);
        }
        return cursor.getString(nameColumn);
    }

    public void putName(ContentValues out, String name) {
        out.put(NAME, name);
    }

    private int timestampColumn = -1;

    public long getTimestamp(Cursor cursor) {
        if (timestampColumn < 0) {
            timestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(timestampColumn);
    }

    public void putTimestamp(ContentValues out, long timestamp) {
        out.put(TIMESTAMP, timestamp);
    }

    private int longitudeColumn = -1;

    public double getLongitude(Cursor cursor) {
        if (longitudeColumn < 0) {
            longitudeColumn = cursor.getColumnIndexOrThrow(LONGITUDE);
        }
        return cursor.getDouble(longitudeColumn);
    }

    public void putLongitude(ContentValues out, long longitude) {
        out.put(LONGITUDE, longitude);
    }

    private int latitudeColumn = -1;

    public double getLatitude(Cursor cursor) {
        if (latitudeColumn < 0) {
            latitudeColumn = cursor.getColumnIndexOrThrow(LATITUDE);
        }
        return cursor.getDouble(latitudeColumn);
    }

    public void putLatitude(ContentValues out, long latitude) {
        out.put(LATITUDE, latitude);
    }

    private int IDColumn = -1;

    public int getID(Cursor cursor) {
        if (IDColumn < 0) {
            IDColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getInt(IDColumn);
    }

    public void putID(ContentValues out, int ID) {
        out.put(_ID, ID);
    }


}
