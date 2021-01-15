package com.example.printingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.asset.CopyAsset;
import es.voghdev.pdfviewpager.library.asset.CopyAssetThreadImpl;

public class AssetOnSDActivity extends BaseSampleActivity {
    final String[] sampleAssets = {"adobe.pdf", "sample.pdf"};

    PDFViewPager pdfViewPager;
    File pdfFolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.asset_on_sd);
        setContentView(R.layout.activity_asset_on_sd);

        pdfFolder = Environment.getExternalStorageDirectory();

        copyAssetsOnSDCard();
    }

    protected void copyAssetsOnSDCard() {
        final Context context = this;
        CopyAsset copyAsset = new CopyAssetThreadImpl(getApplicationContext(), new Handler(), new CopyAsset.Listener() {
            @Override
            public void success(String assetName, String destinationPath) {
                pdfViewPager = new PDFViewPager(context, getPdfPathOnSDCard());
                setContentView(pdfViewPager);
            }

            @Override
            public void failure(Exception e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        for (String asset : sampleAssets) {
            copyAsset.copy(asset, new File(pdfFolder, asset).getAbsolutePath());
        }
    }

    protected String getPdfPathOnSDCard() {
        File f = new File(pdfFolder, "adobe.pdf");
        return f.getAbsolutePath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (pdfViewPager != null) {
            ((BasePDFPagerAdapter) pdfViewPager.getAdapter()).close();
        }
    }
}