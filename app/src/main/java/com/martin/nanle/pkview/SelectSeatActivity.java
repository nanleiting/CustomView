package com.martin.nanle.pkview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.martin.customview.SeatTable;

/**
 * 选座
 */
public class SelectSeatActivity extends AppCompatActivity {
    public SeatTable seatTableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (row == 0) {//设置第二排不可用
                    if (column == 5) {
                        return true;
                    } else {
                        return false;
                    }

                }
                if (column == 0) {//设置第二排不可用
                    if (row == 5) {
                        return true;
                    } else {
                        return false;
                    }

                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
//                if(row==6&&column==6){
//                    return true;
//                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(11, 11);
    }
}
