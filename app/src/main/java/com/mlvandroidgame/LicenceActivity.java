package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class LicenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        TextView textViewResource = (TextView) findViewById(R.id.text_html_resource);
        textViewResource.setText(Html.fromHtml(getResources().getString(R.string.licence_text)));
        textViewResource.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LicenceActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
