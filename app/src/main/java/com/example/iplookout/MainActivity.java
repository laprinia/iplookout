package com.example.iplookout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 Button lookout;
 EditText address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ajustare pt tastatura
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        lookout=(Button) findViewById(R.id.buttonLookout);
        address = findViewById(R.id.editIP);

        lookout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressVal = address.getText().toString();
                if(addressVal.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Please enter an address first! "+addressVal, Toast.LENGTH_LONG).show();
                }else{
                    Intent in = new Intent(MainActivity.this, InfoNavigation.class);

                    Bundle b = new Bundle();
                    b.putString("addr",addressVal);
                    in.putExtras(b);
                    startActivity(in);
                    finish();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



    }
}
