package com.example.video_0927;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.video_0927.api.Api;
import com.example.video_0927.api.ApiRequest;
import com.example.video_0927.entity.response.LoginResponse;
import com.example.video_0927.ui.BaseActivity;
import com.example.video_0927.ui.HomeActivity;
import com.example.video_0927.util.GsonUtil;
import com.example.video_0927.util.SharedPreferencesUtil;
import com.example.video_0927.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * 9.27 Video
 */
public class LoginActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView btnSubmit;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    @Override
    protected void initListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    protected void initData() {
        etUsername.setText("13612345678");
        etPassword.setText("admin");
    }

    /**
     * 登录
     */
    private void login() {
        //拿到输入的账户名和密码
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            ToastUtil.show(this, "用户名和密码不能为空");
            return;
        }


        //连接Api  post    body     需要url
        HashMap<String,Object>params = new HashMap<>();
        params.put("mobile",username);
        params.put("password", password);
        ApiRequest.newPost(Api.LOGIN,params).enqueue(new ApiRequest.CallBack() {
            @Override
            public void onFailure(IOException e) {
                ToastUtil.show(LoginActivity.this,"登录失败");
            }

            @Override
            public void onResponse(String string) {
              //解析json
                LoginResponse loginResponse = GsonUtil.fromJson(string, LoginResponse.class);
                if(loginResponse.isSuccess()){
                    //返回值=0
                    //保存token
                    SharedPreferencesUtil.save("token",loginResponse.token);
                    ToastUtil.show(LoginActivity.this,"登录成功");

                 startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                 finish();

                }else{
                    ToastUtil.show(LoginActivity.this,loginResponse.msg);
                }




            }
        });





    }

}