package com.example.mygooglelogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "GoogleSignIn";
    private static final int RC_SIGN_IN = 9001;

    // ⚠️ GANTI dengan Web Client ID dari Google Cloud Console
    private static final String WEB_CLIENT_ID =
            "YOUR_WEB_CLIENT_ID.apps.googleusercontent.com";

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnGoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnGoogleSignIn = findViewById(R.id.btn_google_signin);
        btnGoogleSignIn.setSize(SignInButton.SIZE_WIDE);
        btnGoogleSignIn.setColorScheme(SignInButton.COLOR_LIGHT);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogleSignIn.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                String idToken = account.getIdToken();
                String email = account.getEmail();
                String displayName = account.getDisplayName();
                String photoUrl = (account.getPhotoUrl() != null)
                        ? account.getPhotoUrl().toString() : "";

                Log.d(TAG, "Sign-in success: " + email);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("name", displayName);
                intent.putExtra("photo", photoUrl);
                intent.putExtra("idToken", idToken);
                startActivity(intent);
                finish();
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            if (e.getStatusCode() == 12501) {
                Toast.makeText(this, "Login dibatalkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Login gagal: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("email", account.getEmail());
            intent.putExtra("name", account.getDisplayName());
            startActivity(intent);
            finish();
        }
    }
              }
