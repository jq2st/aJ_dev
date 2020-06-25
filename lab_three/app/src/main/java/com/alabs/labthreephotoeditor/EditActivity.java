package com.alabs.labthreephotoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    ImageView pic;
    Bitmap imageBitmap;
    Bitmap scaledBitmap;
    Bitmap rotatedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pic = findViewById(R.id.imageViewEdit);
        Intent imageIntent = getIntent();
        Bundle extras = imageIntent.getExtras();
        imageBitmap = (Bitmap) extras.get("imageBitmap");
        scaledBitmap = (Bitmap) extras.get("imageBitmap");
        pic.setImageBitmap(imageBitmap);
    }


    void scaleImage() {
        int width = 200;
        int height = 200;
        scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, true);
    }

    public void savePic(View view) throws IOException {
        ImageView iv = findViewById(R.id.imageViewEdit);
        BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = draw.getBitmap();
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "image_Lpai323" , "newImg");
        Toast toast = Toast.makeText(getApplicationContext(), "Изображение сохранено", Toast.LENGTH_SHORT);
        toast.show();
//
//        FileOutputStream outStream = null;
//        File sdCard = Environment.getExternalStorageDirectory();
//        File dir = new File(sdCard.getAbsolutePath() + "/YourFolderName");
//        dir.mkdirs();
//        String fileName = String.format("%d.jpg", System.currentTimeMillis());
//        File outFile = new File(dir, fileName);
//        outStream = new FileOutputStream(outFile);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//        outStream.flush();
//        outStream.close();
    }

    public void rotateImageL(View view) {
        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        scaledBitmap = rotatedBitmap;
        pic.setImageBitmap(rotatedBitmap);
    }

    public void rotateImageR(View view) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        scaledBitmap = rotatedBitmap;
        pic.setImageBitmap(rotatedBitmap);
    }
}