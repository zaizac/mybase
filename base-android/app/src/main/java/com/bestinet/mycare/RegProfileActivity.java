package com.bestinet.mycare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bestinet.mycare.customs.CustomAlertDialog;
import com.bestinet.mycare.customs.ICustomsAlertDialog;
import com.bestinet.mycare.util.FileCompressor;
import com.bestinet.mycare.util.Utility;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.bestinet.mycare.constant.AppConstant.CAMERA_REQUEST;
import static com.bestinet.mycare.constant.AppConstant.REQUEST_GET_SINGLE_FILE;

public class RegProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgProfile;
    private CustomAlertDialog customAlertDialog;
    private File mPhotoFile = null;
    private FileCompressor fileCompressor;
    private String photo64;

    private AutoCompleteTextView autoMealType;
    private AutoCompleteTextView autoGender;
    private TextInputEditText etDob;

    private int mYear;
    private int mMonth;
    private int mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_profile);
        init();

        ArrayAdapter<String> adaptMealType = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.arrMealType));
        autoMealType.setAdapter(adaptMealType);
        autoMealType.setText(adaptMealType.getItem(0), false);
        autoMealType.setTextSize(14);

        ArrayAdapter<String> adaptGender = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.arrGender));
        autoGender.setAdapter(adaptGender);
        autoGender.setText(adaptGender.getItem(0), false);
        autoMealType.setTextSize(14);

        imgProfile.setOnClickListener(v -> {
//            dispatchTakePictureIntent();
            ICustomsAlertDialog iCustomsAlertDialog = new ICustomsAlertDialog() {
                @Override
                public void onCancel() {
                    customAlertDialog.dismissDialog();
                    dispatchTakePictureIntent();

                }

                @Override
                public void onOk() {
                    customAlertDialog.dismissDialog();
                    dispatchGalleryIntent();

                }

            };

            customAlertDialog = new CustomAlertDialog(iCustomsAlertDialog);
            customAlertDialog.showAlert(this, "Please select an option",
                    "Camera", "Gallery",
                    null);
        });

        etDob.setOnClickListener(this);
    }

    private void init() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile Info");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        fileCompressor = new FileCompressor(this);

        MaterialButton btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(this);
        }

        autoMealType = findViewById(R.id.autoMealType);
        autoGender = findViewById(R.id.autoGender);
        etDob = findViewById(R.id.etDob);
        imgProfile = findViewById(R.id.imgProfile);


    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GET_SINGLE_FILE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(mFileName, ".jpg", storageDir);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    mPhotoFile = fileCompressor.compressToFile(mPhotoFile);
                    Bitmap bmp = BitmapFactory.decodeFile(mPhotoFile.getPath());
                    if (bmp != null) {
                        Glide.with(this)
                                .load(bmp)
                                .into(imgProfile);
                        photo64 = Utility.convertBitmapToBase64(bmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_GET_SINGLE_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = fileCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    Bitmap bmp = BitmapFactory.decodeFile(mPhotoFile.getPath());
                    if (bmp != null) {
                        Glide.with(this)
                                .load(bmp)
                                .into(imgProfile);
                        photo64 = Utility.convertBitmapToBase64(bmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnNext:
                startActivity(new Intent(RegProfileActivity.this, RegAddressActivity.class));
                break;
            case R.id.etDob:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etDob.setText(String.format(Locale.getDefault(), "%d/%d/%d",
                                        dayOfMonth,
                                        monthOfYear + 1, year));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
