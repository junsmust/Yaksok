package yaksok.dodream.com.yaksok_refactoring.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.R;

public class SearchListAdapter extends BaseAdapter {
    private ArrayList<PillSearchItem> pillSearchItems = new ArrayList<PillSearchItem>() ;

    // ListViewAdapter의 생성자
    public SearchListAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return pillSearchItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.searchpill_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tv_S_PillName = (TextView) convertView.findViewById(R.id.tv_S_PillName) ;
        TextView tv_S_PillElement = (TextView) convertView.findViewById(R.id.tv_S_PillElement) ;
        TextView tv_S_PillNum = (TextView) convertView.findViewById(R.id.tv_S_PillNum);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        PillSearchItem listItem = pillSearchItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tv_S_PillName.setText(listItem.getName());
        tv_S_PillElement.setText(listItem.getElement());
        System.out.println(listItem.getMedicineNO());
        String numx = String.valueOf(listItem.getMedicineNO());
        tv_S_PillNum.setText(numx);

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
        return pillSearchItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int pillNum, String pillName, String element) {
        PillSearchItem item = new PillSearchItem();

        item.setName(pillName);
        item.setElement(element);
        item.setMedicineNO(pillNum);

        pillSearchItems.add(item);
    }
}

