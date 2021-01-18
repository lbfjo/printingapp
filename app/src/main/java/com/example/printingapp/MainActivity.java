package com.example.printingapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;

import static com.example.printingapp.FileContent.loadSavedFiles;

public class MainActivity extends BaseSampleActivity implements ItemFragment.OnListFragmentInteractionListener {
    BasePDFPagerAdapter adapter;
    private BaseSampleActivity context;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    File pdfFolder;

    final int REQUEST_CODE = 1;

    boolean haveData = false;
    Runnable dataThread;
    Uri data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.std_example);
        setContentView(R.layout.activity_main);
        context = this;

        dataThread = new Runnable() {
            @Override
            public void run() {
                try{
                    Intent intent = getIntent();
                    data = intent.getData();
                    if(data != null){
                        haveData = true;
                    }
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        };
        if (recyclerViewAdapter == null) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
            recyclerView = (RecyclerView) currentFragment.getView();
            recyclerViewAdapter = ((RecyclerView) currentFragment.getView()).getAdapter();
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pdfFolder = new File(Environment.getExternalStorageDirectory(),"teste");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadSavedFiles(pdfFolder);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sample2) {
            launchActivity(RemotePDFActivity.class);
            return false;
        } else if (id == R.id.action_sample3) {
            requestPermissionsThenOpen(AssetOnSDActivity.class);
            return false;
        } else if (id == R.id.action_sample4) {
            Toast.makeText(this, R.string.dummy_msg, Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_sample5) {
            requestPermissionsThenOpen(AssetOnXMLActivity.class);
        } else if (id == R.id.action_sample8) {
            requestPermissionsThenOpen(ZoomablePDFActivityPhotoView.class);
        } else if (id == R.id.action_sample9) {
            launchActivity(PDFWithScaleActivity.class);
        } else if (id == R.id.action_sample10) {
            launchActivity(InvalidPdfActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean hasExternalStoragePermissions() {
        boolean hasReadPermission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        boolean hasWritePermission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        return hasReadPermission && hasWritePermission;
    }

    protected void requestPermissionsThenOpen(Class activityClass) {
        if (hasExternalStoragePermissions()) {
            launchActivity(activityClass);
        } else {
            requestExternalStoragePermissions();
        }
    }

    protected void requestExternalStoragePermissions() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    protected void launchActivity(Class activityClass) {
        Intent i = new Intent(this, activityClass);
        startActivity(i);
    }


    @Override
    public void onListFragmentInteraction(FileItem item) {

    }
}