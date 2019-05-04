package yaksok.dodream.com.yaksok_refactoring.view.addFamily;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.presenter.IRegister_fam_presenter;
import yaksok.dodream.com.yaksok_refactoring.presenter.Register_Fam_Presenter;
import yaksok.dodream.com.yaksok_refactoring.view.Main.MainPage_activity;
import yaksok.dodream.com.yaksok_refactoring.vo.DeleteService;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok_refactoring.vo.UserService;

public class Register_Family extends AppCompatActivity implements IRegister_Presenter_Family_To_View ,View.OnClickListener{

    public EditText fmaily_number_edt;
    public Button family_find_btn, family_find_skip_btn,complete_btn,deleteFamilyBtn;
    public ListView family_list_view;
    public Retrofit retrofit,retorofit2;
    public UserService userService;
    public DeleteService deleteService;
    public FamilyFindAdapter adapter;
    public ArrayList<FamilyItem> familyItemss = new ArrayList<>();
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


        dialog = new AlertDialog.Builder(this);


        fmaily_number_edt = (EditText) findViewById(R.id.findFamily_edt);
        family_find_btn = (Button) findViewById(R.id.findFamily_btn);
        family_list_view = (ListView) findViewById(R.id.family_list);
        family_find_skip_btn = (Button) findViewById(R.id.family_skip_btn);
        complete_btn = (Button)findViewById(R.id.family_complete_btn);


        complete_btn.setOnClickListener(this);
        family_find_btn.setOnClickListener(this);
        family_find_skip_btn.setOnClickListener(this);



        adapter = new FamilyFindAdapter(this,familyItemss,R.layout.family_list_item);


        presenter.setPreviousRegistered();






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
            case R.id.family_skip_btn:
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

    }

    @Override
    public void makeDialog(String name, final String id) {

        dialog.setTitle("가족찾기");
        dialog.setMessage(name+"을 가족으로 등록 하시겠습니까?");
        dialog.setCancelable(false);
        String user_id = "";


        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.setYesRegisterFam(true,id);

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
    public void onResponse(boolean response) {
        if(response){

            for(int i=0;i<familyItemss.size();i++){
               // Log.d("ffffff1"," "+familyItemss.size());

                adapter.addItem(familyItemss.get(i).getName());
               // adapter.notifyDataSetChanged();

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


}
