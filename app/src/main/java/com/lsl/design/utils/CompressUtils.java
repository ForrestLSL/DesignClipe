package com.lsl.design.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Forrest
 * on 2016/11/17 11:57
 * 图片压缩工具类
 */

public class CompressUtils {

    /**
     *  进行质量压缩
     * @param image  传入Bitmap
     * @param format 格式化类型 JPEG、PNG、WEBP
     * @param size 控制压缩的大小100表示100kb
     * @param savePath 存储路径
     * @return 返回bitmap
     */
    private static Bitmap compressBitmap(Bitmap image, Bitmap.CompressFormat format, int size, String savePath){
        if (image==null){
            return null;
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //100表示不进行质量压缩
        int options=100;
        image.compress(format,100,baos);
        //如果大于要求的压缩大小，就继续进行质量压缩
        while (baos.toByteArray().length/1024>size){
            baos.reset();
            options-=10;
            if (options>0) {
                image.compress(format, options, baos);

            }

        }
        //图片太大即使options=10也无法压缩到指定的大小
        if (baos.toByteArray().length<1){
            image.compress(format, 10, baos);
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        ByteArrayInputStream bIS=new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap= BitmapFactory.decodeStream(bIS,null,newOpts);
        //以流的形式存储，不转成Bitmap存储，因为存储成bitmap的形式会占用更高的存储空间
//        saveBitmap(savePath,bitmap, Bitmap.CompressFormat.JPEG);
        saveBitmap(savePath,baos.toByteArray());
        return bitmap;
    }

    /***
     * 得到图片地址然后进行比例压缩
     * @param srcPath 图片地址
     * @param format 图片格式化
     * @param height 压缩预期高度
     * @param width 压缩预期宽度
     * @param maxSize 压缩预期大小 100表示压缩为100kb
     * @param savePath 存储路径
     * @return bitmap类型
     */
    public static Bitmap getImage(String srcPath, Bitmap.CompressFormat format, float height, float width, int maxSize, String savePath){
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
//        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 想要缩放的目标尺寸
        float hh = height;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = width;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        return compressBitmap(bitmap,format,maxSize,savePath);

    }

    /**
     * 得到图片进行比例压缩
     * @param image 传入的Bitmap
     * @param format 图片格式化
     * @param height 压缩预期高度
     * @param width 压缩预期宽度
     * @param maxSize 压缩预期大小 100表示压缩为100kb
     * @return bitmap类型
     */
    public static Bitmap compressBitmap(Bitmap image , Bitmap.CompressFormat format, int height, int width, int maxSize, String savePath){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        if (baos.toByteArray().length / 1024 > maxSize) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为

        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / height);
        }else {
            be = (int) (newOpts.outWidth / width);
        }
        if (be <= 0) {
            be = 1;
        }
        LogUtil.i("压缩的比例be： " + be);
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressBitmap(bitmap,format,maxSize,savePath);//压缩好比例大小后再进行质量压缩




    }

    public static boolean saveBitmap(File file, Bitmap bm, Bitmap.CompressFormat format)
    {
        String path = file.getParent();
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File filePath = new File(path);
        if (bm == null) {
            return false;
        }
        try
        {
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(format, 100, fos);
            fos.flush();
            fos.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveBitmap(String filePath, Bitmap bm, Bitmap.CompressFormat format)
    {
        File file = new File(filePath);
        return saveBitmap(file, bm, format);
    }

    public static boolean saveBitmap(File file, byte[] bs)
    {
        String path = file.getParent();
        File filePath = new File(path);
        if ((bs == null) || (bs.length == 0)) {
            return false;
        }
        try
        {
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bs);
            fos.flush();
            fos.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveBitmap(String filePath, byte[] bs)
    {
        File file = new File(filePath);
        return saveBitmap(file, bs);
    }

}
