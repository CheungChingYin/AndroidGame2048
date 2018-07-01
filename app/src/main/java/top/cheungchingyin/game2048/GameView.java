package top.cheungchingyin.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    int cardWidth = getCardWidth();
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<Point>();

    public GameView(Context context) {
        super(context);
        initGameView();
        addCards(cardWidth,cardWidth);
        startGame();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
        addCards(cardWidth,cardWidth);
        startGame();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
        addCards(cardWidth,cardWidth);
        startGame();
    }

    private void startGame(){
        for (int y = 0;y < 4;y++){
            for (int x = 0; x < 4;x++){
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();//一开始需要添加2个随机数
        addRandomNum();
    }

    private void initGameView(){
        setColumnCount(4);//设置每行四列
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {

            private float startX;
            private float startY;
            private float offsetX;
            private float offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)){
                            /*
                            由于手指在滑动的时候可能本来是想向垂直方向滑动，但是还是会有一点水平方向滑动，为了防止
                            误判，所以使用绝对值判断是垂直方向的滑动幅度大还是水平方向的大，以判断是在垂直方向滑动
                            还是水平方向滑动
                             */
                            if (offsetX < -5){
                                swipeLeft();
                            }else if (offsetX >5){
                                swipeRight();
                            }
                        }else if (Math.abs(offsetX) < Math.abs(offsetY)){
                            if (offsetY < -5){
                                swipeUp();
                            }else if (offsetY > 5){
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }

   private int getCardWidth(){//获得屏幕宽度
       DisplayMetrics displayMetrics;
       displayMetrics = getResources().getDisplayMetrics();

       int cardWidth = displayMetrics.widthPixels;
       return (cardWidth - 10) /4;

   }

    private void addCards(int cardWidth, int cardHeight) {
    //添加16张卡片
        Card card;
        for (int y = 0;y < 4 ;y++){
            for (int x = 0;x < 4; x++){
                card = new Card(getContext());
                card.setNum(0);
                addView(card,cardWidth,cardHeight);
                cardsMap[x][y] = card;
            }
        }
    }

    private void swipeLeft(){
        boolean merge = false;
        for (int y = 0; y < 4;y++){
            for (int x = 0;x < 4;x++){
                for (int x1 = x+1;x1 < 4;x1++){//遍历当前行
                    if (cardsMap[x1][y].getNum() > 0){//如果当前格x,y的右边有不为0的格
                        if (cardsMap[x][y].getNum() <= 0){//且当前格为0
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());//当前格被右边的格补位，即右边的数往左移动
                            cardsMap[x1][y].setNum(0);
                            x--;//重新遍历一次如果第一次的时候有相同合并后需要再次靠左合并
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){//如果相邻的两个格的值相等
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean merge = false;
        for (int y = 0; y < 4;y++){
            for (int x = 3;x >= 0;x--){
                for (int x1 = x-1;x1 >= 0;x1--){
                    if (cardsMap[x1][y].getNum() > 0){
                        if (cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){
        boolean merge = false;
        for (int x = 0; x < 4;x++){
            for (int y = 0;y < 4;y++){
                for (int y1 = y+1;y1 < 4;y1++){
                    if (cardsMap[x][y1].getNum() > 0){
                        if (cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){
        boolean merge = false;
        for (int x = 0; x < 4;x++){
            for (int y = 3;y >= 0;y--){
                for (int y1 = y-1;y1 >= 0;y1--){
                    if (cardsMap[x][y1].getNum() > 0){
                        if (cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){//如果相邻的两个格的值相等
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void addRandomNum(){
        emptyPoint.clear();
        for (int y = 0;y < 4;y++){
            for (int x = 0;x < 4;x++){
                if (cardsMap[x][y].getNum() <= 0){
                    emptyPoint.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoint.remove((int)(Math.random() * emptyPoint.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1 ? 2:4);
    }
    private void checkComplete(){
        boolean complete = true;
        ALL:
        for (int y = 0;y < 4;y++){
            for (int x = 0;x < 4;x++){
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||//x>0用意是为了检测当前格是否靠边，靠边再-1就数组越界
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||//靠右
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||//靠顶部
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y+1]))){//靠底部
                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("您好，").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.getMainActivity().clearScore();
                    startGame();
                }
            }).show();
        }
    }


}
