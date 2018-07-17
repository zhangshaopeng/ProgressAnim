package app.shao.com;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.shao.util.ToastUtil;
import app.shao.view.ProgressRoundView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressRoundView prView;
    private RelativeLayout rl_Query;
    private RelativeLayout rlResult;
    private TextView tvAgain;
    //进度条长度
    private String progressLength = "100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prView = findViewById(R.id.prView);

        tvAgain = findViewById(R.id.tv_again);
        rl_Query = findViewById(R.id.rl_Query);
        rlResult = findViewById(R.id.rl_Result);
        rlResult.setOnClickListener(this);
        rl_Query.setOnClickListener(this);
        tvAgain.setOnClickListener(this);
        //下划线和抗锯齿
        tvAgain.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvAgain.getPaint().setAntiAlias(true);
        //开启进度条
        startProgress(progressLength);
    }

    private void startProgress(String progressLength) {
        prView.setProgress(Integer.valueOf(progressLength));
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                prView.setView();
            }
        }, ProgressRoundView.MPROGRESSTIME);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_again:
                ToastUtil.show(this, "重新查询");
                prView.onAgine();
                startProgress("90");

                break;
            case R.id.rl_Query:
                ToastUtil.show(this, "人工查询");
                break;
            case R.id.rl_Result:
                ToastUtil.show(this, "查询结果");
                break;
            default:
                break;

        }
    }
}
