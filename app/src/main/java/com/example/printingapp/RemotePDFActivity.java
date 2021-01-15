package com.example.printingapp;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class RemotePDFActivity extends BaseSampleActivity implements DownloadFile.Listener {
    LinearLayout root;
    RemotePDFViewPager remotePDFViewPager;
    EditText etPdfUrl;
    Button btnDownload;
    PDFPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.remote_pdf_example);
        setContentView(R.layout.activity_remote_pdf);

        root = findViewById(R.id.remote_pdf_root);
        etPdfUrl = findViewById(R.id.et_pdfUrl);
        btnDownload = findViewById(R.id.btn_download);

        setDownloadButtonListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    protected void setDownloadButtonListener() {
        final Context ctx = this;
        final DownloadFile.Listener listener = this;
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remotePDFViewPager = new RemotePDFViewPager(ctx, getUrlFromEditText(), listener);
                remotePDFViewPager.setId(R.id.pdfViewPager);
                hideDownloadButton();
            }
        });
    }

    protected String getUrlFromEditText() {
        return etPdfUrl.getText().toString().trim();
    }

    public void showDownloadButton() {
        btnDownload.setVisibility(View.VISIBLE);
    }

    public void hideDownloadButton() {
        btnDownload.setVisibility(View.INVISIBLE);
    }

    public void updateLayout() {
        root.removeAllViewsInLayout();
        root.addView(etPdfUrl,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        root.addView(btnDownload,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
        showDownloadButton();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        showDownloadButton();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}