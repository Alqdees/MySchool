package com.school.myschool;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AdapterRv extends RecyclerView.Adapter<AdapterRv.HolderRv> {
    ArrayList<Model> modelArray;
    Context context;
    databaseSqlite db;

    public AdapterRv (ArrayList modelArray,Context context) {
        this.context = context;
        this.modelArray = modelArray;
        db = new databaseSqlite(context);
    }

        @NonNull
    @Override
    public HolderRv onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclercardview,null,false);
       HolderRv holderRv = new HolderRv(view);
        return holderRv ;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRv holder, int position) {
//        Bitmap picture, identification, residence, tom, certificate, document;
       Model model = modelArray.get(position);
        String ID = model.getId();
        String name = model.getName();
        byte[] picture = model.getPicture();
        byte[] identification = model.getIdentification();
        byte[] residence = model.getResidence();
        byte[] tom = model.getTom();
        byte[] certificate = model.getCertificate();
        byte[] document = model.getDocument();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
       holder.tv_name.setText(name);
       holder.image.setImageBitmap(bitmap);
       holder.getAdapterPosition();
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent send = new Intent(context,ShowRecordActivity.class);
               send.putExtra("ID", ID);
               context.startActivity(send);

           }
       });
       holder.more.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String[] option = {"تعديل","حذف"};
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("أختر...");
               builder.setItems(option, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       if (i == 0){
                           Intent intent = new Intent(context,AddActivity.class);
                           intent.putExtra("id",ID);
                           intent.putExtra("name",name);
                           intent.putExtra("isUpdate",true);
                           Log.d("ERROR","where",new Exception());
                           //        Bitmap picture, identification, residence, tom, certificate, document;
                           context.startActivity(intent);
                       }
                       else if (i == 1){
                           databaseSqlite db = new databaseSqlite(context);
                           db.deleteItem(ID);
                           db.close();
                           ((MainActivity)context).onResume();
                       }
                   }
               });
               builder.create().show();
           }
       });
    }

    @Override
    public int getItemCount() {
        return modelArray.size();
    }

    class HolderRv extends RecyclerView.ViewHolder{
        TextView tv_name;
        ImageView image;
        ImageButton more;

        public HolderRv(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.Tv_name);
            image = itemView.findViewById(R.id.Image);
            more = itemView.findViewById(R.id.more);

        }
    }
}
