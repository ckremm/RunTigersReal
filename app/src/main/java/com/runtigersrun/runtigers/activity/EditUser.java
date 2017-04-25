package com.runtigersrun.runtigers.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.runtigersrun.runtigers.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class EditUser extends AppCompatActivity {

    EditText first, last, username, password, friend1, friend2, friend3;
    String oldun, fi, la, un, pass, f1, f2, f3;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        first = (EditText)findViewById(R.id.regEditFName);
        last = (EditText)findViewById(R.id.regEditLName);
        username = (EditText)findViewById(R.id.regEditUser);
        password = (EditText)findViewById(R.id.regEditPass);
        friend1 = (EditText)findViewById(R.id.edFriend1);
        friend2 = (EditText)findViewById(R.id.edFriend2);
        friend3 = (EditText)findViewById(R.id.edFriend3);

        first.setText(MainActivity.currentUser.getFname());
        last.setText(MainActivity.currentUser.getLname());
        username.setText(MainActivity.currentUser.getUname());
        password.setText(MainActivity.currentUser.getPass());
        friend1.setText(MainActivity.currentUser.getFriend1());
        friend2.setText(MainActivity.currentUser.getFriend2());
        friend3.setText(MainActivity.currentUser.getFriend3());
    }

    public void clickEdit(View view){
        fi = first.getText().toString();
        la = last.getText().toString();
        un = username.getText().toString();
        pass = password.getText().toString();
        f1 = friend1.getText().toString();
        f2 = friend2.getText().toString();
        f3 = friend3.getText().toString();

        BackgroundEditUser editUser = new BackgroundEditUser();
        editUser.execute(MainActivity.currentUser.getUname(), fi,la,un,pass,f1,f2,f3);
        finish();

        Intent maIntent = new Intent(this, MainActivity.class);
        this.startActivity((maIntent));
    }

    public class BackgroundEditUser extends AsyncTask<String,Void, String> {

        String add_user_url;
        @Override
        protected void onPreExecute() {
            add_user_url = "https://people.cs.clemson.edu/~dstieby/cpsc4820/RTR/externaldb/edituser.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String oldun, fn,ln,un,pass,f1,f2,f3;
            oldun=args[0];
            fn=args[1];
            ln=args[2];
            un=args[3];
            pass = args[4];
            f1= args[5];
            f2= args[6];
            f3= args[7];

            try{
                URL url = new URL(add_user_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data_string =
                        URLEncoder.encode("OldUsername","UTF-8")+"="+URLEncoder.encode(oldun,"UTF-8") + "&" +
                        URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(fn,"UTF-8") + "&" +
                        URLEncoder.encode("LastName","UTF-8")+"="+URLEncoder.encode(ln,"UTF-8") + "&" +
                        URLEncoder.encode("Username","UTF-8")+"="+URLEncoder.encode(un,"UTF-8") + "&" +
                        URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+ "&" +
                                URLEncoder.encode("Friend1","UTF-8")+"="+URLEncoder.encode(f1,"UTF-8")+ "&" +
                                URLEncoder.encode("Friend2","UTF-8")+"="+URLEncoder.encode(f2,"UTF-8")+ "&" +
                                URLEncoder.encode("Friend3","UTF-8")+"="+URLEncoder.encode(f3,"UTF-8");
                bw.write(data_string);
                bw.flush();
                bw.close();
                os.close();
                InputStream is = huc.getInputStream();
                is.close();
                huc.disconnect();
                return "User Updated!";
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }


    }
}
