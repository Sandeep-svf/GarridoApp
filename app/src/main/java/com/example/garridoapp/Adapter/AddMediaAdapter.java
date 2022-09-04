package com.example.garridoapp.Adapter;
import static com.example.garridoapp.Activity.Permission.checkPermissionCamera;
import static com.example.garridoapp.Activity.Permission.checkPermissionReadExternal;
import static com.example.garridoapp.Activity.Permission.checkPermissionReadExternal2;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.garridoapp.R;

public class AddMediaAdapter extends RecyclerView.Adapter<AddMediaAdapter.ViewHolder>{

    Context context;
    private static final int PICK_IMAGE = 100;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PIC_REQUEST = 3788;
    private static final int PICK_IMAGE_R = 178500;
    private static final int CAMERA_PIC_REQUEST_R = 34198;
    private Uri imageUri6;
    private ContentValues values6;
    private int PICK_IMAGE2_S;


    public AddMediaAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public AddMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.add_media_layout_adapter,parent,false);
        return new AddMediaAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AddMediaAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            Dialog uploadImage = new Dialog(context.getApplicationContext());
            uploadImage.requestWindowFeature(Window.FEATURE_NO_TITLE);
            uploadImage.setContentView(R.layout.add_id_update_dialog);
            TextView gallaryDialog = uploadImage.findViewById(R.id.gallaryDialog);
            TextView cameraDialog = uploadImage.findViewById(R.id.cameraDialog);
            uploadImage.show();
            Window window = uploadImage.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            gallaryDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openGallery();
                    uploadImage.dismiss();
                }
            });

            cameraDialog.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    pic_camera_open();
                    uploadImage.dismiss();
                }
            });

        });
    }

    private void pic_camera_open() {

        PackageManager packageManager = context.getPackageManager();

        boolean readExternal = checkPermissionReadExternal(context.getApplicationContext());
        boolean writeExternal = checkPermissionReadExternal2(context.getApplicationContext());
        boolean camera = checkPermissionCamera(context.getApplicationContext());

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                values6 = new ContentValues();
                values6.put(MediaStore.Images.Media.TITLE, "New Picture");
                values6.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri6 = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values6);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri6);
               // startActivityForResult(intent, CAMERA_PIC_REQUEST_R);
            }
        } else {
            Toast.makeText(context, "camera permission required", Toast.LENGTH_LONG).show();
            //requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }


    private void openGallery() {

        boolean readExternal = checkPermissionReadExternal(context.getApplicationContext());
        boolean camera = checkPermissionCamera(context.getApplicationContext());
        if (readExternal && camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(intent, PICK_IMAGE_R);
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               // requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
