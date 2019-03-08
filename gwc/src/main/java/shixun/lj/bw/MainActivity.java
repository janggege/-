package shixun.lj.bw;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayoutDirection;

import java.util.List;

import shixun.lj.bw.adapter.Myadapter;
import shixun.lj.bw.data.Datas;
import shixun.lj.bw.presenter.Showpresenter;
import shixun.lj.bw.view.Showview;

public class MainActivity extends AppCompatActivity implements Showview {
    private RecyclerView rv;
    private SwipyRefreshLayout srl;
    private CheckBox checkbox;
    private Myadapter myadapter;
    private Handler handler = new Handler();
    private TextView textView;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        rv = findViewById(R.id.rv);
        srl = findViewById(R.id.srl);
        checkbox = findViewById(R.id.checkbox);
        textView = findViewById(R.id.tv_num);
        textView1 = findViewById(R.id.tv_price);
        //全选  反选
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    myadapter.checked1(true);
                } else {
                    myadapter.checked1(false);
                }

            }
        });
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        srl.setDirection(SwipyRefreshLayoutDirection.BOTH);
        srl.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        srl.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkbox.setChecked(false);
                        Showpresenter showpresenter = new Showpresenter(MainActivity.this);
                        showpresenter.showp();
                        srl.setRefreshing(false);
                    }
                }, 2000);

            }

            @Override
            public void onLoad(int index) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkbox.setChecked(false);
                        Showpresenter showpresenter = new Showpresenter(MainActivity.this);
                        showpresenter.showp();
                        srl.setRefreshing(false);

                    }
                }, 2000);

            }
        });
        Showpresenter showpresenter = new Showpresenter(this);
        showpresenter.showp();

    }


    @Override
    public void Show(Datas datas) {
        List<Datas.ResultBean> result = datas.getResult();
        myadapter = new Myadapter(result, MainActivity.this);
        rv.setAdapter(myadapter);
        myadapter.setOnCheckListener(new Myadapter.OnCheckListener() {
            @Override
            public void onCheck(boolean isCheck) {
                checkbox.setChecked(isCheck);
            }
        });
        //价钱回调
        myadapter.setOnmoney(new Myadapter.onmoney() {
            @Override
            public void onmoney(int money) {
                textView1.setText(money + "");

            }
        });

    }


}
