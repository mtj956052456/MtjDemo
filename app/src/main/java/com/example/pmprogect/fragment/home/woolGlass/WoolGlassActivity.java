package com.example.pmprogect.fragment.home.woolGlass;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseActivity;

import butterknife.BindView;

public class WoolGlassActivity extends BaseActivity {
    @BindView(R.id.img_wool_glass)
    ImageView mImgWoolGlass;
    @BindView(R.id.img_guolin)
    ImageView mImgGuoLin;

    @Override
    protected int setView() {
        return R.layout.activity_wool_glass;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wool_glass_img);
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(this);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //Set the radius of the blur: 0 < radius <= 25
        blurScript.setRadius(25);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        bitmap.recycle();
        rs.destroy();
        mImgWoolGlass.setImageBitmap(outBitmap);
    }

}
