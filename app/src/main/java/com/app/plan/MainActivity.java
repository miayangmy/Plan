package com.app.plan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.DomainCombiner;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    final String TAG = "tagg";
    FloatingActionButton btn1;

    private ListView lv;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<Note>();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (FloatingActionButton) findViewById(R.id.fab_add);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"CLick");
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent,0);
            }
        });

        lv = findViewById(R.id.lv);
        adapter = new NoteAdapter(getApplicationContext(),noteList);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(this);

        refreshListView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        String content = data.getStringExtra("content");
        Note note = new Note(content);//,1

        DBManager op = new DBManager(context);
        op.open();
        op.addNote(note);
        op.close();

        refreshListView();
        super.onActivityResult(requestCode, resultCode, data);

       Log.d(TAG, content);
    }


    public void refreshListView(){
        DBManager op = new DBManager(context);
        op.open();
        if (noteList.size() > 0) noteList.clear();
        noteList.addAll(op.getAllNotes());
        op.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前计划").setPositiveButton("确认",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteList.remove(position);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("取消",null);
        builder.create().show();
        Log.i(TAG, "onItemLongClick: size=" + noteList.size());

        return true;
    }

}



