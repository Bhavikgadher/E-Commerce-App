package com.example.myapp;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class RestPasswordFragment extends Fragment {
    public RestPasswordFragment() {
        // Required empty public constructor
    }

    private RegisterActivity _activity;
    private EditText registerEmail;
    private Button restPasswordBtn;
    private TextView goBack;
    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rest_password, container, false);

        registerEmail = v.findViewById(R.id.Forgot_password_email);
        restPasswordBtn = v.findViewById(R.id.rest_password_btn);
        goBack = v.findViewById(R.id.tv_forgot_password_go_back);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        _activity = (RegisterActivity) requireActivity();
        firebaseAuth = FirebaseAuth.getInstance();
        emailIconContainer = v.findViewById(R.id.forgot_password_email_icon_container);
        emailIcon = v.findViewById(R.id.forgot_password_email_icon);
        emailIconText = v.findViewById(R.id.forgot_password_email_icon_text);
        progressBar = v.findViewById(R.id.forgot_password_pb);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerEmail.addTextChangedListener(new TextWatcher() {
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
        });
        restPasswordBtn.setOnClickListener(view12 -> {

            TransitionManager.beginDelayedTransition(emailIconContainer);
            emailIconText.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(emailIconContainer);
            emailIcon.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);


            restPasswordBtn.setEnabled(false);
            restPasswordBtn.setTextColor(Color.argb(50, 250, 250, 250));
            firebaseAuth.sendPasswordResetEmail(registerEmail.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, emailIcon.getWidth() / 2, emailIcon.getHeight() / 2);
                            scaleAnimation.setDuration(100);
                            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                            scaleAnimation.setRepeatMode(Animation.REVERSE);
                            scaleAnimation.setRepeatCount(1);
                            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    emailIconText.setText("Recovery Email Sent Successfully!");
                                    emailIconText.setTextColor(getResources().getColor(R.color.green));
                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIcon.setVisibility(View.VISIBLE);
                                    emailIconText.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                    emailIcon.setImageResource(R.drawable.ic_baseline_mark_email_read_24);
                                }
                            });
                            emailIcon.startAnimation(scaleAnimation);
                        } else {
                            String error = task.getException().getMessage();
                            restPasswordBtn.setEnabled(true);
                            restPasswordBtn.setTextColor(Color.rgb(255, 255, 255));
                            emailIconText.setText(error);
                            emailIconText.setTextColor(getResources().getColor(R.color.red));
                            TransitionManager.beginDelayedTransition(emailIconContainer);
                            emailIconText.setVisibility(View.VISIBLE);
                        }
                        progressBar.setVisibility(View.GONE);
                    });
        });
        goBack.setOnClickListener(view1 -> _activity.replaceFragment(new SingInFragment(), true));
    }//

    private void checkInputs() {
        if (TextUtils.isEmpty(registerEmail.getText())) {
            restPasswordBtn.setEnabled(false);
            restPasswordBtn.setTextColor(Color.argb(50, 255, 255, 255));
        } else {
            restPasswordBtn.setEnabled(true);
            restPasswordBtn.setTextColor(Color.rgb(255, 255, 255));
        }
    }
}
