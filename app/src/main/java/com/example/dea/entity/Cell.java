package com.example.dea.entity;

import androidx.annotation.Nullable;

public class Cell {
    @Nullable
    private String mData;

    public Cell(@Nullable String data) {
        this.mData = data;
    }

    @Nullable
    public String getData() {
        return mData;
    }

    public void setmData(@Nullable String mData) {
        this.mData = mData;
    }
    public static class ColumnHeader extends Cell {
        public ColumnHeader(@Nullable String  data) {
            super(data);
        }
    }
    public static class RowHeader extends Cell {
        public RowHeader(@Nullable String data) {
            super(data);
        }
    }

}
