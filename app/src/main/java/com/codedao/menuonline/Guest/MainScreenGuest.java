package com.codedao.menuonline.Guest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codedao.menuonline.Adapter.RecyclerviewTableAdapter;
import com.codedao.menuonline.Model.Table;
import com.codedao.menuonline.R;

import java.util.ArrayList;
import java.util.Random;

public class MainScreenGuest extends AppCompatActivity {
    RecyclerView rcvTable;
    ArrayList<Table> listTables;
    RecyclerviewTableAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_guest2);
        getSupportActionBar().hide();
        initView();
        listTables=new ArrayList<>();
        rcvTable.setLayoutManager(new GridLayoutManager(this,3));
        for (int i = 0; i <15 ; i++) {
            int a=randomMaxChair();
            listTables.add(new Table(i+1,randomEmptyChair(a),a));
        }
        adapter=new RecyclerviewTableAdapter(listTables,this);
        rcvTable.setAdapter(adapter);
    }

    private void initView() {
        rcvTable=findViewById(R.id.rcvTable);
    }
    private int randomMaxChair(){
        Random r=new Random();
        return r.nextInt(3)+2;

    }
    private int randomEmptyChair(int x){
        Random r=new Random();
        return r.nextInt(x);

    }
}
