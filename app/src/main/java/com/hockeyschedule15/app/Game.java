package com.hockeyschedule15.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 9/17/2015.
 */
public class Game implements Parcelable {

    private String id;
    private String status;
    private String scheduled;
    private String hTeam;
    private String vTeam;
    private String time;
    private String date;
    private int awayColor;
    private int homeColor;
    private int awayIndex;
    private int homeIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String gethTeam() {
        return hTeam;
    }

    public void sethTeam(String hTeam) {
        this.hTeam = hTeam;
    }

    public String getvTeam() {
        return vTeam;
    }

    public void setvTeam(String vTeam) {
        this.vTeam = vTeam;
    }

    public Game() {

    }

    public String getMonth(){
        return getScheduled().substring(0,2);
    }

    protected Game(Parcel in) {
        id = in.readString();
        status = in.readString();
        scheduled = in.readString();
        hTeam = in.readString();
        vTeam = in.readString();
        time = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(scheduled);
        dest.writeString(hTeam);
        dest.writeString(vTeam);
        dest.writeString(time);
    }

    @SuppressWarnings("unused")
    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getAwayColor() {
        return awayColor;
    }

    public void setAwayColor(int awayColor) {
        this.awayColor = awayColor;
    }

    public int getHomeColor() {
        return homeColor;
    }

    public void setHomeColor(int homeColor) {
        this.homeColor = homeColor;
    }

    public int getAwayIndex() {
        return awayIndex;
    }

    public void setAwayIndex(int awayIndex) {
        this.awayIndex = awayIndex;
    }

    public int getHomeIndex() {
        return homeIndex;
    }

    public void setHomeIndex(int homeIndex) {
        this.homeIndex = homeIndex;
    }
}

/*
"id": "4002b18e-f101-4e0a-b429-ceacd2632c42",
            "status": "scheduled",
            "coverage": "full",
            "scheduled": "2016-04-10T02:30:00+00:00",
            "venue": {
                "id": "1da65282-af4c-4b81-a9de-344b76bb20b0",
                "name": "SAP Center at San Jose",
                "capacity": 17562,
                "address": "525 W. Santa Clara St.",
                "city": "San Jose",
                "state": "CA",
                "zip": "95113",
                "country": "USA"
            },
            "home": {
                "name": "San Jose Sharks",
                "alias": "SJ",
                "id": "44155909-0f24-11e2-8525-18a905767e44"
            },
            "away": {
                "name": "Arizona Coyotes",
                "alias": "ARI",
                "id": "44153da1-0f24-11e2-8525-18a905767e44"
            }
 */
