package yaksok.dodream.com.yaksok_refactoring.Adapter.MyPill;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.SearchPill.PillSearchItem;
import yaksok.dodream.com.yaksok_refactoring.R;

public class MypillListAdapter extends BaseAdapter {
    private ArrayList<MyPillItem> myPillItems = new ArrayList<MyPillItem>() ;

    // ListViewAdapter의 생성자
    public MypillListAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return myPillItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_pill_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tv_name = (TextView)convertView.findViewById(R.id.tv_name_mypill_item);
        TextView tv_TOD = (TextView)convertView.findViewById(R.id.tv_timeOfDay_mypill_item);
        ImageView iv_point1 = (ImageView)convertView.findViewById(R.id.iv_point1_mypill);
        ImageView iv_point2 = (ImageView)convertView.findViewById(R.id.iv_point2_mypill);
        ImageView iv_point3 = (ImageView)convertView.findViewById(R.id.iv_point3_mypill);
        TextView tv_family = (TextView)convertView.findViewById(R.id.tv_family_mypill_item);
        TextView tv_dosagi = (TextView)convertView.findViewById(R.id.tv_dosagi_mypill_item);



        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MyPillItem listItem = myPillItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tv_name.setText(listItem.getName());
        tv_TOD.setText(String.valueOf(listItem.getTime_of_day())+" 번");
        tv_family.setText(listItem.getFamily());
        tv_dosagi.setText(String.valueOf(listItem.getDosagi()));

        if(position % 2 == 0) {
            if (listItem.getTime_of_day() == 1) {
                tv_name.setTextColor(Color.parseColor("#00B49D"));
                iv_point1.setImageResource(R.drawable.mypii_point);
                iv_point2.setVisibility(View.INVISIBLE);
                iv_point3.setVisibility(View.INVISIBLE);
            } else if (listItem.getTime_of_day() == 2) {
                iv_point1.setImageResource(R.drawable.mypii_point);
                iv_point2.setImageResource(R.drawable.mypii_point);
                iv_point3.setVisibility(View.INVISIBLE);
            } else if (listItem.getTime_of_day() == 3) {
                iv_point1.setImageResource(R.drawable.mypii_point);
                iv_point2.setImageResource(R.drawable.mypii_point);
                iv_point3.setImageResource(R.drawable.mypii_point);
            }
        }
        else{
            tv_name.setTextColor(Color.parseColor("#FF64A9"));
            if (listItem.getTime_of_day() == 1) {
                iv_point1.setImageResource(R.drawable.mypii_point_pink);
                iv_point2.setVisibility(View.INVISIBLE);
                iv_point3.setVisibility(View.INVISIBLE);
            } else if (listItem.getTime_of_day() == 2) {
                iv_point1.setImageResource(R.drawable.mypii_point_pink);
                iv_point2.setImageResource(R.drawable.mypii_point_pink);
                iv_point3.setVisibility(View.INVISIBLE);
            } else if (listItem.getTime_of_day() == 3) {
                iv_point1.setImageResource(R.drawable.mypii_point_pink);
                iv_point2.setImageResource(R.drawable.mypii_point_pink);
                iv_point3.setImageResource(R.drawable.mypii_point_pink);
            }

        }

        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return myPillItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String pillName, int dosagi, int time_of_day, String family) {
        MyPillItem item = new MyPillItem();

        item.setName(pillName);
        item.setDosagi(dosagi);
        item.setFamily(family);
        item.setTime_of_day(time_of_day);


        myPillItems.add(item);
    }
}
