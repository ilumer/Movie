package com.example.root.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.root.movie.BrowserActivity;
import com.example.root.movie.R;
import com.example.root.movie.constant.MovieConstant;
import com.example.root.movie.dao.Movie;
import com.example.root.movie.net.DBAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.sign_up)
    Button mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void Login(){
        final Intent i = new Intent(this, BrowserActivity.class);
        startActivityForResult(i, MovieConstant.LOGINA_RQUEST_CODE);
    }

    @OnClick(R.id.sign_up)
    public void Signup(){
        Uri uri = Uri.parse(DBAPI.SING_UP);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MovieConstant.LOGINA_RQUEST_CODE){
            if (resultCode==RESULT_OK){
                String token = data.getStringExtra(MovieConstant.EXTRA_TOKEN);
                Log.e(TAG,token);
            }
        }
    }
}
