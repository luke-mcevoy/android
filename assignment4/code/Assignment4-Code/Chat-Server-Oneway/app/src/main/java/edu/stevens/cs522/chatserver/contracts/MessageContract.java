package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dduggan.
 */

public class MessageContract extends BaseContract {

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Message");

    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    public static final String ID = _ID;

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    // TODO remaining columns in Messages table

    private int messageTextColumn = -1;

    public String getMessageText(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        }
        return cursor.getString(messageTextColumn);
    }

    public void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    // TODO remaining getter and putter operations for other columns

    private int messageIDColumn = -1;

    public long getID(Cursor cursor) {
        if (messageIDColumn < 0) {
            messageIDColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getLong(messageIDColumn);
    }

    public void putID(ContentValues out, long messageID) {
        out.put(_ID, messageID);
    }


    private int messageTimestampColumn = -1;

    private long getTimestamp(Cursor cursor) {
        if (messageTimestampColumn < 0) {
            messageTimestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(messageTimestampColumn);
    }

    private void putTimestamp(ContentValues out, long messageTimestamp) {
        out.put(TIMESTAMP, messageTimestamp);
    }


    private int messageSenderColumn = -1;

    private String getSender(Cursor cursor) {
        if (messageSenderColumn < 0) {
            messageSenderColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(messageSenderColumn);
    }

    public void putSender(ContentValues out, String messageSender) {
        out.put(SENDER, messageSender);
    }
}
