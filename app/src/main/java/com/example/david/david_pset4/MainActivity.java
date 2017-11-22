package com.example.david.david_pset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TodoDatabase mTodoDatabase;
    EditText editText;
    long selected_id;
    private Button leftbtn, rightbtn;
    Integer Itemid = -1;
    String selected ="";
    private ListView listView;
    private TodoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText.setHint("todo item");
        leftbtn = findViewById(R.id.leftbutton);
        rightbtn = findViewById(R.id.rightbutton);
        rightbtn.setVisibility(View.INVISIBLE);
        listView = findViewById(R.id.listView);
        mTodoDatabase = TodoDatabase.getInstance(this);
        mTodoDatabase = TodoDatabase.getInstance(getApplicationContext());

        listView.setOnItemLongClickListener(new ClickListener());
        update_data();

    }


    public void add_data(String new_data) {
    Boolean inserted = mTodoDatabase.insert(new_data);
    System.out.println(inserted);

    if ( inserted == true){
        toast_message("succesfull insert");
        update_data();


    }
    else{
        toast_message("failed insertion");
    }
    }
    public void update_data(){
        Cursor data = mTodoDatabase.getData();
        ArrayList<String> listData = new ArrayList<String>();


        while (data.moveToNext()){
            listData.add(data.getString(1));
            System.out.println(data.getString(1));
        }
        //     ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        System.out.println("HOE DAN?");
        adapter = new TodoAdapter(this, data);
        System.out.println("HOE DAN2?");

        listView.setAdapter(adapter);

    }

    private void toast_message(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void addItem(View view) {

        String new_data = editText.getText().toString();
        if (new_data.length() != 0 ){
            add_data(new_data);
        }
        else
        {
            toast_message("at least put 1 character there");
        }
    }





    private class ClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            selected = parent.getItemAtPosition(position).toString();
            selected_id = id;
            mTodoDatabase.deleteItem(selected_id);
            update_data();
            toast_message("deleted " + selected);
            return true;
        }



    }



}
