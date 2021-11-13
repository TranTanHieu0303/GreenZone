package com.example.greenzone.Class;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class MediaFile implements Parcelable {
    private String path;
    private String type;
    private int selectCount;
    //để biets vị trí nào trong adapter để refresh đúng index đó
    private int position;

    public MediaFile() {
    }

    public MediaFile(String path, String type, int selectCount, int position) {
        this.path = path;
        this.type = type;
        this.selectCount = selectCount;
        this.position = position;
    }

    protected MediaFile(Parcel in) {
        path = in.readString();
        type = in.readString();
        selectCount = in.readInt();
        position = in.readInt();
    }

    public static final Creator<MediaFile> CREATOR = new Creator<MediaFile>() {
        @Override
        public MediaFile createFromParcel(Parcel in) {
            return new MediaFile(in);
        }

        @Override
        public MediaFile[] newArray(int size) {
            return new MediaFile[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        //return super.equals(obj);
        return this.getSelectCount()==((MediaFile)obj).getSelectCount();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(type);
        dest.writeInt(selectCount);
        dest.writeInt(position);
    }
    public static ArrayList<MediaFile> getGalleryPhotos(Context context) {
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        // Get relevant columns for use later.
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

// Return only video and image metadata.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");

        CursorLoader cursorLoader = new CursorLoader(
                context,
                queryUri,
                projection,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " ASC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                MediaFile picture=new MediaFile();

                int indexPath=cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                String type = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                picture.setPath(cursor.getString(indexPath));

                Log.d("Duongdananh",picture.getPath());
                mediaFiles.add(picture);
            }
        }

        Collections.reverse(mediaFiles);
        return mediaFiles;
    }
}
