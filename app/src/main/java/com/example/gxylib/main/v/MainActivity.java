package com.example.gxylib.main.v;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.gxylib.R;
import com.example.gxylib.databinding.ActivityMainBinding;
import com.example.gxylib.main.vm.MainViewModel;
import com.srwing.b_applib.launch.GetGxyLauncher;
import com.srwing.gxylib.coreui.mvvm.BaseMvvmActivity;
import com.srwing.gxylib.coreui.view.badgevview.BadgeView;
import com.srwing.gxylib.timer.CutDownTimer;
import com.srwing.t_network.GxyNet;
import com.srwing.t_network.interceptors.LogInterceptor;
import com.srwing.t_network.utils.GxyLogger;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleLayout() {
        return R.layout.b_base_title_bar;
    }

    @Override
    protected void setTitleContent(View view) {
        // 添加标题的一些事件
        AppCompatTextView tv = view.findViewById(R.id.titleText);
        tv.setText("首页");
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        setTitle("首页");

        new BadgeView(this).bindTarget(dataBinding.line1).setBadgeNumber(6).setBadgeGravity(Gravity.TOP | Gravity.END);
//                .setGravityOffset(-6,true);


        GxyNet.init(this).withApiHost("https://server6.19x19.com")
                .withInterceptor(new LogInterceptor()).withLoggerAdapter() //设置LogAdapter
                .withDebugMode(true) //设置是否打印请求 日志
                .withNoProxy(false).configure(); //配置生效


        viewModel.getLiveData().observe(this, data -> {
            if (data == null) return;
            ToastUtils.showLong("aaaaa");

        });
        viewModel.getMain();


        CutDownTimer mSecondTimer = new CutDownTimer(this, 10000, 1000, 1000);
//        mSecondTimer.setOnCountDownTimerListener(new OnCountDownTimerListener() {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.e("TEST_TIMER", "onTick millisUntilFinished: " + millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.d("TEST_TIMER", "onFinish  ");
//
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("TEST_TIMER", "onCancel  ");
//
//            }
//        });
//        mSecondTimer.start();


        GetGxyLauncher gxyLauncher = new GetGxyLauncher(this);

        dataBinding.tvText.setText("开启2");
        dataBinding.tvText.setOnClickListener(v -> {
            Map<String, String> param = new HashMap<>();
            param.put("data", "来自于MainActivity");
            gxyLauncher.launch(MainActivity.this, TestActivity2.class, param, result -> {
                if (result.getData() != null) {
                    GxyLogger.i("Test-LAUNCHER", "code:" + result.getResultCode() + " ; data: " + result.getData().getStringExtra("data"));
                }
            });
        });

        dataBinding.tvText2.setText("开启3");
        dataBinding.tvText2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity3.class);
            intent.putExtra("data", "开启3");
            gxyLauncher.launch(
                    intent, result -> {
                        if (result.getData() != null) {
                            GxyLogger.i("Test-LAUNCHER", "code:" + result.getResultCode() + " ; data: " +
                                    result.getData().getStringExtra("data"));
                        }
                    }
            );
        });

    }
}