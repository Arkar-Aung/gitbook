package com.arkar.apps.gitbook.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arkar.apps.gitbook.Config;
import com.arkar.apps.gitbook.R;
import com.arkar.apps.gitbook.model.Auth;
import com.arkar.apps.gitbook.model.LoginParam;
import com.arkar.apps.gitbook.network.LoginApi;
import com.arkar.apps.gitbook.util.PrefUtilis;

import rx.Subscription;
import rx.functions.Action1;

import static com.arkar.apps.gitbook.util.NetworkUtils.getRestAdapter;

public class LoginActivity extends ActionBarActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private ProgressDialog dialog;
    private Subscription mLoginSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, null, "Authenticating ! Please wait ..");
                login("Arkar-Aung", "!@#$%^&*(fF123");
//                if (! mUsername.getText().toString().isEmpty() &&
//                        ! mPassword.getText().toString().isEmpty()) {
//
//                    //mLoginSubscription = login(mUsername.getText().toString(), mPassword.getText().toString());
//
//                }
            }
        });
    }

    private Subscription login(String username, String password) {
        String credential = username + ":" + password;
        String authorization = "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
        LoginParam loginParam = new LoginParam(Config.CLIENT_SECRET, "From gitbook app");
        return getRestAdapter(Config.BASE_URL).create(LoginApi.class)
                .basicLogin(authorization, Config.CLIENT_ID, loginParam)
                .subscribe(new Action1<Auth>() {
                    @Override
                    public void call(Auth auth) {
                        dialog.dismiss();
                        if (auth.getToken() != null) {
                            PrefUtilis.setAuthenticated(LoginActivity.this, 1);
                            PrefUtilis.setOAuthToken(LoginActivity.this, auth.getToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mLoginSubscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
