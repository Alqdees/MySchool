package com.school.myschool;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class ConvertBitMap {
    public static byte[] getByte1(Bitmap i1){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        i1.compress(Bitmap.CompressFormat.JPEG,99,stream);
        return stream.toByteArray();
    }  public static byte[] getByte2(Bitmap i2){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        i2.compress(Bitmap.CompressFormat.JPEG,99,stream);
        return stream.toByteArray();
    }
    public static byte[] getByte3(Bitmap i3){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        i3.compress(Bitmap.CompressFormat.JPEG,99,stream);
        return stream.toByteArray();
    }
    public static byte[] getByte4(Bitmap i4){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        i4.compress(Bitmap.CompressFormat.JPEG,99,stream);
        return stream.toByteArray();
    }
    public static byte[] getByte5(Bitmap i5){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        i5.compress(Bitmap.CompressFormat.JPEG,99,stream);
        return stream.toByteArray();
    }
    public static byte[] getByte6(Bitmap i6){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        i6.compress(Bitmap.CompressFormat.JPEG,99,stream);
        return stream.toByteArray();
    }

}
