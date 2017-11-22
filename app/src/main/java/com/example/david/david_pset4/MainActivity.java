package com.example.david.david_pset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TodoDatabase mTodoDatabase;
    EditText editText;
    Button leftbtn, rightbtn;
    Integer Itemid = -1;
    String selected ="";
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        leftbtn = findViewById(R.id.leftbutton);
        rightbtn = findViewById(R.id.rightbutton);
        rightbtn.setVisibility(View.INVISIBLE);
        mTodoDatabase = TodoDatabase.getInstance(this);
        listView = findViewById(R.id.listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                selected = parent.getItemAtPosition(position).toString();
                Cursor data = mTodoDatabase.get_id(selected);
                Itemid = -1;
                while(data.moveToNext()){
                    Itemid = data.getInt(0);
                }
                if  (Itemid == -1){
                    toast_message("no name like this has been found");
                    return false;
                }
                mTodoDatabase.deleteItem(Itemid, selected);
                populate_listview();
                toast_message("deleted " + selected);
                return true;
            }
        });
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_data = editText.getText().toString();
                if (new_data.length() != 0 ){
                    insertData(new_data);
                }
                else
                {
                    toast_message("at least put 1 character there");
                }
            }
        });

        mTodoDatabase = TodoDatabase.getInstance(getApplicationContext());
    }


    public void insertData(String new_data) {
    Boolean inserted = mTodoDatabase.add_data(new_data);
    System.out.println(inserted);

    if ( inserted == true){
        toast_message("succesfull insert");
        populate_listview();


    }
    else{
        toast_message("failed insertion");
    }
    }
    public void populate_listview(){
        Cursor data = mTodoDatabase.getData();
        ArrayList<String> listData = new ArrayList<String>();


        while (data.moveToNext()){
            listData.add(data.getString(1));
            System.out.println(data.getString(1));
        }
        //     ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        System.out.println("HOE DAN?");
        TodoAdapter adapter = new TodoAdapter(this, data);
        System.out.println("HOE DAN2?");

        listView.setAdapter(adapter);

    }

    private void toast_message(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
