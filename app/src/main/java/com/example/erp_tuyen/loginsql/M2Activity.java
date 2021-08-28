package com.example.erp_tuyen.loginsql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
public class M2Activity extends AppCompatActivity {

    Spinner spinnerAssetName;

    String ip, db,pwsql;
    Connection connect;
    PreparedStatement stmt;
    ResultSet rs;
    TextView tvUser;
    String un,pw,hoten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m2);

        tvUser=(TextView)this.findViewById(R.id.tvUser);

        Bundle extras=getIntent().getExtras(); //dùng cái này để hứng gói tin hồi nãy.
        un=extras.getString("USERNAME","N/A");//lấy giá trị của cái key là USERNAME
        pw=extras.getString("PASSWORD","N/A");// như trên
        hoten=extras.getString("HOTEN","N/A");// vẫn rứa
        tvUser.setText(hoten);//cho tên hiển thị gốc phải

        ip = "192.168.1.10";
        un = "sa";
        pwsql = "sa123";
        db = "Session2";

        spinnerAssetName = (Spinner) findViewById(R.id.spinnerAssetName);
        connect = CONN(un, pwsql, db, ip);
        String query = "select AssetName from Assets";
        try {
            connect = CONN(un, pwsql, db, ip);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rs.next()) {
                String id = rs.getString("AssetName");
                data.add(id);

            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerAssetName.setAdapter(NoCoreAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        spinnerAssetName.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String name = spinnerAssetName.getSelectedItem().toString();
                Toast.makeText(M2Activity.this, name, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("NewApi")
    private Connection CONN(String _user, String _pass, String _DB,
                            String _server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
                    + "databaseName=" + _DB + ";user=" + _user + ";password="
                    + _pass + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
//hiển thị dũ liêu
