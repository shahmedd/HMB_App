package com.unify.unifyapp.anyline;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unify.avanza.services.ServiceManager;
import com.unify.avanza.stores.InAppMemoryStore;
import com.unify.unifyapp.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import io.anyline.view.ScanView;

public class ScanUniversalIdResultActivity extends ScanningConfigurationActivity {

    private LinkedHashMap<String, String> resultMap = new LinkedHashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scan_view);
        RecyclerView recyclerView = findViewById(R.id.rv_results);
        Button confirmButton = findViewById(R.id.confirmation_button);
        ImageView controlImage = findViewById(R.id.control_image);
        ImageView controlImage2 = findViewById(R.id.control_image2);
        ImageView faceImageView = findViewById(R.id.face_image);
        TextView frontSideTextView = findViewById(R.id.text);
        TextView backSideTextView = findViewById(R.id.textBackImage);
        TextView fieldsInformationTextView = findViewById(R.id.tvFieldsInformation);
        backSideTextView.setVisibility(View.VISIBLE);
        frontSideTextView.setTypeface(Typeface.DEFAULT);
        backSideTextView.setTypeface(Typeface.DEFAULT);
        fieldsInformationTextView.setVisibility(View.VISIBLE);
        fieldsInformationTextView.setText(Html.fromHtml(getString(R.string.fields_selection)));
        fieldsInformationTextView.setMovementMethod(LinkMovementMethod.getInstance());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = getIntent().getExtras();
            String[] resultKeys = (String[]) intent.getSerializableExtra("resultDataKeys");
            String[] resultValues = (String[]) intent.getSerializableExtra("resultDataValues");
            resultMap = new LinkedHashMap<>();
            for (int i = 0; i < resultKeys.length; i++) {
                resultMap.put(resultKeys[i], resultValues[i]);
            }
            InAppMemoryStore.getInstance().updateDataWithoutNotify("scanUniversalId", resultMap);
            Bitmap bmp = BitmapUtil.getBitmap(extras.getString(Constant.SCAN_FULL_PICTURE_PATH));
            if (bmp == null) {
                frontSideTextView.setVisibility(View.VISIBLE);
                frontSideTextView.setText(Html.fromHtml(
                        String.format(getResources().getString(R.string.card_front), getString(R.string.not_available))));
                controlImage.setVisibility(View.GONE);
            } else {
                frontSideTextView.setVisibility(View.GONE);
                controlImage.setVisibility(View.VISIBLE);
                controlImage.setImageBitmap(bmp);
            }

            Bitmap bmp2 = BitmapUtil.getBitmap(extras.getString(Constant.SCAN_FULL_PICTURE_PATH2));
            if (bmp2 == null) {
                backSideTextView.setVisibility(View.VISIBLE);
                backSideTextView.setText(Html.fromHtml(
                        String.format(getResources().getString(R.string.card_back), getString(R.string.not_available))));
                controlImage2.setVisibility(View.GONE);
            } else {
                backSideTextView.setVisibility(View.GONE);
                controlImage2.setVisibility(View.VISIBLE);
                controlImage2.setImageBitmap(bmp2);
            }

            Bitmap faceBmp = BitmapUtil.getBitmap(extras.getString(Constant.SCAN_FACE_PICTURE_PATH));
            if (faceBmp == null) {
                faceImageView.setVisibility(View.GONE);
            } else {
                faceImageView.setVisibility(View.VISIBLE);
                faceImageView.setImageBitmap(faceBmp);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        BaseGridAdapter adapter = new BaseGridAdapter(this, resultMap); //sortedData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected ScanView getScanView() {
        return null;
    }

    @Override
    protected ScanModuleEnum.ScanModule getScanModule() {
        return null;
    }


    private void shareData() {
        Map<String, String> filesMap = new HashMap<>();
        int i = 1;

        if (getIntent().getStringExtra(Constant.SCAN_FACE_PICTURE_PATH) != null) {
            filesMap.put(getString(R.string.native_share_image_name_prefix, String.valueOf(i)), getIntent().getStringExtra(Constant.SCAN_FACE_PICTURE_PATH));
            i++;
        }

        if (getIntent().getStringExtra(Constant.SCAN_FULL_PICTURE_PATH) != null) {
            filesMap.put(getString(R.string.native_share_image_name_prefix, String.valueOf(i)), getIntent().getStringExtra(Constant.SCAN_FULL_PICTURE_PATH));
            i++;
        }

        if (getIntent().getStringExtra(Constant.SCAN_FULL_PICTURE_PATH2) != null) {
            filesMap.put(getString(R.string.native_share_image_name_prefix, String.valueOf(i)), getIntent().getStringExtra(Constant.SCAN_FULL_PICTURE_PATH2));
        }

        try {
            Intent shareIntent = ShareDataIntentCreator.INSTANCE.createIntent(resultMap, filesMap, this);
            startActivity(shareIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
