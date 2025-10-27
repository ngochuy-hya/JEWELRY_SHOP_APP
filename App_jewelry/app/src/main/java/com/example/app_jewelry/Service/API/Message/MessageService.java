package com.example.app_jewelry.Service.API.Message;

import com.example.app_jewelry.Service.DTO.reponse.MessageResponse;
import com.example.app_jewelry.Service.DTO.request.CreateMessageRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageService {
    @GET("messages/conversation/{conversationId}")
    Call<List<MessageResponse>> getMessages(@Path("conversationId") int conversationId);

    @POST("messages/send")
    Call<MessageResponse> sendMessage(@Body CreateMessageRequest request);


}
