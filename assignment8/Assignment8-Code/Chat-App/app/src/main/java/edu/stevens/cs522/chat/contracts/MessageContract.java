package edu.stevens.cs522.chat.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

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

    public static final String SEQUENCE_NUMBER = "sequence_number";

    public static final String MESSAGE_TEXT = "message_text";

    public static final String CHAT_ROOM = "chat_room";

    public static final String TIMESTAMP = "timestamp";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String SENDER = "sender";

    public static final String SENDER_ID = "sender_id";

    public static final String[] COLUMNS = {ID, SEQUENCE_NUMBER, MESSAGE_TEXT, CHAT_ROOM, TIMESTAMP, LATITUDE, LONGITUDE, SENDER, SENDER_ID};

    // TODO remaining columns in Messages table

    private int sequenceNumberColumn = -1;

    public String getSequenceNumber(Cursor cursor) {
        if (sequenceNumberColumn < 0) {
            sequenceNumberColumn = cursor.getColumnIndexOrThrow(SEQUENCE_NUMBER);
        }
        return cursor.getString(sequenceNumberColumn);
    }

    public void putSequenceNumberColumn(ContentValues out, String messageText) {
        out.put(SEQUENCE_NUMBER, messageText);
    }

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
    private int chatRoomColumn = -1;

    public int getChatRoom(Cursor cursor) {
        if (chatRoomColumn < 0) {
            chatRoomColumn = cursor.getColumnIndexOrThrow(CHAT_ROOM);
        }
        return cursor.getInt(chatRoomColumn);
    }

    public void putChatRoom(ContentValues out, int chatRoom) {
        out.put(CHAT_ROOM, chatRoom);
    }

    private int timeStampColumn = -1;

    public long getTimeStampColumn(Cursor cursor) {
        if (timeStampColumn < 0) {
            timeStampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(timeStampColumn);
    }

    public void putTimeStampColumn(ContentValues out, long timestamp) {
        out.put(TIMESTAMP, timestamp);
    }

    private int latitudeColumn = -1;

    public double getLatitude(Cursor cursor) {
        if (latitudeColumn < 0) {
            latitudeColumn = cursor.getColumnIndexOrThrow(LATITUDE);
        }
        return cursor.getDouble(latitudeColumn);
    }

    public void putLatitude(ContentValues out, double latitude) {
        out.put(LATITUDE, latitude);
    }

    private int longitudeColumn = -1;

    public double getLongitude(Cursor cursor) {
        if (longitudeColumn < 0) {
            longitudeColumn = cursor.getColumnIndexOrThrow(LONGITUDE);
        }
        return cursor.getDouble(longitudeColumn);
    }

    public void putLongitude(ContentValues out, double longitude) {
        out.put(LONGITUDE, longitude);
    }

    private int senderColumn = -1;

    public String getSender(Cursor cursor) {
        if (senderColumn < 0) {
            senderColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(senderColumn);
    }

    public void putSender(ContentValues out, String sender) {
        out.put(SENDER, sender);
    }

    private int senderIDColumn = -1;

    public int getSenderID(Cursor cursor) {
        if (senderIDColumn < 0) {
            senderIDColumn = cursor.getColumnIndexOrThrow(SENDER_ID);
        }
        return cursor.getInt(senderIDColumn);
    }

    public void putSenderID(ContentValues out, int senderID) {
        out.put(SENDER_ID, senderID);
    }



}
