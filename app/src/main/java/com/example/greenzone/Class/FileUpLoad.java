package com.example.greenzone.Class;

public class FileUpLoad {
    String filenames,size;
    Integer count;

    public FileUpLoad() {
    }

    public FileUpLoad(String filenames, String size, Integer count) {
        this.filenames = filenames;
        this.size = size;
        this.count = count;
    }

    public String getFilenames() {
        return filenames;
    }

    public void setFilenames(String filenames) {
        this.filenames = filenames;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
