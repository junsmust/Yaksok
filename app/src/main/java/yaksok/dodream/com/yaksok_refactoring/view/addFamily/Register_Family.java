package yaksok.dodream.com.yaksok_refactoring.view.addFamily;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import retrofit2.Retrofit;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.family_register.Register_Fam_Presenter;
import yaksok.dodream.com.yaksok_refactoring.view.Main.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Register_Family extends AppCompatActivity implements IRegister_Presenter_Family_To_View ,View.OnClickListener{

    public EditText fmaily_number_edt;
    public Button family_find_btn, family_find_skip_btn,complete_btn,deleteFamilyBtn;
    public SwipeMenuListView family_list_view;
    public Retrofit retrofit,retorofit2;
    public UserService userService;
    public DeleteService deleteService;
    public FamilyFindAdapter adapter;
    public ArrayList<FamilyItem> familyItemss = new ArrayList<>();
    public ArrayList<FamilyItem> familyItemss2 = new ArrayList<>();
    public  AlertDialog.Builder dialog;
    public static FamilyVO familyVO;
    public String family_user_id = "";
    public boolean isAddedFamily = false;//김대표
    Intent intent;
    String itForSignUp;
    Register_Fam_Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
        intent = new Intent(getIntent());
                //itForSignUp = intent.getStringExtra("itForSignUp");
                //Log.d("aaaaaaaaaaaaa",itForSignUp);

        presenter = new Register_Fam_Presenter(this);

        presenter.setPreviousRegistered();

        dialog = new AlertDialog.Builder(this);


        fmaily_number_edt = (EditText) findViewById(R.id.findFamily_edt);
        family_find_btn = (Button) findViewById(R.id.findFamily_btn);
        family_list_view = (SwipeMenuListView) findViewById(R.id.family_list);
        complete_btn = (Button)findViewById(R.id.family_complete_btn);


        complete_btn.setOnClickListener(this);
        family_find_btn.setOnClickListener(this);




        adapter = new FamilyFindAdapter(this,familyItemss,R.layout.family_list_item);

        presenter.sendAdapter(adapter);




        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));
                // set item width
                openItem.setWidth(120);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(10);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(120);
                // set a icon
                 deleteItem.setIcon(R.drawable.delete6);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };







        family_list_view.setMenuCreator(creator);
        family_list_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        makeToastMessage("눌림");
                        break;
                    case 1:
                        int index1 = ((FamilyItem)adapter.getItem(position)).getName().indexOf('(');
                        int index2 = ((FamilyItem)adapter.getItem(position)).getName().indexOf(')');

                        String id = ((FamilyItem)adapter.getItem(position)).getName().substring(index1+1,index2);
                        makeDialog(id,position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });





        }
    @Override
    protected void onStart() {
        super.onStart();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = LayoutInflater.from(this).inflate(R.layout.chattingactionbar,null);
        ImageView imageView = view.findViewById(R.id.back_layout_imv);
        TextView textView = view.findViewById(R.id.title_txt);

        FrameLayout frameLayout = view.findViewById(R.id.frame_layout);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent resultIntent = new Intent();
        setResult(4000,resultIntent);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MainPageActivity.class);
//                startActivityForResult(intent,7777);
                finish();
            }
        });
        textView.setText("나의 가족 등록");
        textView.setGravity(Gravity.CENTER);
//        textView.setGravity(Gravity.CENTER);
        actionBar.setTitle(textView.getText().toString());



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        actionBar.setCustomView(view,layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findFamily_btn:
                presenter.searchFam(fmaily_number_edt.getText().toString());
                break;
            case R.id.family_complete_btn:
                startActivity(new Intent(getApplicationContext(), MainPage_activity.class));
                finish();
                break;

        }
    }

    @Override
    public void makeToastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getArrayList(ArrayList<FamilyItem> familyItems) {
        Log.d("djkdjdjdj",familyItems+" ");
        familyItemss = familyItems;

            familyItemss2 = (ArrayList<FamilyItem>) familyItemss.clone();
    }

    @Override
    public void makeDialog(final String name, final String id) {

        dialog.setTitle("가족찾기");
        dialog.setMessage(name+"을 가족으로 등록 하시겠습니까?");
        dialog.setCancelable(false);
        String user_id = "";

        Log.d("@@@@!!!",id);
        int index1 = name.indexOf('(');
        int index2 = name.indexOf(')');

        final String id2 = name.substring(index1+1,index2);


        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.setYesRegisterFam(true,name);

            }
        });
        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();


    }

    @Override
    public void makeDialog(final String id, final int index) {
        dialog.setTitle("가족삭제");
        dialog.setMessage(id+"님을 삭제 하시겠습니까?");
        dialog.setCancelable(false);



        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteFam(true,id,index);
                //adapter.notifyDataSetChanged();

            }
        });
        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               makeToastMessage("취소 되었습니다. ");
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void getFromModelAdapter(FamilyFindAdapter adapter) {
        this.adapter = adapter;


        family_list_view.setAdapter(adapter);
    }

    @Override
    public void onResponse(boolean response) {
        if(response){



            for(int i=0;i<familyItemss.size();i++){
               // Log.d("ffffff1"," "+familyItemss.size());

                adapter.addItem(familyItemss.get(i).getName());
                adapter.notifyDataSetChanged();
                family_list_view.setAdapter(adapter);
                Log.d("i",i+familyItemss.get(i).getName());
            }



        }
        else{
            fmaily_number_edt.setText("");
        }
    }
    @Override
    public void onResponse2(boolean response2, FamilyItem familyItem) {
        if(response2){
            adapter.addItem(familyItem.getName());
            adapter.notifyDataSetChanged();
            family_list_view.setAdapter(adapter);
        }
    }

    @Override
    public void onResponse3(boolean response3) {
        if(response3){
            familyItemss.clear();
            adapter.notifyDataSetChanged();
            family_list_view.setAdapter(adapter);


            adapter = new FamilyFindAdapter(this,familyItemss2,R.layout.family_list_item);
            for(int i=0;i<familyItemss.size();i++){

                adapter.addItem(familyItemss2.get(i).getName());
                adapter.notifyDataSetChanged();
                Log.d("basic3 size ", " "+familyItemss2.size());
            }
            adapter.notifyDataSetChanged();
            family_list_view.setAdapter(adapter);

            familyItemss = (ArrayList<FamilyItem>)familyItemss2.clone();
            presenter.snedViewToModelArrayList(familyItemss);

            }

            //family_list_view.setAdapter(adapter);

        }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        Log.d("ddd111","실행됨");

    }
}



