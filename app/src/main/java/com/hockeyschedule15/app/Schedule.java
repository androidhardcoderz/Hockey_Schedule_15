package com.hockeyschedule15.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Scott on 9/17/2015.
 */
public class Schedule implements Parcelable {

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    private ArrayList<Game> games = new ArrayList<Game>();

    public Schedule() {
        games = new ArrayList<Game>();
    }

    protected Schedule(Parcel in) {
        if (in.readByte() == 0x01) {
            games = new ArrayList<>();
            in.readList(games, Game.class.getClassLoader());
        } else {
            games = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (games == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(games);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
