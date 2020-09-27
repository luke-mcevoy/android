package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public Peer(Cursor cursor) throws UnknownHostException {
        // TODO
        id = PeerContract.getPeerID(cursor);
        name = PeerContract.getPeerName(cursor);
        timestamp = new Date(PeerContract.getPeerTimestamp(cursor));
        Log.i("TEST", "Peer(cursor) timestamp is : " + timestamp);
        try {
            address = InetAddress.getByAddress(PeerContract.getPeerAddress(cursor));
        } catch (Exception exception) {
            address = null;
        }
    }

    public Peer(Parcel in) {
        // TODO
        id = in.readLong();
        name = in.readString();
        timestamp = new Date(in.readLong());
//        byte[] addressByteArray = new byte[in.readInt()];
//        in.readByteArray(addressByteArray);
//        address = InetAddress.getByAddress(addressByteArray);
        address = (InetAddress)in.readValue(InetAddress.class.getClassLoader());
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
//        PeerContract.putPeerID(out, id);
        PeerContract.putPeerName(out, name);
        PeerContract.putPeerTimestamp(out, timestamp);
        Log.i("TEST", "writeToProvider peer timestamp is : " + timestamp);
        PeerContract.putPeerAddress(out, address.getAddress());
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
        out.writeInt(address.getAddress().length);
        out.writeByteArray(address.getAddress());
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
