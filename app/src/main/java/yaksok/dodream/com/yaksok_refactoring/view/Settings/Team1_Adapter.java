package yaksok.dodream.com.yaksok_refactoring.view.Settings;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.MyPill.MyPillItem;
import yaksok.dodream.com.yaksok_refactoring.R;

public class Team1_Adapter extends BaseAdapter {
    private ArrayList<team1_item> team1_items= new ArrayList<team1_item>() ;

    // ListViewAdapter의 생성자
    public Team1_Adapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return team1_items.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.develop_item1, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tv_name = (TextView)convertView.findViewById(R.id.tv_devel_name);
        TextView tv_com = (TextView)convertView.findViewById(R.id.tv_devel_com);



        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        team1_item listItem = team1_items.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tv_name.setText(listItem.getName());
        tv_com.setText(listItem.getCompany());

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
        return team1_items.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String company) {
        team1_item item = new team1_item();

        item.setName(name);
        item.setCompany(company);


        team1_items.add(item);
    }
}
