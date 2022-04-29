package com.school.myschool;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorWindow;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton buttonAdd;
    private ActionBar bar;
    private RecyclerView Rv;
    private databaseSqlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.B_Add);
        Rv = findViewById(R.id.Rv_Main);
        bar = getSupportActionBar();
        bar.setTitle("مدرستي");
        try {
             Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });
        db = new databaseSqlite(this);
    }
    @Override
    protected void onResume() {
        loadRecords();
        super.onResume();

    }
    private void loadRecords() {
        AdapterRv adapterRv = new AdapterRv(db.getAllRecords(databaseSqlite.C_ID), MainActivity.this);
        Rv.setAdapter(adapterRv);
        Rv.setHasFixedSize(true);
    }
    private void search(String name){
        databaseSqlite db = new databaseSqlite(this);
        AdapterRv adapter = new AdapterRv(db.searchRecord(name),MainActivity.this);
        Rv.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)){
                    Toast.makeText(MainActivity.this, "فارغ الحقل", Toast.LENGTH_SHORT).show();
                }else {
                    search(query);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void dialog(){
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("هل متأكد من الخروج");
        String[] options = {"نعم","لا, شكرا على التنبيه"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    finish();
                }
                else if (i == 1){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    public void onBackPressed() {
       dialog();
    }
}