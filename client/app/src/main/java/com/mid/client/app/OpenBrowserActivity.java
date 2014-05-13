package com.mid.client.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class OpenBrowserActivity extends Activity {

    private static final String TAG = OpenBrowserActivity.class.getName();
    private String mCallbackURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        mCallbackURL = getString(R.string.callbackSchema)
                + "://" + getString(R.string.callbackHost);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.oauth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openBrowser(View view) {
        Uri uri = Uri.parse("http://192.168.0.6:3000"
                + "?callbackUrl=" + mCallbackURL);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent == null
                || intent.getData() == null
                || !intent.getData().toString().startsWith(mCallbackURL)) {
            // 関係ないUrlの場合は何もしない
            return;
        }
        // 自分のアプリ用のURLの場合は処理を実行
        Log.i(TAG, "--------------------" + intent.getDataString());

        String text = "You came back to application!!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();

        return;
    }
}
