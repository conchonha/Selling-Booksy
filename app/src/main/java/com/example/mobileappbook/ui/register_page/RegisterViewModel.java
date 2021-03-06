package com.example.mobileappbook.ui.register_page;

import android.app.Application;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobileappbook.utils.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends AndroidViewModel {
    private FirebaseAuth mAuth;
    //MutableLiveData dung de lang nghe va quan sat su thay doi cua du lieu
    //setvalue - post value
    private MutableLiveData<Boolean> mCheckShowDialog = new MutableLiveData<>();
    //Repositories

    public RegisterViewModel(Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }

    public Boolean checkValidationRegister(EditText edtName, EditText edtPhone,EditText edtAddress,EditText edtEmail,EditText edtConfirmPass,EditText edtPass){
        if(Validations.isValidName(edtName.getText().toString())){
            edtName.setError("Name not is validation");
            return false;
        }
        edtName.setError(null);

        if(!Validations.isValidPhoneNumber(edtPhone.getText().toString())){
            edtPhone.setError("edtPhone not is validation");
            return false;
        }
        edtPhone.setError(null);

        if(Validations.isValidAddress(edtAddress.getText().toString())){
            edtAddress.setError("edtAddress not is validation");
            return false;
        }
        edtAddress.setError(null);

        if(Validations.isEmailValid(edtEmail.getText().toString())){
            edtEmail.setError("edtEmail not is validation");
            return false;
        }
        edtEmail.setError(null);

        if(Validations.isPasswordValid(edtPass.getText().toString())){
            edtPass.setError("edtPass not is validation");
            return false;
        }
        edtPass.setError(null);

        if(edtConfirmPass.getText().toString().equals("") || !edtConfirmPass.getText().toString().equals(edtPass.getText().toString())){
            edtConfirmPass.setError("edtConfirmPass not is validation");
            return false;
        }
        edtConfirmPass.setError(null);


        return true;
    }

    public void registerUser(String email, String pass) {
        /*
            trong android thread chia l??m 2 lo???i l?? main thread v?? worker thread
            main thread c??n ???????c g???i l?? ui thread ( asyncTask, Runnable..)
            worker thread l?? thread do ng?????i d??ng t??? t???o (Thread...) th?????ng g???i l?? background thread
            trong khi t???o background thread y??u c???u
             - kh??ng block ui
             - kh??ng l??m vi???t v???i b??? c??ng c??? took kit android.. g?? ???? qu??n m???t
             v?? v???y ????? update dc ui trong background thread andoid cung c???p cho ch??ng ta nh???ng ph????ng th???c
             view.post(runnable)
             handler.post(runnable)
             runOnUiThread();
         */
       new Thread(new Runnable() {
           @Override
           public void run() {
               if (mAuth.getCurrentUser() != null) {
                   mAuth.signOut();
               }
               mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(Task<AuthResult> task) {
                       setCheckShowDialog(false);
                       if (task.isSuccessful()) {
                           Toast.makeText(getApplication(), "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(getApplication(), "????ng k?? th???t b???i", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       }).start();
    }

    public LiveData<Boolean> getCheckShowDialog() {
        return mCheckShowDialog;
    }

    public void setCheckShowDialog(Boolean bool) {
        mCheckShowDialog.setValue(bool);
    }
}
