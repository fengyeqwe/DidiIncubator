package com.didiincubator.Presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;


import com.didiincubator.listener.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 何晓 on 2016/5/31.
 */
//请求显示图片
public class Imageparser extends Thread{
    private ImageView imageView;
    private String url;
    private Handler handler;
    public Imageparser(ImageView imageView, String url, Handler handler) {
        super();
        this.imageView = imageView;
        this.url = url;
        this.handler = handler;
    }

    public void doget(){
        try {
            URL jsonurl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection) jsonurl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
				/*StringBuffer stringBuffer=new StringBuffer();
				BufferedReader bReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String string;
				while ((string=bReader.readLine())!=null) {
					stringBuffer.append(string);
				}*/
            InputStream inputStream=connection.getInputStream();
            final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    imageView.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        doget();
    }
    /*public Imageparser(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }
    public void getImage(){
        Request<Bitmap> request=null;
        request= NoHttp.createImageRequest(url, RequestMethod.GET);
    }

    @Override
    public void onSucceed(int what, Response<Bitmap> response) {
        imageView.setImageBitmap(response.get());
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

    }*/
}
