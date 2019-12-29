package com.example.meetap1;

import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.meetap1.ImagePicker.RxImageConverter;
import com.example.meetap1.ImagePicker.RxImagePicker;
import com.example.meetap1.ImagePicker.Sources;
import com.example.meetap1.Model.Category;
import com.example.meetap1.Model.User;
import com.example.meetap1.Utils.GlobalVars;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.meetap1.Utils.GlobalHelper.compressFoto;
import static com.example.meetap1.Utils.GlobalHelper.convertFileToContentUri;
import static com.example.meetap1.Utils.GlobalHelper.deleteRecursive;
import static com.example.meetap1.Utils.GlobalHelper.encodeFileBase64;
import static com.example.meetap1.Utils.GlobalHelper.getMimeTypeFromUri;
import static com.example.meetap1.Utils.GlobalHelper.getPath;
import static com.example.meetap1.Utils.GlobalVars.BASE_DIR;
import static com.example.meetap1.Utils.GlobalVars.EXTERNAL_DIR_FILES;
import static com.example.meetap1.Utils.GlobalVars.IMAGES_DIR;
import static java.lang.String.valueOf;

public class AskFragment extends Fragment {

    private Button btnPublish;
    private TextInputEditText tags;
    private TextInputEditText tiBody;
    private TextInputEditText tiTitle;
    private TextView tviduser;
    private ImageView addGallery;
    private ImageView addCamera;
    private ImageView imgTivket;
    private Spinner tags1;
    private RadioGroup converterRadioGroup;
    private static final int PICK_IMAGE_FILE = 1;
    private SharedPreferences pref, prf;

    private String id;
    private File tempFile= null;
    private File compressedImage = null;
    private Uri finalPhotoUri = null;
    private String photoExt = "";
    private String encodePhoto = "";
    private Bitmap theBitmap = null;
    private Uri photoUri;

    private String idUser;

    private static String TAG = AskFragment.class.getSimpleName();

    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        return inflater.inflate(R.layout.fragment_ask, null);
        View rootView = inflater.inflate(R.layout.fragment_ask, container, false);

        gson = new Gson();
        btnPublish = rootView.findViewById(R.id.btnPublish);
        tags1 = rootView.findViewById(R.id.tags1);
        tiBody = rootView.findViewById(R.id.tiBody);
        tiTitle = rootView.findViewById(R.id.tiTitle);
        tviduser = rootView.findViewById(R.id.tviduser);
        addGallery = rootView.findViewById(R.id.addGallery);
        addCamera = rootView.findViewById(R.id.addCamera);
        imgTivket = rootView.findViewById(R.id.imgTivket);

        pref = this.getActivity().getSharedPreferences("id",getActivity().MODE_PRIVATE);
        idUser = pref.getString("IdUser3", null);


        converterRadioGroup = rootView.findViewById(R.id.radio_group);
        converterRadioGroup.check(R.id.radio_file);

        if (RxImagePicker.with(getContext()).getActiveSubscription() != null) {
            RxImagePicker.with(getContext()).getActiveSubscription().subscribe(this::onImagePicked);
        }
        id = valueOf(System.currentTimeMillis());

        TextView iduser = rootView.findViewById(R.id.tviduser);
        //iduser.setText("1");


        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishTicket();
                Log.e(TAG,"User id : " +idUser);

            }
        });

        addCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromSource(Sources.CAMERA);
            }
        });

        addGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        loadCat();

        loadUser();

        return rootView;
    }


    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FILE);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null){
            photoUri = data.getData();
            Glide.with(this)
                    .load(photoUri)
                    .skipMemoryCache(true)
                    .into(imgTivket);
        }
    }

    private void pickImageFromSource(Sources source) {

        RxImagePicker.with(getContext()).requestImage(source)
                .flatMap(uri -> {
                    switch (converterRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_file:
                            return RxImageConverter.uriToFile(getActivity(), uri, createTempFile());
                        case R.id.radio_bitmap:
                            return RxImageConverter.uriToBitmap(getActivity(), uri);
                        default:
                            return Observable.just(uri);
                    }
                })
                .subscribe(this::onImagePicked, throwable -> Toast.makeText(getActivity(), String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }

    private File createTempFile() {
        return new File(BASE_DIR + EXTERNAL_DIR_FILES, id + ".jpeg");
    }

    private void onImagePicked(Object result) {
        if (result instanceof Bitmap) {
            //ivImage.setImageBitmap((Bitmap) result);
        }else{
            photoUri = Uri.parse(String.valueOf(result));

            tempFile = new File(String.valueOf(photoUri));

            compressedImage = compressFoto(getActivity(), tempFile);


            try {
                finalPhotoUri = convertFileToContentUri(getActivity(), compressedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

            photoExt = "."+getMimeTypeFromUri(getActivity(), finalPhotoUri);
            encodePhoto = encodeFileBase64(getPath(getActivity(), finalPhotoUri));

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }

                    try {
                        theBitmap = Glide.
                                with(getActivity()).
                                asBitmap().
                                load(getPath(getActivity(), finalPhotoUri))
                                .apply(RequestOptions.circleCropTransform()).
                                        into(100, 100).get();

                    } catch (final ExecutionException e) {
                        Log.e("TAG","ExecutionException " + e.getMessage());
                    } catch (final InterruptedException e) {
                        Log.e("TAG","InterruptedException " + e.getMessage());
                    }
                    return null;
                }
                @SuppressLint("WrongThread")
                @Override
                protected void onPostExecute(Void dummy) {
                    if (null != theBitmap) {
                        // The full bitmap should be available here
                        //ivAvatar.setImageBitmap(theBitmap);

                        File mypath=new File(IMAGES_DIR,id+".jpeg");

                        ContextWrapper cw = new ContextWrapper(getActivity());
                        // path to /data/data/yourapp/app_data/imageDir
                        // Create imageDir
                        //File mypath=new File(fotoPath,userId+".jpeg");

                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(mypath);
                            // Use the compress method on the BitMap object to write image to the OutputStream
                            theBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Glide.with(getContext())
                                .load(mypath)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.ic_person)
                                .into(imgTivket);

                        Log.d("TAG", "Image loaded");
                    };
                }
            }.execute();

            deleteRecursive(new File(String.valueOf(finalPhotoUri)));
            deleteRecursive(createTempFile());
            deleteRecursive(tempFile);

        }
    }


    public void loadCat() {

        AndroidNetworking.post(GlobalVars.BASE_IP +"ticket/tampilCategory")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Category> result = new ArrayList<>();

                        try {
                            if (result != null)
                                result.clear();

                            String message = response.getString("message");

                            if (message.equals("Category Ditemukan")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Category category = gson.fromJson(dataArr.getJSONObject(i).toString(), Category.class);
                                        result.add(category);
                                        Log.e(TAG, "Category >> " + category.getName());

                                    }
                                    setCategory(result);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "JSONException "+e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "ANError "+anError, Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private String catId = "0";
    private void setCategory(final List<Category> categoryList) {
        ArrayAdapter<Category> voteTypeAdapter = new ArrayAdapter<Category>(getContext(), R.layout.category_spinner, categoryList) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(categoryList.get(position).getName());

//                if (position == 0) {
//                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//                } else {
//                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_700));
//                }

                return textView;
            }



            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(categoryList.get(position).getName());

//                if (position==0){
//                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
//                }else{
//                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_700));
//                }
                return textView;
            }
        };

        tags1.setAdapter(voteTypeAdapter);
        tags1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catId = categoryList.get(i).getId();
                Log.e(TAG,"cateId "+categoryList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


    public void tagCategory(){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            jsonObject.get("id");
            jsonObject.get("name");

            jsonArray.put(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadUser() {

        AndroidNetworking.post("http://ask.meetap.id/api/profile/tampilProfile")
                .addBodyParameter("id", "1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<User> result = new ArrayList<>();
                        try {
                            if (result != null)
                                result.clear();

                            Log.e("tampil user", response.toString(1));

                            String message = response.getString("message");

                            if (message.equals("Profile Ditemuka")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);
                                tagCategory();

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        User user = gson.fromJson(dataArr.getJSONObject(i).toString(), User.class);
                                        result.add(user);
//                                        tviduser.setText(String.valueOf(user.getUser_id()));
                                    }
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


//    [{"img":"/assets/img_ticket/7/screenshot1.jpg","thumbnail":"1"},{"img":"/assets/img_ticket/7/screenshot2.jpg","thumbnail":"0"}]


    public void publishTicket() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray newArr = new JSONArray();

            jsonObject.put("title", tiTitle.getText().toString());
            jsonObject.put("content", tiBody.getText().toString());
            jsonObject.put("user_id", idUser);
            jsonObject.put("category_id", catId);
            jsonObject.put("images_name", id+photoExt);
            jsonObject.put("images_file", encodePhoto);

            newArr.put(jsonObject);
            Log.e("coba input = ", newArr.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "user_id" + tviduser.getText().toString());
        Log.e(TAG, "title" + tiTitle.getText().toString());
        AndroidNetworking.post("http://ask.meetap.id/api/ticket/insertTicket")
                .addJSONObjectBody(jsonObject)
//                .addBodyParameter("title", tiTitle.getText().toString())
//                .addBodyParameter("content", tiBody.getText().toString())
//                .addBodyParameter("user_id", tviduser.getText().toString())
//                .addBodyParameter("category_id", tags.getText().toString())

//                .addBodyParameter("title", tiTitle.getText().toString())
//                .addBodyParameter("title", tiTitle.getText().toString())
//                .addBodyParameter("title", tiTitle.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MenuActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "JSONEXceptions" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), "Gagal menambah data", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
