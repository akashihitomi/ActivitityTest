package com.example.asus.activititytest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.R.attr.handle;
import static android.R.attr.path;
import static com.example.asus.activititytest.CameraTest.CHOOSE_PHOTO;
import static com.example.asus.activititytest.R.id.takephoto;

public class CameraTest extends AppCompatActivity {
   public static final int Take_photo = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        Button takePhoto = (Button) findViewById(takephoto);
        Button chooseFromAlbum = (Button) findViewById(R.id.album);
        picture = (ImageView) findViewById(R.id.headportrait);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImagine = new File(getExternalCacheDir(),"output_image.jpg"); /*创建File对象保存拍下图片，并存在SD卡*/
                try {
                    if (outputImagine.exists()) {
                        outputImagine.delete();
                    }
                    outputImagine.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >=24) { /*android版本是否<7.0*/
                    imageUri = FileProvider.getUriForFile(CameraTest.this,"com.exmaple.cameraalbumtest.filrprovider",outputImagine); /*将File对象转换成封装过的Uri对象*/
                }else {
                    imageUri = Uri.fromFile(outputImagine);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,Take_photo);
            }
        });

    chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(ContextCompat.checkSelfPermission(CameraTest.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CameraTest.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            } else {
                openAlbum();
            }
        }
    });
}

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public  void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                } else {
                    Toast.makeText(this,"未获得权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data) {
        switch (requestCode) {
            case Take_photo:
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream(imageUri));
                    picture.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImagineKitKat (data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImagineKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("@xml/filepathscontent://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
              }
             }else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }


        private void handleImageBeforeKitKat (Intent data) {
            Uri uri = data.getData();
            String imagePath = getImagePath(uri,null);
            displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
    }
    return path;
 }
private void displayImage(String imagePath) {
    if (imagePath != null) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        picture.setImageBitmap(bitmap);
    } else {
        Toast.makeText(this,"图片获取失败",Toast.LENGTH_SHORT).show();
    }
  }
}
