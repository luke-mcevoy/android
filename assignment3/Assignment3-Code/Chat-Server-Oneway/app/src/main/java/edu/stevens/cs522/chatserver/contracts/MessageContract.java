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

    private static int messageTextColumn = -1;
    public static String getMessageText(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        }
        return cursor.getString(messageTextColumn);
    }
    public static void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }


    private static int messageTimestampColumn = -1;
    public static long getMessageTimeStamp(Cursor cursor) {
        if (messageTimestampColumn < 0) {
            messageTimestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(messageTimestampColumn);
    }
    public static void putMessageTimestamp(ContentValues out, long messageTimestamp) {
        out.put(TIMESTAMP, messageTimestamp);
    }


    private static int messageSenderColumn = -1;
    public static String getMessageSender(Cursor cursor) {
        if (messageSenderColumn < 0) {
            messageSenderColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(messageSenderColumn);
    }
    public static void putMessageSender(ContentValues out, String messageSender) {
        out.put(SENDER, messageSender);
    }
}
