package com.example.app_jewelry;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.Service.API.apiManager;
import com.example.app_jewelry.Service.DTO.reponse.MessageResponse;
import com.example.app_jewelry.Service.DTO.request.CreateMessageRequest;
import com.example.app_jewelry.adapter.MessageAdapter;
import com.example.app_jewelry.adapter.MessageImagePreviewAdapter;
import com.example.app_jewelry.utils.Cloudinary.CloudinaryUploader;
import com.example.app_jewelry.utils.SharedPreferencesManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1001;

    private RecyclerView recyclerChat, recyclerSelectedImages;
    private EditText edtMessage;
    private ImageButton btnSend, btnImage;
    private ImageView btnBack;

    private final List<MessageResponse> messageList = new ArrayList<>();
    private final List<Uri> selectedImageUris = new ArrayList<>();

    private MessageAdapter adapter;
    private MessageImagePreviewAdapter previewAdapter;

    private int currentUserId = -1;
    private Integer conversationId;

    private apiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        currentUserId = new SharedPreferencesManager(this).getUserId();
        if (currentUserId <= 0) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        btnBack = findViewById(R.id.btnBack);
        recyclerChat = findViewById(R.id.recyclerChat);
        recyclerSelectedImages = findViewById(R.id.recyclerSelectedImages);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        btnImage = findViewById(R.id.btnImage);

        adapter = new MessageAdapter(this, messageList, currentUserId);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerChat.setAdapter(adapter);

        previewAdapter = new MessageImagePreviewAdapter(this, selectedImageUris, position -> {
            selectedImageUris.remove(position);
            previewAdapter.notifyDataSetChanged();
            toggleImagePreview();
        });
        btnBack.setOnClickListener(v -> {
            finish();
        });
        recyclerSelectedImages.setAdapter(previewAdapter);
        recyclerSelectedImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        apiManager = new apiManager();


        apiManager.getConversationIdByUserId(currentUserId, new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    conversationId = response.body();
                    loadMessages(); // chỉ load khi có conversationId
                } else {
                    Toast.makeText(ChatActivity.this, "Không tìm thấy hội thoại", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        btnSend.setOnClickListener(v -> {
            sendMessage();
        });

        btnImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_IMAGE_PICK);
        });
    }

    private void loadMessages() {
        apiManager.getMessages(conversationId, new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messageList.clear();
                    messageList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    scrollToBottom();
                } else {
                    Toast.makeText(ChatActivity.this, "Không tải được tin nhắn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String content = edtMessage.getText().toString().trim();

        if (TextUtils.isEmpty(content) && selectedImageUris.isEmpty()) {
            Toast.makeText(this, "Hãy nhập nội dung hoặc chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSend.setEnabled(false);
        if (selectedImageUris.isEmpty()) {
            postMessage(content, new ArrayList<>());
        } else {
            uploadImagesAndSend(content);
        }
    }

    private void uploadImagesAndSend(String content) {
        List<String> uploadedUrls = new ArrayList<>();
        final int total = selectedImageUris.size();

        for (Uri uri : selectedImageUris) {
            CloudinaryUploader.uploadImage(this, uri, new CloudinaryUploader.UploadCallback() {
                @Override
                public void onSuccess(String imageUrl) {
                    uploadedUrls.add(imageUrl);
                    if (uploadedUrls.size() == total) {
                        postMessage(content, uploadedUrls);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(ChatActivity.this, "Upload ảnh thất bại", Toast.LENGTH_SHORT).show();
                    btnSend.setEnabled(true);
                }
            });
        }
    }

    private void postMessage(String content, List<String> imageUrls) {
        CreateMessageRequest request = new CreateMessageRequest(conversationId, "User", content, imageUrls);

        apiManager.sendMessage(request, new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                btnSend.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    MessageResponse newMsg = response.body();
                    messageList.add(newMsg);
                    adapter.notifyItemInserted(messageList.size() - 1);
                    scrollToBottom();
                    edtMessage.setText("");
                    selectedImageUris.clear();
                    previewAdapter.notifyDataSetChanged();
                    toggleImagePreview();
                } else {
                    Toast.makeText(ChatActivity.this, "Gửi tin nhắn thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                btnSend.setEnabled(true);
                Toast.makeText(ChatActivity.this, "Lỗi máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleImagePreview() {
        recyclerSelectedImages.setVisibility(selectedImageUris.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void scrollToBottom() {
        recyclerChat.scrollToPosition(messageList.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUris.add(data.getData());
            previewAdapter.notifyDataSetChanged();
            toggleImagePreview();
        }
    }
}

