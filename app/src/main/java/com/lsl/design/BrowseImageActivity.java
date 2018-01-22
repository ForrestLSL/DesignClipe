package com.lsl.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lsl.design.utils.LogUtil;
import com.lsl.design.view.HackyViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Forrest
 * on 2017/11/8 19:01
 */

public class BrowseImageActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    HackyViewPager mViewPager;

    private String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        filePath = getIntent().getStringExtra("path");
        LogUtil.i("传过来的文件路径： " + filePath);
        initView();

        List<String >  fileList = getImagePathFromSD(filePath);
        for (String str: fileList){
            LogUtil.i("图片文件路径： " + str);
        }

        if (!fileList.isEmpty()){
            MPagerAdapter mPagerAdapter = new MPagerAdapter(fileList);
            mViewPager.setAdapter(mPagerAdapter);

        }
    }

    private void initView(){
        ButterKnife.bind(this);
    }


    private class MPagerAdapter extends PagerAdapter {

        private List<String> filePath;

        public MPagerAdapter(List<String> filePath) {
            this.filePath = filePath;
        }

        @Override
        public int getCount() {
            return filePath.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            View view=getLayoutInflater().inflate(R.layout.item_show_image,null);
            final ImageView photoView= (ImageView) view.findViewById(R.id.photo_view);
            final String path = filePath.get(position);
            File file = new File(path);
            Glide.with(BrowseImageActivity.this).load(file).into(photoView);
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BrowseImageActivity.this, ClipActivity.class);
                    intent.putExtra("path", path);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//			PhotoView photoView = ((PhotoView)object);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }











    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD(String filePath) {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }


}
