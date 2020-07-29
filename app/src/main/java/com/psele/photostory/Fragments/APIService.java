package com.psele.photostory.Fragments;


import com.psele.photostory.Notifications.MyResponse;
import com.psele.photostory.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAjBKKhj4:APA91bH2Ay3RtdJkz0yvFbDe__CypGj1FRo5-BGC2BCqQmDGzl-KiFlLuv0aJNRsdmRb-SILrTmGuVrFXUtLj5YFE1oeryMuzek8hNYMJF74mspkz4raa0lmgg4ExC8ie4oI2VNm75Yc"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
