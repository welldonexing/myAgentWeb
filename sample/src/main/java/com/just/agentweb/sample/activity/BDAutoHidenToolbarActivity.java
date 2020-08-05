package com.just.agentweb.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.NestedScrollAgentWebView;
import com.just.agentweb.sample.R;

public class BDAutoHidenToolbarActivity extends AppCompatActivity implements View.OnClickListener {

    private AgentWeb mAgentWeb;
    private CoordinatorLayout main;
    private Toolbar toolbar;
    /**
     * 后退
     */
    private TextView btnBack;
    /**
     * 前进
     */
    private TextView btnForward;
    /**
     * 刷新
     */
    private TextView btnRefresh;
    /**
     * 菜单
     */
    private TextView btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bdactivity_auto_hiden_toolbar);

        initView();
        toolbar.setTitle("aabb");
        setSupportActionBar(toolbar);

        NestedScrollAgentWebView webView = new NestedScrollAgentWebView(this);

        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(-1, -1);
        lp.setBehavior(new AppBarLayout.ScrollingViewBehavior());

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(main, 1, lp)//lp记得设置behavior属性
                .useDefaultIndicator()
                .setWebView(webView)
                .createAgentWeb()
                .ready()
                .go("http://www.baidu.com");


    }

    private void initView() {
        main = findViewById(R.id.main);
       toolbar = findViewById(R.id.toolbar);
       // btnBack = (TextView) findViewById(R.id.btn_back);
        //btnForward = (TextView) findViewById(R.id.btn_forward);
        //btnRefresh = (TextView) findViewById(R.id.btn_refresh);
        //btnMenu = (TextView) findViewById(R.id.btn_menu);
       // btnBack.setOnClickListener(this);
       // btnForward.setOnClickListener(this);
       // btnRefresh.setOnClickListener(this);
       // btnMenu.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                if (mAgentWeb.getWebCreator().getWebView().canGoBack()) {
                    mAgentWeb.back();
                } else {
                    Toast.makeText(this, "无法后退", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.toolbar:
                super.onBackPressed();
                //return true;
                //mAgentWeb.getUrlLoader().loadUrl("http://www.baidu.com");
                Toast.makeText(this, "回主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_forward:
                if (mAgentWeb.getWebCreator().getWebView().canGoForward()) {
                    mAgentWeb.getWebCreator().getWebView().goForward();
                } else {
                    Toast.makeText(this, "无法前进", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_refresh:
                mAgentWeb.getWebCreator().getWebView().reload();
                break;
            default:
                Toast.makeText(this, "这是菜单选项", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //.直接获取按钮按下事件，直接重写Activity的onKeyDown方法即可，   不让back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Toast.makeText(this ,"点击头部返回主页", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
