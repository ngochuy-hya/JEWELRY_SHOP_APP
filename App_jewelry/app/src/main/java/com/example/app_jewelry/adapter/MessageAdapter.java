package com.example.app_jewelry.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_jewelry.R;
import com.example.app_jewelry.Service.DTO.reponse.MessageResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_RIGHT = 1;
    private static final int VIEW_TYPE_LEFT = 0;

    private final Context context;
    private final List<MessageResponse> messageList;
    private final int currentUserId;

    public MessageAdapter(Context context, List<MessageResponse> messageList, int currentUserId) {
        this.context = context;
        this.messageList = messageList;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        MessageResponse msg = messageList.get(position);
        return "User".equals(msg.getSender()) ? VIEW_TYPE_RIGHT : VIEW_TYPE_LEFT;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_RIGHT) {
            View view = inflater.inflate(R.layout.item_message_right, parent, false);
            return new RightMessageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_message_left, parent, false);
            return new LeftMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageResponse msg = messageList.get(position);
        if (holder instanceof RightMessageViewHolder) {
            ((RightMessageViewHolder) holder).bind(msg);
        } else if (holder instanceof LeftMessageViewHolder) {
            ((LeftMessageViewHolder) holder).bind(msg);
        }
    }

    // Tin nhắn bên phải (user gửi)
    class RightMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        RecyclerView recyclerImages;

        RightMessageViewHolder(View view) {
            super(view);
            tvMessage = view.findViewById(R.id.tvMessage);
            tvTime = view.findViewById(R.id.tvTime);
            recyclerImages = view.findViewById(R.id.recyclerMessageImages);
        }

        void bind(MessageResponse msg) {
            showTextAndImages(msg, tvMessage, recyclerImages);
            tvTime.setText(getFormattedTime(msg.getSentAt()));
            tvTime.setVisibility(View.VISIBLE);
        }
    }

    // Tin nhắn bên trái (admin gửi)
    class LeftMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        RecyclerView recyclerImages;

        LeftMessageViewHolder(View view) {
            super(view);
            tvMessage = view.findViewById(R.id.tvMessage);
            tvTime = view.findViewById(R.id.tvTime);
            recyclerImages = view.findViewById(R.id.recyclerMessageImages);
        }

        void bind(MessageResponse msg) {
            showTextAndImages(msg, tvMessage, recyclerImages);
            tvTime.setText(getFormattedTime(msg.getSentAt()));
            tvTime.setVisibility(View.VISIBLE);
        }
    }

    // Hiển thị nội dung và ảnh
    private void showTextAndImages(MessageResponse msg, TextView tvMessage, RecyclerView recyclerImages) {
        if (!TextUtils.isEmpty(msg.getContent())) {
            tvMessage.setText(msg.getContent());
            tvMessage.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setVisibility(View.GONE);
        }

        if (msg.getImageUrls() != null && !msg.getImageUrls().isEmpty()) {
            recyclerImages.setVisibility(View.VISIBLE);
            recyclerImages.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerImages.setAdapter(new MessageImageAdapter(context, msg.getImageUrls()));
        } else {
            recyclerImages.setVisibility(View.GONE);
        }
    }

    // Định dạng thời gian: "2025-07-12T17:10:00" -> "HH:mm"
    private String getFormattedTime(String isoTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(isoTime);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
}
