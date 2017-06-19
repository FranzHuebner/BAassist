package edu.ba.baassist;

 //Login to get the userdata and submit them to www.campus-dual.de

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import edu.ba.fragments.MainFragment;
import edu.ba.fragments.StatusFragment;


public class LoginActivity extends AppCompatActivity {

    //params
    private UserLoginTask mAuthTask = null;

    // UI elements
    private AutoCompleteTextView mUsername;
    private EditText mHashView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = (AutoCompleteTextView) findViewById(R.id.username);
        mHashView = (EditText) findViewById(R.id.hash);
        mHashView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        if (mAuthTask != null) return;

        // Reset errors.
        mUsername.setError(null);
        mHashView.setError(null);

        // Store value for the next steps.
        String username = mUsername.getText().toString();
        String hash = mHashView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid hash.
        if (!TextUtils.isEmpty(hash) && !isPasswordValid(hash)) {
            mHashView.setError(getString(R.string.error_invalid_hash));
            focusView = mHashView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (!isUserValid(username)) {
            mUsername.setError(getString(R.string.error_invalid_email));
            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            //Error!
            focusView.requestFocus();
        } else {
            //Perform login taskchain
            showProgress(true);
            mAuthTask = new UserLoginTask(username, hash);
            mAuthTask.execute((Void) null);
        }
    }

    //Checks for possibility based on www.campus-dual.de
    //Username needs to be integer only.
    private boolean isUserValid(String username) {
        return (!username.matches(".*[a-z].*"));
    }

    //Checks for possibility based on www.campus-dual.de
    //Hash need to be 32 digits.
    private boolean isPasswordValid(String password) {
        return (password.length() == 32);
    }

    //Check running version for visibility
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    //Async tasks to pull dta from web to our client.
    //Does not interferer with our main thread.
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String mHash;

        //Function params.
        UserLoginTask(String email, String hash) {
            username = email;
            mHash = hash;
        }

        //Actual request.
        @Override
        protected Boolean doInBackground(Void... params) {

            boolean reachable = false;

            //Check if campus dual is working && login with provided hash and userid.
            try {
                reachable = connAdapter.isReachable("https://erp.campus-dual.de/sap/bc/webdynpro/sap/zba_initss?uri=https%3a%2f%2fselfservice.campus-dual.de%2findex%2flogin&sap-client=100&sap-language=DE#");
                } catch (IOException e) {
                e.printStackTrace();
            }

            boolean UserCheck =connAdapter.logInconnection(username,mHash);

            //Set personal-values global for later usage.
            connAdapter.setGlobalHash(mHash);
            connAdapter.setGlobalId(username);

            //Return true if successful or false if something failed.
            return (reachable && UserCheck);
        }

        //After Login.
        @Override
        protected void onPostExecute(final Boolean success) {
            //Reset params.
            mAuthTask = null;
            showProgress(false);

            //Check if login was successful.
            if (success) {

                //Set times for the url.
                String startT = String.valueOf(connAdapter.StartTime());
                String endT = String.valueOf(connAdapter.EndTime(Long.valueOf(startT)+3024000));

                String output="";
                try {
                    output= new timetableTask(connAdapter.getGlobalId(),connAdapter.getGlobalHash(),startT,endT)
                            .execute()
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                //Set global value of the cal
                connAdapter.setUserCalc(output);

                String output2="";
                try {
                    output2= new Semestertask(connAdapter.getGlobalId(),connAdapter.getGlobalHash())
                            .execute()
                            .get();

                    String[] status= output2.split("\n");

                    connAdapter.setUserFs(status[0].replaceAll("\"",""));
                    connAdapter.setUserCredits(status[1]);

                    connAdapter.setUserExams(status[2]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                //Everything is fine, now go to the next activity.
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            } else {

                //after wrong connection show dialog
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Fehler bei der Verbindung");
                alertDialog.setMessage("Es ist ein Fehler bei der Verbindung zu Campus-Dual aufgetreten!" +
                        " Bitte überprüfe deine Userid und deinen Userhash." +
                        " Für weitere Fragen nutze bitte:");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }});
                alertDialog.show();
            }
        }

        //Canceled login.
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public class timetableTask extends AsyncTask<Void, Void,String> {

        //Params.
        private String userName;
        private String hashValue;
        private String startTime;
        private String endTime;

        //Sets.
        timetableTask(String id, String hash, String start, String end){
            userName=id;
            hashValue=hash;
            startTime=start;
            endTime=end;
        }

        //get Content
        @Override
        protected String doInBackground(Void...params){
            String url = connAdapter.getcal(userName,hashValue, startTime, endTime);
            return url;
        }

    }

    public class Semestertask extends AsyncTask<Void, Void,String> {

        //Params.
        private String userName;
        private String hashValue;

        //Sets.
        Semestertask(String id, String hash) {
            userName = id;
            hashValue = hash;
        }

        //Get content.
        @Override
        protected String doInBackground(Void... params) {
            String url = connAdapter.getsemester(userName, hashValue) + connAdapter.getcredits(userName, hashValue) + connAdapter.getexams(userName, hashValue);
            return url;
        }
    }
}