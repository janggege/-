package shixun.lj.bw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shixun.lj.bw.Myview;
import shixun.lj.bw.R;
import shixun.lj.bw.data.Datas;

/*
  name:刘江
  data:2019
*/public class Myadapter extends RecyclerView.Adapter<Myadapter.Myviewholder> {
    List<Datas.ResultBean> list;
    Context context;

    Map<String, Boolean> map = new HashMap<>();

    //价格的map集合
    private Map<Integer, Integer> map2 = new HashMap<>();


    public Myadapter(List<Datas.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
        getChecked2(false);
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item, null, false);
        Myviewholder myviewholder = new Myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder myviewholder, final int i) {
        myviewholder.tv_title.setText(list.get(i).getCommodityName().substring(0, 3));
        Glide.with(context).load(list.get(i).getMasterPic()).into(myviewholder.iv);
        myviewholder.price.setText("￥" + list.get(i).getPrice());

        String id = list.get(i).getCommodityId() + "";
        myviewholder.cb.setChecked(map.get(id));
        myviewholder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //做价格的
                map2.clear();
                notifyDataSetChanged();
//                ————————————————————————————
                String idid = list.get(i).getCommodityId() + "";
                map.put(idid, myviewholder.cb.isChecked());
                boolean flag = true;
                for (String key : map.keySet()) {
                    Boolean aBoolean = map.get(key);
                    if (!aBoolean) {
                        flag = false;
                        listener.onCheck(flag);

                    }
                }
                if (flag) {
                    listener.onCheck(flag);
                }
            }
        });

        //刷新适配器
        myviewholder.myview.setOnclick(new Myview.onclick() {
            @Override
            public void inclick() {

                notifyDataSetChanged();
            }
        });
        if (myviewholder.cb.isChecked()) {
            EditText ed2 = myviewholder.myview.findViewById(R.id.ed);
            String s = ed2.getText().toString();
            int i1 = Integer.parseInt(s);
            int price = list.get(i).getPrice();
            map2.put(price, i1);
            getmoney();
        } else {
            getmoney();
        }


    }


    private void getmoney() {
        int money = 0;
        for (Integer key : map2.keySet()) {
            money += key * map2.get(key);
        }
        onmoney.onmoney(money);
        Log.i("money", money + "");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv_title;
        private CheckBox cb;
        private final TextView price;
        private final Myview myview;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            cb = itemView.findViewById(R.id.cb);
            price = itemView.findViewById(R.id.price);
            myview = itemView.findViewById(R.id.myline);

        }
    }


    public interface onmoney {
        void onmoney(int money);
    }

    onmoney onmoney;

    public void setOnmoney(Myadapter.onmoney onmoney) {
        this.onmoney = onmoney;
    }


    //定义CheckBox点击的接口回调


    public interface OnCheckListener {

        void onCheck(boolean isCheck);
    }

    private OnCheckListener listener;

    public void setOnCheckListener(OnCheckListener listener) {
        this.listener = listener;
    }

    //全选  反选
    public void checked1(boolean a) {
        map2.clear();
        getChecked2(a);
        notifyDataSetChanged();
    }

    public void getChecked2(boolean a) {
        map.clear();
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getCommodityId();
            map.put(id + "", a);
        }
    }

}
