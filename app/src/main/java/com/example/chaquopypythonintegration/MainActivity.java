package com.example.chaquopypythonintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ImageView iv;
    EditText editTextX, editTextY;
    String X_data, Y_data;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.submit);
        iv = (ImageView) findViewById(R.id.image_view);
        editTextX = (EditText) findViewById(R.id.x_data);
        editTextY = (EditText)findViewById(R.id.y_data);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        final Python py = Python.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                X_data = editTextX.getText().toString();
                Y_data = editTextY.getText().toString();

                PyObject pyo = py.getModule("myscript");
                PyObject obj = pyo.callAttr("main",X_data,Y_data);

                String str = obj.toString();

                byte data[] = android.util.Base64.decode(str, Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);

                iv.setImageBitmap(bmp);



            }
        });
    }
}