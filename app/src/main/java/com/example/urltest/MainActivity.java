package com.example.urltest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x123)
            {
                show.setImageBitmap(bitmap);
            }
        }
    };
    private ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = (ImageView)findViewById(R.id.show);



        new Thread()
        {
            public void run()
            {
                try
                {
                    URL url = new URL("http://www.crazyit.org/attachments/month_1008/20100812_7763e970f822325bfb019ELQVym8tW3A.png");

                    InputStream is = url.openStream();

                    bitmap = BitmapFactory.decodeStream(is);

                    handler.sendEmptyMessage(0x123);
                    is.close();

                    is = url.openStream();

                    OutputStream os = openFileOutput("crazyit.png", MODE_PRIVATE);

                    byte[] buff = new byte[1024];

                    int hashRead = 0;

                    while((hashRead = is.read()) > 0)
                    {
                        os.write(buff, 0 , hashRead);
                    }
                    is.close();
                    os.close();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
