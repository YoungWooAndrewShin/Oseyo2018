package user.com.openproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class LoginActivity extends AppCompatActivity {

    private SessionCallback callback;
    private CallbackManager callbackManager;
    private ImageView mRegisterBtn;
    private ImageView mLoginBtn;
    //db
    SQLiteDatabase mDb;
    String mDbName = "open.db";
    int mDbVersion = 1; // 데이터베이스 버전


    //kakao
    private com.kakao.usermgmt.LoginButton mKakaoLoginBtn;
    private ImageView mFakeKakaoLoginBtn;
    //facebook
    private LoginButton mFacebookLoginBtn;
    private ImageView mFakeFacebookLoginBtn;
    //naver
    private OAuthLoginButton mNaverLoginBtn;
    private ImageView mFakeNaverLoginBtn;
    private static OAuthLogin mOAuthLoginModule;
    private static final String OAUTH_CLIENT_ID = "K0ei99CGh0YOq0UgOaEZ";
    private static final String OAUTH_CLIENT_SECRET = "H4z_RayGTO";
    private static final String OAUTH_CLIENT_NAME = "Open project";

    final static String TAG = "LOGIN_ACTIVITY";
    final static String KAKAO_TAG = "KAKAO_LOGIN_API";
    final static String FACEBOOK_TAG = "FACEBOOK_LOGIN_API";
    final static String NAVER_TAG = "NAVER_LOGIN_API";
    final static String DB_TAG = "DB";

    private void setRes() {

        mLoginBtn = (ImageView)findViewById(R.id.LoginLoginBtn);
        mRegisterBtn = (ImageView)findViewById(R.id.LoginRegisterBtn);
        mKakaoLoginBtn = (com.kakao.usermgmt.LoginButton) findViewById(R.id.LoginKakaoBtn);
        mFakeKakaoLoginBtn = (ImageView)findViewById(R.id.LoginFakeKakaoBtn);
        mFacebookLoginBtn = (LoginButton)findViewById(R.id.LoginFacebookBtn);
        mFakeFacebookLoginBtn = (ImageView)findViewById(R.id.LoginFakeFacebookBtn);
        mNaverLoginBtn = findViewById(R.id.LoginNaverBtn);
        mFakeNaverLoginBtn = (ImageView)findViewById(R.id.LoginFakeNaverBtn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, PoliceActivity.class);
                startActivity(i);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        setKakao();
        setFacebook();
        setNaver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String hashKey = getKeyHash(this);
        Log.d(TAG, hashKey);

        setRes();

        GlobalVariable.gDeviceId = getUuid();

        Thread thread = new Thread() {
            @Override
            public void run() {

                String url = "http://49.247.202.54:3000/api/v1/auto_login/" + GlobalVariable.gDeviceId;
                Log.d("uuid url", url);

                try {
                    String responseString = sendGetMsg(url);
                    Log.d("login_response", responseString);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private String sendGetMsg(String url) throws URISyntaxException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        URI uri = new URI(url);
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(uri);
        HttpResponse response = httpClient.execute(httpGet);
        String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        return responseString;
    }

    private String getUuid() {
        String id = android.provider.Settings.System.getString(super.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return id;
    }

    private InputStream OpenHttpConnection(String strURL) throws IOException {

        URLConnection conn = null;
        InputStream inputStream = null;
        URL url = new URL(strURL);
        conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setRequestMethod("GET");
        httpConn.connect();

        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = httpConn.getInputStream();
        }

        return inputStream;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    //kakao session
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void setKakao() {
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

        mFakeKakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKakaoLoginBtn.performClick();
            }
        });

        mKakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SampleLoginActivity.class);
                startActivity(i);
            }
        });
    }



    private void setFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        mFakeFacebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFacebookLoginBtn.performClick();
            }
        });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken ,

                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.d("TAG","페이스북 로그인 결과" + response.toString());
                                try {
                                    String email = object.getString("email");       // 이메일
                                    String name = object.getString("name");         // 이름
                                    String gender = object.getString("gender");     // 성별
                                    Log.d("TAG","페이스북 이메일 -> " + email);
                                    Log.d("TAG","페이스북 이름 -> " + name);
                                    Log.d("TAG","페이스북 성별 -> " + gender);

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void setNaver() {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(this
                ,OAUTH_CLIENT_ID
                ,OAUTH_CLIENT_SECRET
                ,OAUTH_CLIENT_NAME);

        mFakeNaverLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaverLoginBtn.performClick();
            }
        });

        mNaverLoginBtn.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(LoginActivity.this);
                ProfileTask task = new ProfileTask();
                task.execute(accessToken);
                String refreshToken = mOAuthLoginModule.getRefreshToken(LoginActivity.this);
                long expiresAt = mOAuthLoginModule.getExpiresAt(LoginActivity.this);
                String tokenType = mOAuthLoginModule.getTokenType(LoginActivity.this);
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(LoginActivity.this).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(LoginActivity.this);
                Toast.makeText(LoginActivity.this, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    class ProfileTask extends AsyncTask<String, Void, String> {
        String result;
        @Override
        protected String doInBackground(String... strings) {
            String token = strings[0];// 네이버 로그인 접근 토큰;
            String header = "Bearer " + token; // Bearer 다음에 공백 추가
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                result = response.toString();
                br.close();
                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
            //result 값은 JSONObject 형태로 넘어옵니다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //넘어온 result 값을 JSONObject 로 변환해주고, 값을 가져오면 되는데요.
                // result 를 Log에 찍어보면 어떻게 가져와야할 지 감이 오실거에요.
                JSONObject object = new JSONObject(result);
                if(object.getString("resultcode").equals("00")) {
                    JSONObject jsonObject = new JSONObject(object.getString("response"));
                    Log.d(NAVER_TAG, jsonObject.toString());
                    Log.d(NAVER_TAG, jsonObject.getString("email"));
                    Log.d(NAVER_TAG, jsonObject.getString("name"));
                    Log.d(NAVER_TAG, jsonObject.getString("nickname"));
                    Log.d(NAVER_TAG, jsonObject.getString("profile_image"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
