package com.mmc.library.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mmc.library.R;
import com.mmc.library.base.BaseActivity;
import com.mmc.library.bean.User;
import com.mmc.library.ui.presenters.LoginPresenters;
import com.mmc.library.ui.presenters.MainPresenters;
import com.mmc.library.ui.presenters.base.BaseView;
import com.mmc.library.ui.presenters.base.Message;
import com.mmc.library.utils.Cache;
import com.mmc.library.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HM on 2017/2/21.
 */
public class LoginActivity extends BaseActivity<LoginPresenters> implements BaseView{

    @BindView(R.id.login_account)
    EditText login_account;

    @BindView(R.id.login_user_password)
    EditText login_user_password;

    @BindView(R.id.login_forgetBtn)
    TextView login_forgetBtn;

    @BindView(R.id.login_registerBtn)
    TextView login_registerBtn;


    ProgressDialog pdg = null;
    @BindView(R.id.login_loginBtn)
    Button login_LoginBtn;
    @OnClick(R.id.login_loginBtn)
    void login(){
        String account = login_account.getText().toString().trim();
        String password = login_user_password.getText().toString().trim();
        if(account.equals("")) {
            showToast(getString(R.string.input_account_null));
        }else if(password.equals("")) {
            showToast(getString(R.string.input_password_null));
        } else {
            pdg = ProgressDialog.show(this,"","正在登录。。。");
            mPresenter.login(Message.obtain(this,account,password));
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
    }

    MainPresenters mainPresenters;
    @Override
    protected LoginPresenters getPresenter() {
        mainPresenters = new MainPresenters();
        return new LoginPresenters();
    }

    @Override
    protected Cache getCache() {
        return Cache.get(this);
    }

    /*注册*/
    private void register() {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    // 忘记密码
    private void forget() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message msg) {
        if(pdg != null)
            pdg.dismiss();
        switch (msg.what) {
            case Constant.LOGIN_FAILD_CODE:
                showToast("登录失败");
                finish();
                break;
            case Constant.LOGIN_SUCCUSE_CODE:
                User user = (User)msg.obj;
                getCache().put("user",user);
                finish();
                break;
        }
    }
}
