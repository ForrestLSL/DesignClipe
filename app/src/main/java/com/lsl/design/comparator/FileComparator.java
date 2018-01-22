package com.lsl.design.comparator;

import com.lsl.design.bean.FileInfo;

import java.util.Comparator;

/**
 * Created by Forrest
 * on 2017/11/8 19:35
 */

public class FileComparator implements Comparator<FileInfo> {
    @Override
    public int compare(FileInfo o1, FileInfo o2) {
        if(o1.getFileLastModified() < o2.getFileLastModified())
        {
            return 1;
        }else
        {
            return -1;
        }
    }
//
//    @Override
//    public int compare(FileInfo o1, FileInfo o2) {
//        return o1.getFileName().compareTo(o2.getFileName());
//    }
}
