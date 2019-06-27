package yaksok.dodream.com.yaksok_refactoring.view.addFamily;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyFindAdapter;
import yaksok.dodream.com.yaksok_refactoring.Adapter.family.FamilyItem;
import yaksok.dodream.com.yaksok_refactoring.vo.FamilyVO;

public interface IRegister_Presenter_Family_To_View {
    void makeToastMessage(String message);
    void onResponse(boolean response);
    void onResponse2(boolean response2,FamilyItem familyItem);
    void onResponse3(boolean response3);
    void getArrayList(ArrayList<FamilyItem> familyItems);
    void makeDialog(String name,String id);
    void deleteMakeDialog(String name,  int index);
    void getFromModelAdapter(FamilyFindAdapter adapter);


}
