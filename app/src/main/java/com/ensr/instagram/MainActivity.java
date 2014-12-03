package com.ensr.instagram;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.ensr.instagram.tabs.Downloaded;
import com.ensr.instagram.tabs.Export;
import com.ensr.instagram.tabs.Url;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;



public class MainActivity extends TabActivity {

    TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = getTabHost();

        TabHost.TabSpec downloadedSpec = mTabHost.newTabSpec("downloaded");
        downloadedSpec.setIndicator("Download", null);
        Intent downloadedIntent = new Intent(this, Downloaded.class);
        downloadedSpec.setContent(downloadedIntent);

        TabHost.TabSpec exportSpec = mTabHost.newTabSpec("export");
        exportSpec.setIndicator("Export", null);
        Intent exportIntent = new Intent(this, Export.class);
        exportSpec.setContent(exportIntent);

        TabHost.TabSpec urlSpec = mTabHost.newTabSpec("url");
        urlSpec.setIndicator("Url", null);
        Intent urlIntent = new Intent(this, Url.class);
        urlSpec.setContent(urlIntent);

        mTabHost.addTab(exportSpec);
        mTabHost.addTab(urlSpec);
        mTabHost.addTab(downloadedSpec);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForCrashes();
        checkForUpdates();
    }

    private void checkForCrashes() {
        CrashManager.register(this, "13a21d418b35141d967f124de71c2ce1");
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this, "13a21d418b35141d967f124de71c2ce1");
    }

}