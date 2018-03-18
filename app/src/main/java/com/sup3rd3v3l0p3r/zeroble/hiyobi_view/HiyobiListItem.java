package com.sup3rd3v3l0p3r.zeroble.hiyobi_view;

import android.graphics.drawable.Drawable;

/**
 * Created by dlals on 2018-03-18.
 */

public class HiyobiListItem {
    private Drawable iconDrawable;
    private String titleStr;
    private String artistStr;
    private String originalStr;
    private String typeStr;
    private String tagsStr;
    private String urlStr;

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setArtistStr(String artistStr) {
        this.artistStr = artistStr;
    }

    public void setOriginalStr(String originalStr) {
        this.originalStr = originalStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public void setTagsStr(String tagsStr) {
        this.tagsStr = tagsStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public String getArtistStr() {
        return artistStr;
    }

    public String getOriginalStr() {
        return originalStr;
    }

    public String getTagsStr() {
        return tagsStr;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public String getUrlStr() {
        return urlStr;
    }
}