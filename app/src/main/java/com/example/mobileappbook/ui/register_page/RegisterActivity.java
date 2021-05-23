package com.example.mobileappbook.ui.register_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.mobileappbook.R;
import com.example.mobileappbook.app.MyApplication;
import com.example.mobileappbook.utils.Validations;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RegisterViewModel mRegisterViewModel;
    private EditText mEdtName,mEdtPhone,mEdtAddress,mEdtEmail,mEdtConfirmPass,mEdtPassWord;
    private ProgressDialog progressDialog; //tao dialog

    //nếu ko dùng mvvm thì mặc định theo bình thường thì activity chính là controller sử dụng mvc
    //onCreate -> chỉ dc khởi tạo 1 lần, nó không bị reload data khi nguwoif dùng xoay màn hình
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        listenerOnclick();
        initViewModel();
    }

    private void initViewModel() {
        mRegisterViewModel = new ViewModelProvider(this, MyApplication.factory).get(RegisterViewModel.class);
        mRegisterViewModel.getCheckShowDialog().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressDialog.show();
                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }
    //alo

    private void listenerOnclick() {
        findViewById(R.id.mBtnRegister).setOnClickListener(this::onClick);
    }

    private void initView() {
        mEdtName = findViewById(R.id.mEdtname);
//        mEdtName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(Validations.isValidName(mEdtName.getText().toString())){
//                    mEdtName.setError("Name is validation");
//                }else{
//                    mEdtName.setError(null);
//                }
//            }
//        });
        mEdtPhone = findViewById(R.id.mEdtPhone);
        mEdtAddress = findViewById(R.id.mEdtAddress);
        mEdtEmail = findViewById(R.id.mEdtEmail);
        mEdtConfirmPass = findViewById(R.id.mEdtConfirmPass);
        mEdtPassWord = findViewById(R.id.mEdtPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mBtnRegister:
                if(mRegisterViewModel.checkValidationRegister(mEdtName,mEdtPhone,mEdtAddress,mEdtEmail,mEdtConfirmPass,mEdtPassWord)){
                    mRegisterViewModel.setCheckShowDialog(true);
                    mRegisterViewModel.registerUser(mEdtEmail.getText().toString(),mEdtPassWord.getText().toString());
                }
                break;
        }
    }
}