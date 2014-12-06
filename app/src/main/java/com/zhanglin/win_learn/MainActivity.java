package com.zhanglin.win_learn;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanglin.util.UploadPostUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_id;
    private EditText editText_path;
    private TextView textView_getReturnInfo;
    private Button button_commit;
    private String returnString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_id = (EditText)findViewById(R.id.editText_mainActivity_id);
        editText_username = (EditText)findViewById(R.id.editText_mainActivity_url);
        editText_password = (EditText)findViewById(R.id.editText_mainActivity_password);
        editText_path = (EditText)findViewById(R.id.editText_mainActivity_path);
        textView_getReturnInfo = (TextView)findViewById(R.id.textView_mainActivity_getReturnInfo);
        button_commit = (Button)findViewById(R.id.button_mainActivity_goto);
        editText_path.setText("/sdcard" + File.separatorChar + "test");

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        textView_getReturnInfo.setText(returnString);
                        Toast.makeText(getApplicationContext(),"上传完成！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }
        };
        button_commit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username;
                String password;
                String path;
                int id;
                path = editText_path.getText().toString();
                username = editText_username.getText().toString();
                password = editText_password.getText().toString();
                id = Integer.parseInt(editText_id.getText().toString());
                new UploadThread(username, password, path , id, handler).start();
            }
        });
    }

    /**
     * 这个类的作用是实现上传功能的
     * \n ui线程里面是不让联网的，另外开个线程类联网
     */
    private class UploadThread extends Thread {
        private String username;
        private String password;
        private String path;
        private int id;
        private Handler handler;

        /**
         * 构造函数
         * @param username 用户名
         * @param password 密码
         * @param path 要上传的文件路径，可以是文件夹也可以是文件
         * @param id 上传的id
         * @param handler ui传进来的handler
         */
        public UploadThread(String username, String password, String path, int id, Handler handler) {
            this.username = username;
            this.password = password;
            this.path = path;
            this.id = id;
            this.handler = handler;
        }

        @Override
        public void run() {
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("id", id + "");
            File dir = new File(path);
            File files[] = dir.listFiles();
            String urlPath = "http://192.168.0.142:33333/upload.action";
            UploadPostUtil uploadPostUtil = new UploadPostUtil(urlPath, params, files);
            try {
                InputStream returnInputStream = uploadPostUtil.uploadByPost();
                ByteArrayOutputStream write = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while((len = returnInputStream.read(buffer))!= -1) {
                    write.write(buffer, 0, len);
                }
                returnString = new String(write.toByteArray());
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        }
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
