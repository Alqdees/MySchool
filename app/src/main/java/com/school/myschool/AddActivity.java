package com.school.myschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {
    private ActionBar bar;
    private EditText Et_name;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE1 = 102;
    private static final int IMAGE_PICK_CAMERA_CODE2 = 11;
    private static final int IMAGE_PICK_CAMERA_CODE3 = 22;
    private static final int IMAGE_PICK_CAMERA_CODE4 = 33;
    private static final int IMAGE_PICK_CAMERA_CODE5 = 44;
    private static final int IMAGE_PICK_CAMERA_CODE6 = 55;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    private String[] cameraPermission;
    private String[] storagePermission;
    private static final int PICK_FIRST_IMAGE = 1;
    private static final int PICK_SECOND_IMAGE = 2;
    private static final int PICK_THREE_IMAGE = 3;
    private static final int PICK_FOUR_IMAGE = 4;
    private static final int PICK_FIVE_IMAGE = 5;
    private boolean isEdit = false;
    private Uri uri;
    private Button button;
    private ImageView picture,identification,residence,tom,certificate,document;;
    private FloatingActionButton Add;
    private Bitmap image,image1,image2,image3,image4,image5;
    private databaseSqlite db;
    String id,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        bar = getSupportActionBar();
//        bar.setTitle("أضافة المعلمين");
        db = new databaseSqlite(this);
        Et_name = findViewById(R.id.Et_Name);
        residence = findViewById(R.id.residence);
        tom = findViewById(R.id.tom);
        certificate = findViewById(R.id.certificate);
        document = findViewById(R.id.document);
        picture = findViewById(R.id.ImView);
        Add = findViewById(R.id.Add);
        identification = findViewById(R.id.identification);
        button = findViewById(R.id.getPicture);
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isUpdate",false);
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        if (isEdit){
            bar.setTitle("التعديل");
            Et_name.setText(name);
            String RowQuery = " SELECT * FROM " + databaseSqlite.TB_NAME + " WHERE " +  databaseSqlite.C_ID  + " =\""+ id +"\"";
            SQLiteDatabase database = db.getWritableDatabase();
            Cursor cursor = database.rawQuery(RowQuery,null);
//        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
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
                    picture.setImageBitmap(bitmap1);
                    identification.setImageBitmap(bitmap2);
                    residence.setImageBitmap(bitmap3);
                    tom.setImageBitmap(bitmap4);
                    certificate.setImageBitmap(bitmap5);
                    document.setImageBitmap(bitmap6);
                    Et_name.setText(name);
                }while (cursor.moveToNext());
            }
//                 int result =
//                 if (result != -1){
//                     Toast.makeText(AddActivity.this, "تم التعديل", Toast.LENGTH_SHORT).show();
//                 }else {
//                     Toast.makeText(AddActivity.this, "فشل التعديل", Toast.LENGTH_SHORT).show();
//                 }
//            }catch (Exception e){
//                Toast.makeText(AddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }

            //        Bitmap picture, identification, residence, tom, certificate, document;

        }else {
            bar.setTitle("أضافة معلم");
        }

//        getDocument = findViewById(R.id.getDocument);
//        getDocument.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imagePickDialog();
//            }
//        });
//        getCertificate = findViewById(R.id.getCertificate);
//        getTom = findViewById(R.id.getTom);
//        getResidence = findViewById(R.id.getResidence);
//        getIdentification = findViewById(R.id.getIdentification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }
    private void imagePickDialog() {
        String[] options = {"الكاميرا","الاستوديو"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("جيب الصورة");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
//                        IV,identification,residence,tom,certificate,document;
                        pickFromCamera();
                    }
                }else if (i == 1){
                    if (!checkStoragePermissions()){
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }
                }
            }
        })
                .create().show();
    }
//    IV,identification,residence,tom,certificate,document;
    private void pickFromGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,"Select Picture"), IMAGE_PICK_GALLERY_CODE);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
        startActivityForResult(galleryIntent,PICK_FIRST_IMAGE);
        startActivityForResult(galleryIntent,PICK_SECOND_IMAGE);
        startActivityForResult(galleryIntent,PICK_THREE_IMAGE);
        startActivityForResult(galleryIntent,PICK_FOUR_IMAGE);
        startActivityForResult(galleryIntent,PICK_FIVE_IMAGE);
//        startActivityForResult(galleryIntent,PICK_SIX_IMAGE);
//            startActivityForResult(galleryIntent, PICK_FIRST_IMAGE);

    }
//    private void pickGallery(){
//        Intent intent = new Intent(
//                Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(intent, PICK_FIRST_IMAGE);
//    }
    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"عنوان الصورة");
        values.put(MediaStore.Images.Media.DESCRIPTION,"وصف الصورة");
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE1);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE2);
//        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE3);
//        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE4);
//        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE5);
//        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE6);
    }
    private boolean checkStoragePermissions(){
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return result1;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==
                (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return result1 && result2;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
               if (grantResults.length > 0){
                   boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                   boolean storageAccepted = grantResults[1] ==PackageManager.PERMISSION_GRANTED;
                   if (cameraAccepted && storageAccepted){
                       pickFromCamera();
                   }else{
                       Toast.makeText(AddActivity.this, "تحتاج الصلاحيات", Toast.LENGTH_SHORT).show();
                   }
               }
            }
            break;
            case STORAGE_REQUEST_CODE:{
             if (grantResults.length > 0 ){
                 boolean storageAccepted = grantResults[0] ==PackageManager.PERMISSION_GRANTED;
                 if (storageAccepted){
                     pickFromGallery();
                 }else{
                     Toast.makeText(AddActivity.this, "صلاحيات الكتابة على الذاكرة", Toast.LENGTH_SHORT).show();
                 }
              }
          }
            break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if ( resultCode == RESULT_OK) {
           if (requestCode == IMAGE_PICK_GALLERY_CODE) {
               picture.setImageURI(data.getData());
           } else if (requestCode == PICK_FIRST_IMAGE) {
               identification.setImageURI(data.getData());
           } else if (requestCode == PICK_SECOND_IMAGE) {
               residence.setImageURI(data.getData());
           } else if (requestCode == PICK_THREE_IMAGE) {
               tom.setImageURI(data.getData());
           } else if (requestCode == PICK_FOUR_IMAGE) {
               certificate.setImageURI(data.getData());
           } else if (requestCode == PICK_FIVE_IMAGE) {
               document.setImageURI(data.getData());
           } else if (resultCode == RESULT_OK) {
               if (requestCode == IMAGE_PICK_CAMERA_CODE1) {
                   picture.setImageURI(uri);
               } else if (requestCode == IMAGE_PICK_CAMERA_CODE2) {
                   identification.setImageURI(uri);
               }
           }
       }
//        IV,identification,residence,tom,certificate,document;;
               super.onActivityResult(requestCode, resultCode, data);
    }
    private void insertData() {
        String name = Et_name.getText().toString();
        image = ((BitmapDrawable) picture.getDrawable()).getBitmap();
        image1 = ((BitmapDrawable)identification.getDrawable()).getBitmap();
        image2 = ((BitmapDrawable)residence.getDrawable()).getBitmap();
        image3 = ((BitmapDrawable)tom.getDrawable()).getBitmap();
        image4 = ((BitmapDrawable)certificate.getDrawable()).getBitmap();
        image5 = ((BitmapDrawable)document.getDrawable()).getBitmap();
        if (isEdit) {
            try {
                db.updateData(name,id,
                        ConvertBitMap.getByte1(image),
                        ConvertBitMap.getByte2(image1),
                        ConvertBitMap.getByte3(image2),
                        ConvertBitMap.getByte4(image3),
                        ConvertBitMap.getByte5(image4),
                        ConvertBitMap.getByte6(image5));
                Et_name.setText("");
                Toast.makeText(AddActivity.this, "تم التعديل ", Toast.LENGTH_SHORT).show();

//                picture.setImageURI();
            }catch (Exception e){
                Toast.makeText(AddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else {
            try {
                long result = db.insertData(name,
                        ConvertBitMap.getByte1(image),
                        ConvertBitMap.getByte2(image1),
                        ConvertBitMap.getByte3(image2),
                        ConvertBitMap.getByte4(image3),
                        ConvertBitMap.getByte5(image4),
                        ConvertBitMap.getByte6(image5));
                Et_name.setText("");
                if (result != -1) {
                    Toast.makeText(AddActivity.this, "تمت لاضافة", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddActivity.this, "فشلت الاضافة", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(AddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}