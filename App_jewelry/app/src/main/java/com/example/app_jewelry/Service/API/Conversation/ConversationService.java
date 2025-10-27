package com.example.app_jewelry.Service.API.Conversation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConversationService {
    @GET("conversations/by-user/{userId}")
    Call<Integer> getConversationIdByUserId(@Path("userId") int userId);
}
