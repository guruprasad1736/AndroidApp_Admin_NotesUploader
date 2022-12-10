package com.guruprasad.notesuplaoder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.guruprasad.notesuplaoder.databinding.ActivityLabmanualsBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class labmanuals extends AppCompatActivity {
    Uri file_path;
    ActivityLabmanualsBinding binding ;
    FirebaseDatabase database ;
    FirebaseStorage storage ;
    StorageReference storageReference ;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    RadioButton radioButton ;

    String[] sem_name = {"Select","Sem-1","Sem-2","Sem-3","Sem-4","Sem-5","Sem-6"};
    String name = "";



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLabmanualsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        storageReference =FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin_lab_manual");

        TextView pagename = findViewById(R.id.page_name);
        pagename.setText("Upload Lab manual");

        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),navigation.class));
            finish();
        });

        binding.cancel.setOnClickListener(view -> {
            binding.cancel.setVisibility(View.INVISIBLE);
            binding.img1.setVisibility(View.INVISIBLE);
            binding.upload.setVisibility(View.INVISIBLE);
        });

       binding.browse.setOnClickListener(view -> {
           Dexter.withContext(view.getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                   .withListener(new PermissionListener() {
                       @Override
                       public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                           Intent intent = new Intent();
                           intent.setType("application/pdf");
                           intent.setAction(Intent.ACTION_GET_CONTENT);
                           startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 101);
                       }

                       @Override
                       public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                           Toast.makeText(labmanuals.this, "Please give the permission to browse the files", Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                           permissionToken.continuePermissionRequest();
                       }
                   }).check();
       });

       binding.upload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String name_of_manual = String.valueOf(binding.title.getText());

               if (TextUtils.isEmpty(name_of_manual))
               {
                    binding.title.setError("Enter the name of the lab manual");
                    Toast.makeText(labmanuals.this, "Enter the name of the lab manual", Toast.LENGTH_SHORT).show();
                   return;
               }
               else
               {
                   ProgressDialog pd = new ProgressDialog(view.getContext());
                    pd.setTitle("Lab Manual is Uploading");
                    pd.setMessage("PLease Wait ....");
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    StorageReference reference = storageReference.child("admin_lab_manual/"+name+".pdf");
                    reference.putFile(file_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    file_model filemodel = new file_model(binding.title.getText().toString(), uri.toString(), auth.getCurrentUser().getUid());

                                    if (binding.computerRadio.isChecked()  && binding.firstYear.isChecked() ) {
                                        databaseReference.child("computer").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.firstYear.isChecked())
                                    {
                                        databaseReference.child("computer").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.secondYear.isChecked())
                                    {
                                        databaseReference.child("computer").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.computerRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.computerRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.computerRadio.isChecked()  && binding.thirdYear.isChecked())
                                    {
                                        databaseReference.child("computer").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.computerRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.computerRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.computerRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("computer").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }

                                    // mechanical

                                    else if (binding.mechanicalRadio.isChecked()  && binding.firstYear.isChecked() ) {
                                        databaseReference.child("mechanical").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.firstYear.isChecked())
                                    {
                                        databaseReference.child("mechanical").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.secondYear.isChecked())
                                    {
                                        databaseReference.child("mechanical").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.mechanicalRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.mechanicalRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.mechanicalRadio.isChecked()  && binding.thirdYear.isChecked())
                                    {
                                        databaseReference.child("mechanical").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.mechanicalRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.mechanicalRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.mechanicalRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("mechanical").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }


                                    //civil


                                    else if (binding.civilRadio.isChecked()  && binding.firstYear.isChecked() ) {
                                        databaseReference.child("civil").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.firstYear.isChecked())
                                    {
                                        databaseReference.child("civil").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.secondYear.isChecked())
                                    {
                                        databaseReference.child("civil").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.civilRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.civilRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.civilRadio.isChecked()  && binding.thirdYear.isChecked())
                                    {
                                        databaseReference.child("civil").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.civilRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.civilRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.civilRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("civil").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }

                                    // IT





                                    else if (binding.ITRadio.isChecked()  && binding.firstYear.isChecked() ) {
                                        databaseReference.child("IT").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.firstYear.isChecked())
                                    {
                                        databaseReference.child("IT").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.secondYear.isChecked())
                                    {
                                        databaseReference.child("IT").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.ITRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.ITRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.ITRadio.isChecked()  && binding.thirdYear.isChecked())
                                    {
                                        databaseReference.child("IT").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.ITRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.ITRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.ITRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("IT").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }

//                                    E and TC





                                    else if (binding.eAndTcRadio.isChecked()  && binding.firstYear.isChecked() ) {
                                        databaseReference.child("E_and_TC").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.firstYear.isChecked())
                                    {
                                        databaseReference.child("E_and_TC").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.firstYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("first_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.secondYear.isChecked())
                                    {
                                        databaseReference.child("E_and_TC").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.secondYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("second_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else if (binding.eAndTcRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.eAndTcRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.eAndTcRadio.isChecked()  && binding.thirdYear.isChecked())
                                    {
                                        databaseReference.child("E_and_TC").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.eAndTcRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.eAndTcRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }
                                    else  if (binding.eAndTcRadio.isChecked()  && binding.thirdYear.isChecked() )
                                    {
                                        databaseReference.child("E_and_TC").child("third_year").child(databaseReference.push().getKey()).setValue(filemodel);
                                        binding.img1.setVisibility(View.INVISIBLE);
                                        binding.upload.setVisibility(View.INVISIBLE);
                                        binding.cancel.setVisibility(View.INVISIBLE);
                                        binding.title.setText("");
                                        pd.dismiss();
                                        Toast.makeText(labmanuals.this, "Lab manual is uploaded", Toast.LENGTH_SHORT).show();


                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(labmanuals.this, "Error : "+ e, Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float per = 100*snapshot.getBytesTransferred() /snapshot.getTotalByteCount();
                            pd.setMessage("File uploaded : "+(int)per+"%");
                        }
                    });


               }


           }
       });




    }
    public String get_file_name_from_uri(Uri filepath)
    {
        String result = null;
        if (filepath.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = filepath.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && resultCode==RESULT_OK)
        {
            file_path = data.getData();
            binding.img1.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.VISIBLE);
            binding.upload.setVisibility(View.VISIBLE);
            binding.title.setText(get_file_name_from_uri(file_path));
            name = get_file_name_from_uri(file_path);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),navigation.class));
        finish();
    }


}