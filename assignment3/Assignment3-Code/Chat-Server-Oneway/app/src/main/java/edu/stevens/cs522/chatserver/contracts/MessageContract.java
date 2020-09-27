package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by dduggan.
 */

public class MessageContract implements BaseColumns {

    public static final String _ID = "_id";

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    public static final String SENDER_ID = "sender_id";


    // TODO remaining columns in Messages table


    private static int messageIDColumn = -1;
    public static String getMessageID(Cursor cursor) {
        if (messageIDColumn < 0) {
            messageIDColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getString(messageIDColumn);
    }
    public static void putMessageID(ContentValues out, long messageID) {
        out.put(MESSAGE_TEXT, messageID);
    }


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
    public static String getMessageTimeStamp(Cursor cursor) {
        if (messageTimestampColumn < 0) {
            messageTimestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getString(messageTimestampColumn);
    }
    public static void putMessageTimestamp(ContentValues out, String messageTimestamp) {
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


    private static int messageSenderIDColumn = -1;
    public static String getMessageSenderID(Cursor cursor) {
        if (messageSenderIDColumn < 0) {
            messageSenderIDColumn = cursor.getColumnIndexOrThrow(SENDER_ID);
        }
        return cursor.getString(messageSenderIDColumn);
    }
    public static void putMessageSenderID(ContentValues out, long messageSenderID) {
        out.put(SENDER_ID, messageSenderID);
    }
    // TODO remaining getter and putter operations for other columns
}
