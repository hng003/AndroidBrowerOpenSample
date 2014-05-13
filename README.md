AndroidBrowerOpenSample
=======================

AndroidアプリからBrowserアプリを起動し制御をAndroidアプリに戻すSample

# Server

## Summary
`/`にリクエストがあったらクエリパラメータ`callbackUrl`で指定されたUrlにリダイレクトする.

## Usage
```bash
node server/app.js
```

# Client

## Summary
`Open Browser`Button 押下時に `192.168.0.6:3000/`の`GET`リクエストをBrowserアプリで実行.

## Implement
### OpenBrowserActivity
`data`タグの`android:host`, `android:schema`で定義したURLにリダイレクトされた場合に後述する`onNewIntent`イベントが発火される.
このため、`android:schema`にはアプリ固有となるようなの値を定義する.
```xml
        <activity
            android:name=".OpenBrowserActivity"
            android:label="@string/title_activity_open_browse"
            android:launchMode="singleTask">

            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                        android:host="@string/callbackHost"
                        android:scheme="@string/callbackSchema" />

            </intent-filter>
```
### onClick event
`Open Browser`Button 押下時のEvent.
ここでBrowserでServerのエンドポイントをOpenするためのIntentをnewする.
```java
    public void openBrowser(View view) {
        Uri uri = Uri.parse("http://192.168.0.6:3000"
                + "?callbackUrl=" + mCallbackURL);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }
```
### onNewIntent event
```java
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
}*
```
