package com.lsl.design.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;

/**
 * Created by Forrest
 * on 2017/11/8 20:52
 */

public class PhotoPathUtil {

    private Bitmap.CompressFormat format;
    private int height;
    private int width;
    private int maxSize;

    private String photoFilePath ;
    public PhotoPathUtil(String dir) {
//        File cacheFile =  new File(Environment.getExternalStorageDirectory(), "/ClipImages/");
        File cacheFile =  new File(dir);
        if (!cacheFile.exists()) {
            if (!cacheFile.mkdirs()) {
                LogUtil.e("该文件路径不能正常创建" + cacheFile.getAbsolutePath());
            }
        }
        photoFilePath = cacheFile.getAbsolutePath();
        this.format= Bitmap.CompressFormat.JPEG;
        this.height = 340;
        this.width = 340;
        this.maxSize = 200;

    }

    public Bitmap saveAndPress(Bitmap bitmap,String fileName){
        // 获取扩展名
        String FileEnd = fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length()).toLowerCase();
        if (FileEnd.equals("png")){
            format= Bitmap.CompressFormat.PNG;
        }else {
            format= Bitmap.CompressFormat.JPEG;

        }
        String savePath = photoFilePath + "/" + fileName;
        return  CompressUtils.compressBitmap(bitmap, format, height, width, maxSize,savePath);
    }

}
