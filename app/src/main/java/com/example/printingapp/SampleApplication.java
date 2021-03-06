package com.example.printingapp;

import android.app.Application;
import android.os.Handler;

import java.io.File;

import es.voghdev.pdfviewpager.library.asset.CopyAsset;
import es.voghdev.pdfviewpager.library.asset.CopyAssetThreadImpl;

public class SampleApplication extends Application {
    final String[] sampleAssets = {};

    @Override
    public void onCreate() {
        super.onCreate();

        initSampleAssets();
    }

    private void initSampleAssets() {
        CopyAsset copyAsset = new CopyAssetThreadImpl(this, new Handler());
        for (String asset : sampleAssets) {
            copyAsset.copy(asset, new File(getCacheDir(), asset).getAbsolutePath());
        }
    }
}