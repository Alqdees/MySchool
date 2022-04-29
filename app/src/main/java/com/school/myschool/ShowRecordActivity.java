package com.school.myschool;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class ShowRecordActivity extends AppCompatActivity {
    private ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    private databaseSqlite db;
    private String ID;
    byte [] Pic;
    TextView tv_name;
    private BitmapDrawable drawable;
    private Bitmap bitmap;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);
        Intent received = getIntent();
        ID = received.getStringExtra("ID");
//        Pic= received.getByteArrayExtra("pic");
        db = new databaseSqlite(this);
        iv1 = findViewById(R.id.image_vi1);
        iv2 = findViewById(R.id.image_vi2);
        iv3 = findViewById(R.id.image_vi3);
        iv4 = findViewById(R.id.image_vi4);
        iv5 = findViewById(R.id.image_vi5);
        iv6 = findViewById(R.id.image_vi6);
        tv_name = findViewById(R.id.iv_name);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });
        showRecordDetails();
    }
    private void shareImage() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
         drawable = (BitmapDrawable) iv1.getDrawable();
         bitmap = drawable.getBitmap();
        File file = new File(getExternalCacheDir()+"/"+"MySchool" + ".jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,99,outputStream);
            outputStream.flush();
            outputStream.close();
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }catch (Exception e ){
            Toast.makeText(ShowRecordActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        startActivity(Intent.createChooser(intent,"ارسال عبر :"));
    }
    private void showRecordDetails() {
        String RowQuery = " SELECT * FROM " + databaseSqlite.TB_NAME + " WHERE " +  databaseSqlite.C_ID  + " =\""+ ID +"\"";
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = database.rawQuery(RowQuery,null);
        if (cursor.moveToFirst()){
            do {
                String id = ""+cursor.getString(0);
                String name = ""+cursor.getString(1);
                byte[] pic1 = cursor.getBlob(2);
                byte[] pic2= cursor.getBlob(3);
                byte[] pic3 = cursor.getBlob(4);
                byte[] pic4 = cursor.getBlob(5);
                byte[] pic5 = cursor.getBlob(6);
                byte[] pic6 = cursor.getBlob(7);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(pic1,0,pic1.length);
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(pic2,0,pic2.length);
                Bitmap bitmap3 = BitmapFactory.decodeByteArray(pic3,0,pic3.length);
                Bitmap bitmap4 = BitmapFactory.decodeByteArray(pic4,0,pic4.length);
                Bitmap bitmap5 = BitmapFactory.decodeByteArray(pic5,0,pic5.length);
                Bitmap bitmap6 = BitmapFactory.decodeByteArray(pic6,0,pic6.length);
                iv1.setImageBitmap(bitmap1);
                iv2.setImageBitmap(bitmap2);
                iv3.setImageBitmap(bitmap3);
                iv4.setImageBitmap(bitmap4);
                iv5.setImageBitmap(bitmap5);
                iv6.setImageBitmap(bitmap6);
                tv_name.setText(name);
            }while (cursor.moveToNext());
        }
    }
}