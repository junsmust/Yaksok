package yaksok.dodream.com.yaksok_refactoring.Adapter.family;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.R;

public class FamilyFindAdapter extends BaseAdapter {
    private static final String TAG = "FamilyFindAdapter";

        private LayoutInflater inflater;
        private ArrayList<FamilyItem> familyItems = new ArrayList<>();
        private int layout;
        private String id;
        private boolean isplay=false;
        public FamilyFindAdapter(Context context, ArrayList<FamilyItem> familyItems, int layout) {
            Log.e(TAG, "FamilyFindAdapter: "+ familyItems.size() );
            this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.familyItems = familyItems;
            this.layout = layout;
        }

        @Override
        public int getCount() {
            return familyItems.size();
        }

        @Override
        public Object getItem(int position) {
            return familyItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            if(convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(layout,parent,false);
            }

            TextView f_name = (TextView)convertView.findViewById(R.id.family_first_tv);
            TextView name = (TextView)convertView.findViewById(R.id.family_list_item_name);
            TextView f_pn = (TextView)convertView.findViewById(R.id.family_list_item_pn);

            FamilyItem familyItem = familyItems.get(position);
            f_name.setText(familyItem.getFirst_name());
            name.setText(familyItem.getName());
            f_pn.setText(familyItem.getUser_pn());


            return convertView;
        }

        public void addItem(String first_name,String name,String pn){

            FamilyItem familyItem = new FamilyItem();

            familyItem.setFirst_name(first_name);
            familyItem.setName(name);
            familyItem.setUser_pn(pn);
            /*if(isplay==false) {
             familyItems.clear();
            }*/
            familyItems.add(familyItem);
            isplay=true;
        }
        public void remove(String string) {
            // TODO Auto-generated method stub



        }
        public String getNameToId(){
            return id;
        }
        public void setNameToId(String id){
            this.id = id;
        }
    }


