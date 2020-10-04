package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.InetAddress;
import java.util.Date;

import edu.stevens.cs522.chatserver.contracts.PeerContract;

/**
 * Created by dduggan.
 */

public class Peer implements Parcelable, Persistable {

    // Will be database key
    public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    // Where we heard from them
    public InetAddress address;

    public Peer() {
    }

    public Peer(Cursor cursor) {
        // TODO
        id = PeerContract.getID(cursor);
        name = PeerContract.getName(cursor);
        timestamp = new Date(PeerContract.getTimestamp(cursor));
        try {
            address = InetAddress.getByAddress(PeerContract.getAddress(cursor));
        } catch (Exception exception) {
            address = null;
        }
    }

    public Peer(Parcel in) {
        // TODO
        id = in.readLong();
        name = in.readString();
        timestamp = new Date(in.readLong());
        address = (InetAddress)in.readValue(InetAddress.class.getClassLoader());
        Log.i("DEBUG", "*** Peer parcel in adddrees " + address);
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        PeerContract.putName(out, name);
        PeerContract.putTimestamp(out, timestamp.getTime());
        PeerContract.putAddress(out, address.getAddress());
        Log.i("DEBUG", "*** address in Peer writeToProvider: " + address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO
        out.writeLong(id);
        out.writeString(name);
        out.writeLong(timestamp.getTime());
        out.writeByteArray(address.getAddress());
        Log.i("DEBUG", "*** writeToParcel address: " + address);
    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            // TODO
            return new Peer(source);
        }

        @Override
        public Peer[] newArray(int size) {
            // TODO
            return new Peer[size];
        }

    };
}
