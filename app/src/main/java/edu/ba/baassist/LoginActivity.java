package edu.ba.baassist;

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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Login activity to check if the user has an account on campus-dual.
 * Use the given adapters to evaluate if there is a cache and if he is online.
 * Goto MainActivity after completion.
 */


public class LoginActivity extends AppCompatActivity {

    //Params.
    private UserLoginTask mAuthTask = null;

    // UI elements.
    private AutoCompleteTextView mUserId;
    private EditText mHashView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set context for our adapter.
        CacheAdapter.fileContext =  getApplicationContext();

        //Set up the login form.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mUserId = (AutoCompleteTextView) findViewById(R.id.username);
        mHashView = (EditText) findViewById(R.id.hash);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //Actionlistener for HashView.
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

        //Start the actual login-process with an onclick listener.
        mSignInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    //Automate the login process.
    @Override
    protected void onStart() {
        super.onStart();
        if(checkCache()){
            try {
                getOtherData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    //Check  if necessary data is inside of cache.
    private boolean checkCache(){
        boolean exist1 = new CacheAdapter().getFileExistence("userGlobal");
        boolean exist2 = new CacheAdapter().getFileExistence("HashGlobal");
        boolean exist3 = new CacheAdapter().getFileExistence("userExams");
        boolean exist4 = new CacheAdapter().getFileExistence("userCredits");
        boolean exist5 = new CacheAdapter().getFileExistence("userFs");
        boolean exist6 = new CacheAdapter().getFileExistence("userCal");

        if(exist1 && exist2 && exist3 && exist4 && exist5 && exist6){
            try {
                getLoginData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }else {
            return false;
        }
    }
    //Get the login-data from cache.
    private static void getLoginData () throws FileNotFoundException {
        String user =new CacheAdapter().getUserGlobalfromMem();
        String hash = new CacheAdapter().getHashGlobalfromMem();
        ConnAdapter.setGlobalId(user);
        ConnAdapter.setGlobalHash(hash);
    }

    //Get other information from cache.
    private static void getOtherData() throws FileNotFoundException{
        String cal =new CacheAdapter().getCalfromMem();
        String fs  =new CacheAdapter().getFsfromMem();
        String exams=new CacheAdapter().getExamsfromMem();
        String credits= new CacheAdapter().getCreditsfromMem();
        String GroupFilter = new CacheAdapter().getFilterfromMem();

        if(GroupFilter.equals("Datei nicht vorhanden.")){
            ConnAdapter.setFilterGlobal("");
            new CacheAdapter().saveFiltertoMem("");
        }

        ConnAdapter.setUserExams(exams);
        ConnAdapter.setUserCredits(credits);
        ConnAdapter.setUserFs(fs);
        ConnAdapter.setUserCal(cal);
        ConnAdapter.setFilterGlobal(GroupFilter);
    }

    //Set information to cache.
    private static void setCache(){
        new CacheAdapter().saveCaltoMem(ConnAdapter.getUserCal());
        new CacheAdapter().saveCredittoMem(ConnAdapter.getUserCredits());
        new CacheAdapter().saveExamstoMem(ConnAdapter.getUserExams());
        new CacheAdapter().saveFstoMem(ConnAdapter.getUserFs());
        new CacheAdapter().saveHashGlobaltoMem(ConnAdapter.getGlobalHash());
        new CacheAdapter().saveUserGlobaltoMem(ConnAdapter.getGlobalId());
    }

    //Attempt to login.
    private void attemptLogin() {

        //Error->go back.
        if (mAuthTask != null) return;

        //Reset errors.
        mUserId.setError(null);
        mHashView.setError(null);

        //Store value for the next steps.
        String username = mUserId.getText().toString();
        String hash = mHashView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //Check for a valid hash.
        if (!isPasswordValid(hash)) {
            mHashView.setError(getString(R.string.error_invalid_hash));
            focusView = mHashView;
            cancel = true;
        }

        //Check if user entered a hash.
        if (TextUtils.isEmpty(hash)) {
            mHashView.setError(getString(R.string.error_field_required));
            focusView = mHashView;
            cancel = true;
        }

        //Check if the user entered an username and it is regular.
        if (TextUtils.isEmpty(username)) {
            mUserId.setError(getString(R.string.error_field_required));
            focusView = mUserId;
            cancel = true;
        } else if (!isUserValid(username)) {
            mUserId.setError(getString(R.string.error_invalid_email));
            focusView = mUserId;
            cancel = true;
        }

        //Error!
        if (cancel) {
            focusView.requestFocus();
        } else {
            //Perform login-task.
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

    //Async tasks to pull data from campus-dual to our client.
    //Async task does not interferer with our main thread.
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String mHash;

        //Function params.
        UserLoginTask(String email, String hash) {
            username = email;
            mHash = hash;
        }

        //Make actual request.
        @Override
        protected Boolean doInBackground(Void... params) {

            boolean reachable = false;

            //Check if campus dual is working && login with provided hash and userid.
            try {
                reachable = ConnAdapter.isReachable("https://erp.campus-dual.de/sap/bc/webdynpro/sap/zba_initss?uri=https%3a%2f%2fselfservice.campus-dual.de%2findex%2flogin&sap-client=100&sap-language=DE#");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Try to login with provided data.
            boolean userCheck = ConnAdapter.loginConnection(username,mHash);

            //Set personal-values as global objects for later usage.
            ConnAdapter.setGlobalHash(mHash);
            ConnAdapter.setGlobalId(username);

            //Return true if successful or false if something failed.
            return (reachable && userCheck);
        }

        //After Login-credentials are validated.
        @Override
        protected void onPostExecute(final Boolean success) {

            //Reset params.
            mAuthTask = null;

            //Check if login was successful.
            if (success) {

                //Set times for the url.
                String startT = String.valueOf(ConnAdapter.startTime());
                String endT = String.valueOf(ConnAdapter.endTime(Long.valueOf(startT)+3024000));

                String output="";
                try {
                    output= new TimetableTask(ConnAdapter.getGlobalId(), ConnAdapter.getGlobalHash(),startT,endT)
                            .execute()
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                //Set global value of the cal
                ConnAdapter.setUserCal(output);

                String output2;
                try {
                    output2= new SemesterTask(ConnAdapter.getGlobalId(), ConnAdapter.getGlobalHash())
                            .execute()
                            .get();

                    String[] status= output2.split("\n");

                    ConnAdapter.setUserFs(status[0].replaceAll("\"",""));
                    ConnAdapter.setUserCredits(status[1]);

                    ConnAdapter.setUserExams(status[2]);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                //set information to cache
                setCache();
                //Everything is fine, now go to the next activity.
                showProgress(false);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            } else {

                showProgress(false);
                //after wrong connection show dialog
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Fehler bei der Verbindung !");
                alertDialog.setMessage("Es ist ein Fehler bei der Verbindung zu Campus-Dual aufgetreten!" +
                        "\nBitte überprüfe deine Userid und deinen Userhash." +
                        "\nBitte überprüfe deine Internetverbindung.");
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

    //Async task.
    private class TimetableTask extends AsyncTask<Void, Void,String> {

        //Set params.
        private String userName;
        private String hashValue;
        private String startTime;
        private String endTime;

        //Take params from function.
        TimetableTask(String id, String hash, String start, String end){
            userName=id;
            hashValue=hash;
            startTime=start;
            endTime=end;
        }

        //Get Content.
        @Override
        protected String doInBackground(Void...params){
            return ConnAdapter.getCal(userName,hashValue, startTime, endTime);
        }

    }

    //Second async task.
    private class SemesterTask extends AsyncTask<Void, Void,String> {

        //Params.
        private String userName;
        private String hashValue;

        //Sets.
        SemesterTask(String id, String hash) {
            userName = id;
            hashValue = hash;
        }

        //Get content.
        @Override
        protected String doInBackground(Void... params) {
            return ConnAdapter.getSemester(userName, hashValue) + ConnAdapter.getCredits(userName, hashValue) + ConnAdapter.getExams(userName, hashValue);
        }
    }
}