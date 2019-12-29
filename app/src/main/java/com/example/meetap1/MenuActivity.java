package com.example.meetap1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public SharedPreferences pref, prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Show status bar
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //loading the default fragment
        loadFragment(new BerandaFragment());

        BottomNavigationView navigation = findViewById(R.id.botNav);
        navigation.setOnNavigationItemSelectedListener(this);

        //SharedPreference

        pref = getSharedPreferences("id", MODE_PRIVATE);
        String idUser = pref.getString("IdUser3", null);
        SharedPreferences.Editor editorId = pref.edit();
        editorId.putString("IdUser4", idUser);
        editorId.commit();

        pref = getSharedPreferences("email", MODE_PRIVATE);
        String email1 = pref.getString("etemail", null);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email1", email1);
        editor.commit();

//        //bundle
//        Bundle bundleObject = new Bundle();
//        bundleObject.putString("dataId", idUser);
//        /*set Fragmentclass Arguments*/
//        Fragment fragmentobject = new Fragment();
//        fragmentobject.setArguments(bundleObject);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new BerandaFragment();
                break;

            case R.id.navigation_ask:
                fragment = new AskFragment();
                break;

            case R.id.navigation_member:
                fragment = new MemberFragment();
                break;

            case R.id.navigation_profil:
                fragment = new ProfilFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
