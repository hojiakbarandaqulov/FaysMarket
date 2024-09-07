package org.example.service.history;

import org.example.utils.RandomUtil;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmsService {

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${my.eskiz.uz.email}")
    private String myEskizUzEmail;

    @Value("${my.eskiz.uz.password}")
    private String myEskizUzPassword;

    public void sendSms(String phone) {
        String message = "Bu Eskiz dan test";
        send(phone, message);
    }

    private void send(String phone, String message) {
        String token = "Bearer " + getToken();
        String prPhone = phone;
        if (prPhone.startsWith("+")) {
            prPhone = prPhone.substring(1);
        }
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", prPhone)
                .addFormDataPart("message", message)
                .addFormDataPart("from", "4546")
                .build();

        Request request = new Request.Builder()
                .url(smsUrl + "api/message/sms/send")
                .method("POST", body)
                .header("Authorization", token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
            if (response.isSuccessful()) {
                System.out.println(response);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
  /*  private String getToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", myEskizUzEmail)
                .addFormDataPart("password", myEskizUzPassword)
                .build();
        Request request = new Request.Builder()
                .url(smsUrl + "api/v1/authorization/login")
                .method("POST", body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string(); // Javobni faqat bir marta o'qib, saqlaymiz

            if (!response.isSuccessful()) {
                System.err.println("HTTP error code: " + response.code());
                System.err.println("Error message: " + response.message());
                throw new IOException("Failed to get token: " + response.code());
            }

            // JSONni o'qiymiz va parse qilamiz
            try {
                JSONObject object = new JSONObject(responseBody);
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to parse JSON: " + responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute request", e);
        }
    }*/

    private String getToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", myEskizUzEmail)
                .addFormDataPart("password", myEskizUzPassword)
                .build();
        Request request = new Request.Builder()
                .url(smsUrl + "api/v1/authorization/login")
                .method("POST", body)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException();
            } else {
                JSONObject object = new JSONObject(response.body().string());
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}


