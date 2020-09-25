package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by dduggan.
 */

public class MessageContract implements BaseColumns {

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    // TODO remaining columns in Messages table

//    private static int messageTextColumn = -1;


    public static String getMessageText(Cursor cursor) {
        int messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        return cursor.getString(messageTextColumn);
    }

    public static void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    public static String getMessageTimeStamp(Cursor cursor) {
        int messageTimeStamp = cursor.getColumnIndexOrThrow(TIMESTAMP);
        return cursor.getString(messageTimeStamp);
    }

    public static void putMessageTimestamp(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    // TODO remaining getter and putter operations for other columns
}
