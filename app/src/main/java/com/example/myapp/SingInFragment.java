package com.example.myapp;

import static com.example.myapp.RegisterActivity.onRestPassword;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

public class SingInFragment extends Fragment {

    private RegisterActivity _activity;

    private EditText email;
    private EditText password;
    private ImageButton closeBtn;
    private Button singInBtn;
    private TextView forgotPassword;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private TextView dontHaveAnAccount;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    //private String passwordPattern = "[(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20}]";

    static boolean disableCloseBtn = false;

    public SingInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        _activity = (RegisterActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_sing_in, container, false );
        dontHaveAnAccount = view.findViewById( R.id.tv_dont_have_an_account );
        email = view.findViewById( R.id.sign_in_email );
        password = view.findViewById( R.id.sign_in_password );
        closeBtn = view.findViewById( R.id.sign_in_close_btn );
        forgotPassword = view.findViewById( R.id.sign_in_forgotpassword );
        singInBtn = view.findViewById( R.id.sign_in_btn );
        progressBar = view.findViewById( R.id.sing_in_pb );
        firebaseAuth = FirebaseAuth.getInstance();

        if (disableCloseBtn) {
            closeBtn.setVisibility( View.GONE );
        } else {
            closeBtn.setVisibility( View.VISIBLE );
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        dontHaveAnAccount.setOnClickListener( view1 -> {
            Log.e( "LOG_LOG", "CLICK dontHaveAnAccount" );
            _activity.replaceFragment( new SingUpFragment(), true );

        } );
        forgotPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestPassword = true;
                _activity.replaceFragment( new RestPasswordFragment(), true );
            }

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
        singInBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        } );
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty( (email.getText()) )) {
            if (!TextUtils.isEmpty( (password.getText()) )) {
                singInBtn.setEnabled( true );
                singInBtn.setTextColor( Color.WHITE );
            } else {
                singInBtn.setEnabled( false );
                singInBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
            }
        } else {
            singInBtn.setEnabled( false );
            singInBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
        }
    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches( emailPattern )) {
            if (password.length() >= 8) {
                progressBar.setVisibility( View.VISIBLE );
                singInBtn.setEnabled( false );
                singInBtn.setTextColor( Color.argb( 50, 255, 255, 255 ) );
                firebaseAuth.signInWithEmailAndPassword( email.getText().toString(), password.getText().toString() )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mainIntent();
                                } else {
                                    progressBar.setVisibility( View.INVISIBLE );
                                    singInBtn.setEnabled( true );
                                    singInBtn.setTextColor( Color.rgb( 255, 255, 255 ) );
                                    String error = task.getException().getMessage();
                                    Toast.makeText( _activity, error, Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );

            } else {
                Toast.makeText( _activity, "Incorrect email or password!", Toast.LENGTH_SHORT ).show();
            }
        } else {
            Toast.makeText( _activity, "Incorrect email or password!", Toast.LENGTH_SHORT ).show();
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