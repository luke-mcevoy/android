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

//    private static int messageTextColumn = -1;

    public static String getMessageID(Cursor cursor) {
        int messageIDColumn = cursor.getColumnIndexOrThrow(_ID);
        return cursor.getString(messageIDColumn);
    }
    public static void putMessageID(ContentValues out, long messageID) {
        out.put(MESSAGE_TEXT, messageID);
    }


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
    public static void putMessageTimestamp(ContentValues out, String messageTimestamp) {
        out.put(TIMESTAMP, messageTimestamp);
    }



    public static String getMessageSender(Cursor cursor) {
        int messageSender = cursor.getColumnIndexOrThrow(SENDER);
        return cursor.getString(messageSender);
    }
    public static void putMessageSender(ContentValues out, String messageSender) {
        out.put(SENDER, messageSender);
    }



    public static String getMessageSenderID(Cursor cursor) {
        int messageSenderID = cursor.getColumnIndexOrThrow(SENDER_ID);
        return cursor.getString(messageSenderID);
    }
    public static void putMessageSenderID(ContentValues out, long messageSenderID) {
        out.put(SENDER_ID, messageSenderID);
    }
    // TODO remaining getter and putter operations for other columns
}
