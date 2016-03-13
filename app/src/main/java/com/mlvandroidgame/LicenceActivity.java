package com.mlvandroidgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LicenceActivity extends Activity {

    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);

        TextView textViewResource = (TextView) findViewById(R.id.text_html_resource);
        textViewResource.setText(Html.fromHtml(getResources().getString(R.string.licence_text)));
        textViewResource.setMovementMethod(LinkMovementMethod.getInstance());

        this.next = (Button) findViewById(R.id.continueButton);
        this.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenceActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LicenceActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
