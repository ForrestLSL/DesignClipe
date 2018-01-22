package com.lsl.design.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lsl.design.R;
import com.lsl.design.bean.FileInfo;

import java.io.File;
import java.util.List;

/**
 * Created by Forrest
 * on 2017/11/8 17:56
 */

public class FileAdapter  extends BaseAdapter{

    private Context mContext;
    private List<FileInfo> fileInfo;

    public FileAdapter(Context mContext, List<FileInfo> fileInfo) {
        this.mContext = mContext;
        this.fileInfo = fileInfo;
    }


    @Override
    public int getCount() {
        return fileInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_file, null);
            holder.file_name = (TextView) convertView.findViewById(R.id.file_name);
            holder.file_path = (TextView) convertView.findViewById(R.id.file_path);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        File f = new File(fileInfo.get(position).getFilePath().toLowerCase());
        if(fileInfo.get(position).getFileName().equals("CustomRoot")){
            holder.file_name.setText("返回根目录");
        }else if(fileInfo.get(position).getFileName().equals("CustomParent")){
            holder.file_name.setText("返回上一级");
        }else{

            holder.file_path.setText(fileInfo.get(position).getFilePath());
            if(f.isDirectory()){
                holder.file_name.setText(f.getName()+"  文件夹");
            }else{
                holder.file_name.setText(f.getName());
            }
        }

        return convertView;
    }

    class ViewHolder{
        TextView file_name;
        TextView file_path;
    }
}
