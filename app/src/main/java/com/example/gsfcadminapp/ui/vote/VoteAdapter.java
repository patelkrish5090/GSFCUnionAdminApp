package com.example.gsfcadminapp.ui.vote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;
import com.example.gsfcadminapp.User;
import com.example.gsfcadminapp.ui.home.UserAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

//public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder> {
//    private List<Vote> votes;
//
//    public VoteAdapter(List<Vote> votes) {
//        this.votes = votes;
//    }
//
//    @NonNull
//    @Override
//    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vote_item, parent, false);
//        return new VoteViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
//        Vote vote = votes.get(position);
//        holder.bind(vote);
//    }
//
//    @Override
//    public int getItemCount() {
//        return votes.size();
//    }
//
//    public void setVotes(List<Vote> votes) {
//        this.votes = votes;
//        notifyDataSetChanged();
//    }
//
//    static class VoteViewHolder extends RecyclerView.ViewHolder {
//        TextView questionTextView, totalCountTextView;
//        RecyclerView optionsRecyclerView;
//
//        public VoteViewHolder(@NonNull View itemView) {
//            super(itemView);
//            questionTextView = itemView.findViewById(R.id.questionTextView);
//            totalCountTextView = itemView.findViewById(R.id.totalCountTextView);
//            optionsRecyclerView = itemView.findViewById(R.id.optionsRecyclerView);
//        }
//
//        public void bind(Vote vote) {
//            questionTextView.setText(vote.question);
//            totalCountTextView.setText(String.valueOf(vote.total_count));
//
//            optionsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//            optionsRecyclerView.setAdapter(new OptionAdapter(new ArrayList<>(vote.options.values())));
//        }
//    }
//}

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder> {
    private List<Vote> votes;
    private OnVoteActionListener onVoteActionListener;

    public VoteAdapter(List<Vote> votes, OnVoteActionListener onVoteActionListener) {
        this.votes = votes;
        this.onVoteActionListener = onVoteActionListener;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vote_item, parent, false);
        return new VoteViewHolder(view, onVoteActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
        Vote vote = votes.get(position);
        holder.bind(vote);
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
        notifyDataSetChanged();
    }

    static class VoteViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, totalCountTextView;
        RecyclerView optionsRecyclerView;
        Button buttonEndSession, buttonDeleteVote, buttonExportVote;

        public VoteViewHolder(@NonNull View itemView, OnVoteActionListener onVoteActionListener) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            totalCountTextView = itemView.findViewById(R.id.totalCountTextView);
            optionsRecyclerView = itemView.findViewById(R.id.optionsRecyclerView);
            buttonEndSession = itemView.findViewById(R.id.buttonEndSession);
            buttonDeleteVote = itemView.findViewById(R.id.buttonDeleteVote);
            buttonExportVote = itemView.findViewById(R.id.buttonExportVote);

            buttonEndSession.setOnClickListener(v -> {
                if (onVoteActionListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onVoteActionListener.onEndSessionClick(position);
                    }
                }
            });

            buttonDeleteVote.setOnClickListener(v -> {
                if (onVoteActionListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onVoteActionListener.onDeleteVoteClick(position);
                    }
                }
            });

            buttonExportVote.setOnClickListener(v -> {
                if (onVoteActionListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onVoteActionListener.onExportVoteClick(position);
                    }
                }
            });
        }

        public void bind(Vote vote) {
            questionTextView.setText(vote.question);
            totalCountTextView.setText(String.valueOf(vote.total_count));

            optionsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            optionsRecyclerView.setAdapter(new OptionAdapter(new ArrayList<>(vote.options.values())));
        }
    }

    public interface OnVoteActionListener {
        void onEndSessionClick(int position);
        void onDeleteVoteClick(int position);
        void onExportVoteClick(int position);
    }
}


