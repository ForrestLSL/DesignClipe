package com.lsl.design;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lsl.design.utils.LogUtil;
import com.lsl.design.utils.PhotoPathUtil;
import com.lsl.design.view.ClipImageLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Forrest
 * on 2017/11/8 20:15
 */

public class ClipActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.clip_image_layout)
    ClipImageLayout clip_image_layout;
    @BindView(R.id.clip)
    Button clip;
    private String imagePath;
    private String fileName;
    private PhotoPathUtil photoPathUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        ButterKnife.bind(this);

        imagePath = getIntent().getStringExtra("path");
//         /storage/emulated/0/佳丽/8号ArinaBB （李娜）/-008-1.jpg
        String [] args = imagePath.split("/");

        fileName = args[args.length-2];
        LogUtil.i("裁剪图片名称：" + fileName);

        //修改图片名称  -008-1.jpg
//        String []fileStart = fileName.split("-");
//        LogUtil.i("1: "+fileStart[0]);
//        LogUtil.i("2: "+ fileStart[1]);
        // 获取扩展名
        String photoName = args[args.length -1];
        String fileEnd = photoName.substring(photoName.lastIndexOf(".") + 1,
                photoName.length()).toLowerCase();
        LogUtil.i("拓展名为： " + fileEnd);

        fileName = fileName + "." + fileEnd;

        File file = new File(imagePath);
        Glide.with(ClipActivity.this).load(file).into(clip_image_layout.getmZoomImageView());
        clip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clip:
                String dir = imagePath.substring(0, imagePath.lastIndexOf("/"));
                LogUtil.i("文件路径：" + dir);
                photoPathUtil = new PhotoPathUtil(dir);
                Bitmap bitmap =clip_image_layout.clip();
                if (photoPathUtil.saveAndPress(bitmap, fileName) != null){
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(dir + "/" + fileName))));
                    Toast.makeText(ClipActivity.this,"裁剪成功",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}
