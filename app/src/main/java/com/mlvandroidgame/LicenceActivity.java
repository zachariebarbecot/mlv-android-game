package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LicenceActivity extends Activity {

    private Button enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_licence);

        TextView textViewResource = (TextView) findViewById(R.id.text_html_resource);
        textViewResource.setText(Html.fromHtml(getResources().getString(R.string.licence_text)));
        textViewResource.setMovementMethod(LinkMovementMethod.getInstance());

        this.enabled = (Button) findViewById(R.id.enabled);
        this.enabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(LicenceActivity.this, ShareActivity.class);
                startActivity(i);
                finish();
                LicenceActivity.this.finish();
            }
        });
    }
}
