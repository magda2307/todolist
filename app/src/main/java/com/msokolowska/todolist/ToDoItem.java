package com.msokolowska.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
// This class represents a single item from to do list.
public class ToDoItem implements Parcelable {
    private String id;
    private String date;
    private String text;
    private boolean isDeleted;

    public ToDoItem(String id, String date, String text, boolean isDeleted) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.isDeleted = isDeleted;
    }

    // Parcelable constructor
    protected ToDoItem(Parcel in) {
        id = in.readString();
        date = in.readString();
        text = in.readString();
        isDeleted = in.readByte() != 0;
    }
    // Parcelable creator
    public static final Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    // Implementation of writeToParcel method for Parcelable interface
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(text);
        dest.writeByte((byte) (isDeleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}