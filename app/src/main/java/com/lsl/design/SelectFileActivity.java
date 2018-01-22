package com.lsl.design;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lsl.design.adapter.FileAdapter;
import com.lsl.design.bean.FileInfo;
import com.lsl.design.comparator.FileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Forrest
 * on 2017/11/8 17:44
 */

public class SelectFileActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.file_list)
    ListView file_list;
    @BindView(R.id.path)
    TextView mPath;


    private String rootPath;
    private String fileClipePath;
    private List<FileInfo> fileInfo;
    private FileAdapter mFileAdapter;
    private FileComparator fileComparator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
        initView();
        rootPath = Environment.getExternalStorageDirectory().toString();
        getFileDir(rootPath);
    }

    private void initView(){
        ButterKnife.bind(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        fileComparator = new FileComparator();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
//                Intent intent = new Intent();
//                intent.putExtra("path", fileClipePath);
//                setResult(RESULT_OK,intent);
//                finish();
                Intent intent = new Intent(SelectFileActivity.this, BrowseImageActivity.class);
                intent.putExtra("path", fileClipePath);
                startActivity(intent);
                break;
            case R.id.cancel:
                finish();
                break;

        }
    }


    private void getFileDir(final String filePath){

        File f=new File(filePath);
        if(f.exists() && f.canWrite()){
            mPath.setText(filePath);
            this.fileClipePath = filePath;
            fileInfo = new ArrayList<>();
            File[] files=f.listFiles();
            if(!filePath.equals(rootPath)){
                fileInfo.add(new FileInfo("CustomParent",f.getParent(),System.currentTimeMillis()));
                fileInfo.add(new FileInfo("CustomRoot",rootPath,System.currentTimeMillis()));
            }
            for(int i=0;i<files.length;i++){
                File file=files[i];
                if(file.isDirectory()){
                    fileInfo.add(new FileInfo(file.getName(),file.getPath(),file.lastModified()));
                }
            }
            Collections.sort(fileInfo, fileComparator);
            mFileAdapter = new FileAdapter(this,fileInfo);
            file_list.setAdapter(mFileAdapter);
            file_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                    if(fileInfo.get(position).getFileName().equals("CustomParent")){
                        getFileDir(fileInfo.get(position).getFilePath());
                    }else if(fileInfo.get(position).getFileName().equals("CustomRoot")){
                        getFileDir(fileInfo.get(position).getFilePath());
                        return;
                    }else{
                        File file=new File(fileInfo.get(position).getFilePath());
                        if(file.canWrite()){
                            if (file.isDirectory()){
                                getFileDir(fileInfo.get(position).getFilePath());
                            }
                        }
                    }
                }
            });
        }
    }
}
