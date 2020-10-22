package edu.stevens.cs522.chatserver.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.Set;

import edu.stevens.cs522.chatserver.async.AsyncContentResolver;
import edu.stevens.cs522.chatserver.async.IContinue;
import edu.stevens.cs522.chatserver.async.IEntityCreator;
import edu.stevens.cs522.chatserver.async.QueryBuilder;
import edu.stevens.cs522.chatserver.async.IQueryListener;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;


/**
 * Created by dduggan.
 */

public class MessageManager extends Manager<Message> {

    private static final int LOADER_ID = 1;

    private static final IEntityCreator<Message> creator = new IEntityCreator<Message>() {
        @Override
        public Message create(Cursor cursor) {
            return new Message(cursor);
        }
    };

    public MessageManager(Context context) {
        super(context, creator, LOADER_ID);
    }

    public void getAllMessagesAsync(IQueryListener<Message> listener) {
        // TODO use QueryBuilder to complete this
        executeQuery(MessageContract.CONTENT_URI, listener);
    }

    public void getMessagesByPeerAsync(Peer peer, IQueryListener<Message> listener) {
        // TODO use QueryBuilder to complete this
        // Remember to reset the loader!
//        listener.closeResults();
        String[] projection = new String[]{MessageContract._ID, MessageContract.MESSAGE_TEXT};
        String selection = MessageContract.SENDER + "=?";
        String[] selectionArgs = new String[]{String.valueOf(peer.name)};
        executeQuery(MessageContract.CONTENT_URI, projection, selection, selectionArgs, null, listener);
    }

    public void persistAsync(final Message message) {
        // TODO use AsyncContentResolver to complete this
        AsyncContentResolver cr = getAsyncResolver();
        ContentValues out = new ContentValues();
        message.writeToProvider(out);
        cr.insertAsync(MessageContract.CONTENT_URI,
                out,
                new IContinue<Uri>() {
                    @Override
                    public void kontinue(Uri uri) {
                        message.id = MessageContract.getId(uri);
                    }
                });
    }
}
