package com.zhanglin.win_learn;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
    private EditText editText_mainactivity_url;
    private Button button_mainactivity_goto;
    private WebView webView_mainactivity_mainweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_mainactivity_url = (EditText) findViewById(R.id.editText_mainActivity_url);
        button_mainactivity_goto = (Button) findViewById(R.id.button_mainActivity_goto);
        webView_mainactivity_mainweb = (WebView) findViewById(R.id.webView_mainActivity_mainweb);
        editText_mainactivity_url.setText("http://");
        button_mainactivity_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.zhanglin.win_learn.WebView.class);
                intent.putExtra("url", editText_mainactivity_url.getText().toString());
                MainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
