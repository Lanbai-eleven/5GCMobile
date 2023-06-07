package com.example.a5gcmoble;

import com.example.a5gcmoble.SpeedTestActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TransPhoto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_connect:
                Intent mainAct = new Intent(TransPhoto.this, MainActivity.class);
                mainAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainAct);
                break;
            case R.id.nav_speedtest:
                Intent speedTest = new Intent(TransPhoto.this, SpeedTestActivity.class);
                speedTest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(speedTest);
                break;
            case R.id.nav_transphoto:
                Intent transPhoto = new Intent(TransPhoto.this, TransPhoto.class);
                transPhoto.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(transPhoto);
                break;
        }

        return true;
    }


    private static final int BACK_PRESS_DELAY = 2000; // 两次返回按钮的时间间隔
    private long backPressedTime; // 记录上一次按下返回按钮的时间

    @Override
    public void onBackPressed() {
        if (backPressedTime + BACK_PRESS_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity(); // 关闭所有Activity并退出应用程序
            return;
        } else {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}