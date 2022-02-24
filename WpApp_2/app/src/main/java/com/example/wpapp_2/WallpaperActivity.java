package com.example.wpapp_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    private ImageView walpaperIV;
    private Button setWallpaperBtn;
    private String imgUrl;
    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        walpaperIV=findViewById(R.id.idIVWallpaper);
        setWallpaperBtn=findViewById(R.id.idBtnSetWallpaper);
        imgUrl= getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(walpaperIV);
        wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
        setWallpaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this,"Resim Yüklenemedi",Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                       try {
                           wallpaperManager.setBitmap(resource);
                       }catch (IOException e)
                       {
                           e.printStackTrace();
                           Toast.makeText(WallpaperActivity.this,"Duvar Kağıdı Ayarlanamadı",Toast.LENGTH_SHORT).show();

                       }
                        return false;
                    }
                }).submit();
                FancyToast.makeText(WallpaperActivity.this,"Duvar Kağıdı Ayarlandı",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }
        });


    }
}