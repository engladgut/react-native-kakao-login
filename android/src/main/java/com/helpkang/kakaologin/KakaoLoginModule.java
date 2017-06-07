package com.helpkang.kakaologin;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.helpkang.kakaologin.mo.ReactKakaoLogin;
import com.kakao.auth.Session;

public class KakaoLoginModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private ReactKakaoLogin rkl;

    public KakaoLoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        initKakao();
    }

    @ReactMethod
    public void login(Promise promise){
        rkl.login(promise);
    }

    @ReactMethod
    public void logout(Promise promise){
        rkl.logout(promise);
    }

    public void initKakao() {
        if( this.rkl  != null) return;
        ReactApplicationContext reactContext = getReactApplicationContext();
        reactContext.addActivityEventListener(this);

//        Log.d("current context", reactContext.toString());
//        Log.d("current Activity", reactContext.getCurrentActivity().toString());

        this.rkl = new ReactKakaoLogin(reactContext);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initKakao();
        // KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 페북 로그인일 경우 처리 안 함.
        Bundle bundle = data.getExtras();
        if (bundle.get("com.facebook.LoginFragment:Result") != null) {
            return;
        }

        try {
            if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
                return;
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public String getName() {
        return "KakaoLoginModule";
    }
}
