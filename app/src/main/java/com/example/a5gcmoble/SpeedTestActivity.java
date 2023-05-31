package com.example.a5gcmoble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpeedTestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /**
         * 实现侧边菜单栏
         */
        setSupportActionBar(toolbar);

        navigationView.bringToFront();  //触发点击效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();  //同步drawerlayout的状态

        /**
         * 实现侧边菜单栏的点击事件
         */
        navigationView.setNavigationItemSelectedListener(this);  //设置点击监听事件

        //button 点击事件
        Button button = findViewById(R.id.speed_test_button);
        button.setOnClickListener(v -> {
            speedTest();
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_connect:
                Intent intent = new Intent(SpeedTestActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_speedtest:
                Intent speedTest = new Intent(SpeedTestActivity.this, SpeedTestActivity.class);
                startActivity(speedTest);
                break;

        }

        return true;
    }

    private void speedTest() {
        //use okhttp to test current network speed
        final TextView resultTextView = findViewById(R.id.show_result_text_view);
        EditText urlEditText = findViewById(R.id.url_edit_text);
        String url = urlEditText.getText().toString();
        if(!url.startsWith("http://")) url = "https://" + url;

        String testUrl = url;
        new Thread(() -> {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS) // 设置连接超时时间为10秒
                        .readTimeout(10, TimeUnit.SECONDS) // 设置读取超时时间为10秒
                        .build();
                Request request = new Request.Builder()
                        .url(testUrl)
                        .build();
                try {
                    long startTime = System.currentTimeMillis(); // 记录开始时间
                    Response response = client.newCall(request).execute(); // 发送请求
                    long endTime = System.currentTimeMillis(); // 记录结束时间
                    final long responseTime = endTime - startTime; // 计算响应时间

                    // 将结果发送给主线程更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTextView.setText("响应时间为：" + responseTime + "ms");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }).start();
    }
}
