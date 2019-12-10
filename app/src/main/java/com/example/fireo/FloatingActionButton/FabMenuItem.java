package com.example.fireo.FloatingActionButton;

import android.graphics.Bitmap;

public class FabMenuItem {
    private String title;
    private Bitmap icon;

    public FabMenuItem(String title, Bitmap icon) {
        this.title = title;
        this.icon = icon;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
