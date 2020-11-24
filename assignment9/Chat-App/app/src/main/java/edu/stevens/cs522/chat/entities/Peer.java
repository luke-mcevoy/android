package edu.stevens.cs522.chat.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.util.Date;

import edu.stevens.cs522.chat.contracts.PeerContract;

/**
 * Created by dduggan.
 */

public class Peer implements Parcelable, Persistable {

    public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    public Double longitude;

    public Double latitude;

    public Peer() {
    }

    public Peer(Cursor cursor) {
        // TODO
        id = PeerContract.getID(cursor);
        name = PeerContract.getName(cursor);
        timestamp = new Date(PeerContract.getTimestamp(cursor));
        longitude = PeerContract.getLongitude(cursor);
        latitude = PeerContract.getLatitude(cursor);
    }

    public Peer(Parcel in) {
        // TODO
        id = in.readLong();
        name = in.readString();
        timestamp = new Date(in.readLong());
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        PeerContract.putID(out, id);
        PeerContract.putName(out, name);
        PeerContract.putTimestamp(out, timestamp.getTime());
        PeerContract.putLongitude(out, longitude);
        PeerContract.putLatitude(out, latitude);
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
        out.writeDouble(longitude);
        out.writeDouble(latitude);
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
