package com.lsl.design.bean;

/**
 * Created by Forrest
 * on 2017/11/8 19:28
 */

public class FileInfo {
    private String fileName;
    private String filePath;
    private long fileLastModified;

    public FileInfo(String fileName, String filePath, long fileLastModified) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileLastModified = fileLastModified;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileLastModified() {
        return fileLastModified;
    }

    public void setFileLastModified(long fileLastModified) {
        this.fileLastModified = fileLastModified;
    }
}
