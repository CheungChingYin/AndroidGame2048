package top.cheungchingyin.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

    private TextView label;
    private int num = 0;

    public Card(@NonNull Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);//设置卡牌填充满
        lp.setMargins(10,10,0,0);//每一个小方块间距
        addView(label,lp);//添加副集容器
        setNum(9);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        switch (num) {
            case 0:
                label.setBackgroundColor(0x33ffffff);
                break;
            case 2:
                label.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                label.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                label.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                label.setBackgroundColor(0xfff59563);
                break;
            case 32:
                label.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                label.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                label.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                label.setBackgroundColor(0xffedcc61);
                break;
            case 512:
                label.setBackgroundColor(0xffedc850);
                break;
            case 1024:
                label.setBackgroundColor(0xffedc53f);
                break;
            case 2048:
                label.setBackgroundColor(0xffedc22e);
                break;
            default:
                label.setBackgroundColor(0xff3c3a32);
                break;
        }
        if (num <= 0){
            label.setText("");
        }else {
            label.setText(num+"");
        }
    }

    public boolean equals(Card card) {
        return getNum() == card.getNum();
    }
}
