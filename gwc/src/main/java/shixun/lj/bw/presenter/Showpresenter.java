package shixun.lj.bw.presenter;

import shixun.lj.bw.data.Datas;
import shixun.lj.bw.model.Showmodel;
import shixun.lj.bw.view.Showview;

/*
  name:刘江
  data:2019
*/public class Showpresenter {

    private final Showmodel showmodel;
    private final Showview showview1;

    public Showpresenter(Showview showview) {
        showmodel = new Showmodel();
        showview1 = showview;

    }

    public void showp() {
        showmodel.showmodel();
        showmodel.setOnclick(new Showmodel.onclick() {
            @Override
            public void onclick(Datas datas) {
                showview1.Show(datas);
            }
        });
    }
}
