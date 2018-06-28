package user.com.openproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.ui.view.OAuthLoginLayoutNaverAppDownloadBanner;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    Button mLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogoutBtn = (Button)findViewById(R.id.MainLogoutBtn);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kakaoLogout();
                facebookLogout();
                naverLogout();
            }
        });
    }

    private void naverLogout() {
        if(OAuthLogin.getInstance().getAccessToken(this) != null) {
            //로그인 되어있으면
            OAuthLogin.getInstance().logout(this);
            Toast.makeText(MainActivity.this, "네이버 로그아웃합니다.", Toast.LENGTH_SHORT).show();
        } else {
            //로그인 안된상태
        }
    }

    private void facebookLogout() {
        if(AccessToken.getCurrentAccessToken() != null) {
            //로그인 된상태
            GraphRequest.Callback callback = new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    try {
                        if(response.getError() != null) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "failed_to_deauth",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                        else if (response.getJSONObject().getBoolean("success")) {
                            LoginManager.getInstance().logOut();
                        }
                    } catch (JSONException ex) { }
                }
            };
            GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                    "me/permissions", new Bundle(), HttpMethod.DELETE, callback);
            request.executeAsync();
//            LoginManager.getInstance().logOut();
//            FacebookSdk.sdkInitialize(getApplicationContext());
//
            Toast.makeText(MainActivity.this, "페이스북 로그아웃합니다.", Toast.LENGTH_SHORT).show();
        } else {
            //로그인 안된상태
        }
    }

    private void kakaoLogout() {
        if (Session.getCurrentSession().isOpened()) {
            UserManagement.requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                    redirectLoginActivity();
                }
            });
            Toast.makeText(MainActivity.this, "카카오톡 로그아웃합니다.", Toast.LENGTH_SHORT).show();
            // 로그인 상태
        } else {
            // 로그인되어있지 않은 상태
        }
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
