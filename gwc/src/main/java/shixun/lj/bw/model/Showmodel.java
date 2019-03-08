package shixun.lj.bw.model;

import android.os.Handler;
import android.os.Message;
import android.widget.ScrollView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import shixun.lj.bw.data.Datas;
import shixun.lj.bw.utils.OkHttpUtils;

/*
  name:刘江
  data:2019
*/public class Showmodel {

    public static interface onclick {
        void onclick(Datas datas);
    }

    onclick onclick;

    public void setOnclick(Showmodel.onclick onclick) {
        this.onclick = onclick;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String data = (String) msg.obj;
                    Gson gson = new Gson();
                    Datas datas = gson.fromJson(data, Datas.class);
                    onclick.onclick(datas);
                    break;

            }

        }
    };


    public void showmodel() {
        String url = "http://172.17.8.100/small/commodity/v1/findCommodityByKeyword?keyword=高跟鞋&page=1&count=7";
        OkHttpUtils.getinstance().doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = string;
                handler.sendMessage(message);


            }
        });
    }
}
