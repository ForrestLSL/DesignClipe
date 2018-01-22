package com.lsl.design;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener{

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @BindView(R.id.select_file)
    Button select_file;
    @BindView(R.id.start_browser)
    Button start_browser;
    @BindView(R.id.file_path)
    TextView file_path;

    public int PERMISSION_WRITE_READ = 10;
    public int REQUEST_CODE = 1;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //申请权限
        if (EasyPermissions.hasPermissions(MainActivity.this, permissions)){
            initView();
        }else {
            EasyPermissions.requestPermissions(this, "读取和写入需要权限",
                    PERMISSION_WRITE_READ, permissions);
        }

    }

    private void initView(){
        ButterKnife.bind(this);
        select_file.setOnClickListener(this);
        start_browser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_file:
                if (EasyPermissions.hasPermissions(MainActivity.this, permissions)) {
                    startActivityForResult(new Intent(MainActivity.this, SelectFileActivity.class),REQUEST_CODE);
                }else {
                    Toast.makeText(MainActivity.this,"您需要同意读写权限才可以进行操作",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.start_browser:
                if (TextUtils.isEmpty(mPath)){
                    Toast.makeText(MainActivity.this,"请先选择文件路径",Toast.LENGTH_LONG).show();
                }else {

                }

                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null){
            mPath = data.getStringExtra("path");
            if (!TextUtils.isEmpty(mPath)) {
                file_path.setText(mPath);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initView();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(MainActivity.this,"您需要同意读写权限才可以进行操作",Toast.LENGTH_LONG).show();
        initView();
    }


}
