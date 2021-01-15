package com.example.printingapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PdfScale;

public class PDFWithScaleActivity extends BaseSampleActivity {
    PDFViewPager pdfViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.menu_sample9_txt);
        pdfViewPager = new PDFViewPager(this, "moby.pdf");
        setContentView(pdfViewPager);
        pdfViewPager.setAdapter(new PDFPagerAdapter.Builder(this)
                .setPdfPath("moby.pdf")
                .setScale(getPdfScale())
                .setOnPageClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pdfViewPager.setVisibility(View.GONE);
                        Toast.makeText(PDFWithScaleActivity.this, R.string.page_was_clicked, Toast.LENGTH_LONG).show();
                    }
                })
                .create()
        );
    }

    private PdfScale getPdfScale() {
        PdfScale scale = new PdfScale();
        scale.setScale(3.0f);
        scale.setCenterX(getScreenWidth(this) / 2);
        scale.setCenterY(0f);
        return scale;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ((BasePDFPagerAdapter) pdfViewPager.getAdapter()).close();
    }

    public int getScreenWidth(Context ctx) {
        int w = 0;
        if (ctx instanceof Activity) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            w = displaymetrics.widthPixels;
        }
        return w;
    }
}