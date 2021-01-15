package com.example.printingapp;

import com.example.printingapp.FileItem;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileContent {
    static final List<FileItem> ITEMS = new ArrayList<>();

    public static void loadSavedFiles(File dir) {
        ITEMS.clear();
        if (dir.exists()) {
            File[] files = dir.listFiles();
            Log.i("teste","inside saved Files" + files );
            for (File file : files) {
                String absolutePath = file.getAbsolutePath();
                String extension = absolutePath.substring(absolutePath.lastIndexOf("."));
                if (extension.equals(".pdf")) {
                    loadFile(file);
                }
            }
        }
    }



    private static String getDateFromUri(Uri uri) {
        String[] split = uri.getPath().split("/");
        String fileName = split[split.length - 1];
        String fileNameNoExt = fileName.split("\\.")[0];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(new Date(Long.parseLong(fileNameNoExt)));
        return dateString;
    }

    public static void loadFile(File file) {
        FileItem newItem = new FileItem();
        newItem.uri = Uri.fromFile(file);
        newItem.name = file.getName();
        //newItem.date = getDateFromUri(newItem.uri);
        addItem(newItem);
    }

    private static void addItem(FileItem item) {
        ITEMS.add(0, item);
    }
}