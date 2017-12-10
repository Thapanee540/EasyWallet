package com.example.easywallet.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easywallet.R;
import com.example.easywallet.model.PayItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO_MANG on 10/12/2560.
 */

public class PayListAdapter extends ArrayAdapter<PayItem> {

    private Context mContext;
    private int mLayoutID;
    private ArrayList<PayItem> mPayList;

    public PayListAdapter(@NonNull Context context, int layoutID, ArrayList<PayItem> payItemList) {
        super(context, layoutID, payItemList);
        this.mContext = context;
        this.mLayoutID = layoutID;
        this.mPayList = payItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutID,null);

        PayItem item = mPayList.get(position);

        ImageView payImage = itemLayout.findViewById(R.id.Pay_imageView);
        TextView phoneTitleTextView = itemLayout.findViewById(R.id.pay_title_text_view);
        TextView phoneNumberTextView = itemLayout.findViewById(R.id.pay_text_view);

        phoneTitleTextView.setText(item.title);
        phoneNumberTextView.setText(item.number);

        String pictureFileName = item.picture;

        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(pictureFileName);
            Drawable drawable = Drawable.createFromStream(stream, null);
            payImage.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();

            File pictureFile = new File(mContext.getFilesDir(), pictureFileName);
            Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
            payImage.setImageDrawable(drawable);
        }

        return itemLayout;
    }
}
