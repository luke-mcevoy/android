package edu.stevens.cs522.chat.async;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.chat.managers.TypedCursor;

/**
 * Created by dduggan.
 */

public class SimpleQueryBuilder<T> implements IContinue<Cursor>{

    private String tag;

    private IEntityCreator<T> creator;

    private IQueryListener<T> listener;

    private SimpleQueryBuilder(String tag,
                               IEntityCreator<T> creator,
                               ISimpleQueryListener<T> listener) {
        // TODO
        this.tag = tag;
        this.creator = creator;
        this.listener = (IQueryListener<T>) listener;
    }

    public static <T> void executeQuery(String tag,
                                        Activity context,
                                        Uri uri,
                                        String[] columns,
                                        String select,
                                        String[] selectArgs,
                                        IEntityCreator<T> creator,
                                        ISimpleQueryListener<T> listener) {

        SimpleQueryBuilder<T> qb = new SimpleQueryBuilder<T>(tag, creator, listener);

        AsyncContentResolver contentResolver = new AsyncContentResolver(context.getContentResolver());

        contentResolver.queryAsync(uri, columns, select, selectArgs, null, qb);
    }

    public static <T> void executeQuery(String tag,
                                        Activity context,
                                        Uri uri,
                                        IEntityCreator<T> creator,
                                        ISimpleQueryListener<T> listener) {
        executeQuery(tag, context, uri, null, null, null, creator, listener);
    }

    @Override
    public void kontinue(Cursor cursor) {
        // TODO complete this
        List<T> instances = new ArrayList<T>();
//        TypedCursor<T> instances = new TypedCursor<T>()
        if (cursor.moveToFirst()) {
            do {
                T instance = creator.create(cursor);
                instances.add(instance);
            } while (cursor.moveToNext());
        }
        cursor.close();
        listener.handleResults((TypedCursor<T>) instances);
    }

}
