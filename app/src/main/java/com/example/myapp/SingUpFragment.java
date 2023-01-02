package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SingUpFragment extends Fragment {
    private RegisterActivity _activity;
    private TextView alreadyHaveAnAccount;

    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;

    private ImageButton closeBtn;
    private Button singUpBtn;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    // private String passwordPattern = "[(?=.*[0-9]) + (?=.*[a-z])(?=.*[A-Z])+(?=.*[@#$%^&+=])+(?=\\S+$).{8,20}$]";
    public static boolean disableCloseBtn = false;

    public SingUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        _activity = (RegisterActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_sing_up, container, false );
        alreadyHaveAnAccount = view.findViewById( R.id.tv_already_have_an_account );
        email = view.findViewById( R.id.sign_up_email );
        password = view.findViewById( R.id.sign_up_password );
        fullName = view.findViewById( R.id.sign_up_fullname );
        confirmPassword = view.findViewById( R.id.sign_up_confirmpassword );

        closeBtn = view.findViewById( R.id.sign_up_close_btn );
        singUpBtn = view.findViewById( R.id.sign_up_btn );
        progressBar = view.findViewById( R.id.sing_up_pb );

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (disableCloseBtn){
            closeBtn.setVisibility( View.GONE );
        }else{
            closeBtn.setVisibility( View.VISIBLE );
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        alreadyHaveAnAccount.setOnClickListener( view1 -> {

            _activity.onBackPressed();
        } );
        closeBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();
            }
        } );
        email.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        fullName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        password.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        confirmPassword.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        singUpBtn.setOnClickListener( view12 -> checkEmailAndPassword() );
    }

    @SuppressLint("ResourceAsColor")
    private void checkInputs() {
        if (!TextUtils.isEmpty( email.getText() )) {
            if (!TextUtils.isEmpty( fullName.getText() )) {
                if (!TextUtils.isEmpty( password.getText() ) && password.length() >= 8) {
                    if (!TextUtils.isEmpty( confirmPassword.getText() )) {
                        singUpBtn.setEnabled( true );
                        singUpBtn.setTextColor( Color.rgb( 255, 255, 255 ) );
                    } else {
                        singUpBtn.setEnabled( false );
                        singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                    }
                } else {
                    singUpBtn.setEnabled( false );
                    singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                }
            } else {
                singUpBtn.setEnabled( false );
                singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
            }
        } else {
            singUpBtn.setEnabled( false );
            singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
        }
    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches( emailPattern )) {
            if ((password.getText().toString().equals( (confirmPassword.getText().toString()) ))) {
                progressBar.setVisibility( getView().VISIBLE );
                singUpBtn.setEnabled( false );
                singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                firebaseAuth.createUserWithEmailAndPassword( email.getText().toString(), password.getText().toString() )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put( "fullName", fullName.getText().toString() );

                                    firebaseFirestore.collection( "USER" )
                                            .add( userdata )
                                            .addOnCompleteListener( new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        mainIntent();
                                                    } else {
                                                        progressBar.setVisibility( View.INVISIBLE );
                                                        singUpBtn.setEnabled( true );
                                                        singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText( getActivity(), error, Toast.LENGTH_SHORT ).show();
                                                    }
                                                }
                                            } );

                                } else {
                                    progressBar.setVisibility( View.INVISIBLE );
                                    singUpBtn.setEnabled( true );
                                    singUpBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                                    String error = task.getException().getMessage();
                                    Toast.makeText( getActivity(), error, Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );

            } else {
                confirmPassword.setError( "password doesn't matched!" );
            }
        } else {
            email.setError( "Invalid Email!" );
        }
    }

    private void mainIntent() {
        if (disableCloseBtn) {
            disableCloseBtn = false;
            getActivity().finish();
        } else {
            Intent mainIntent = new Intent( getActivity(), MainActivity.class );
            startActivity( mainIntent );
            getActivity().finish();
        }
    }
}