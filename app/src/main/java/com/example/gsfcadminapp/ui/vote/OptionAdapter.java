package com.example.gsfcadminapp.ui.vote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private List<Option> options;

    public OptionAdapter(List<Option> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.options_item, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Option option = options.get(position);
        holder.bind(option);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class OptionViewHolder extends RecyclerView.ViewHolder {
        TextView optionValueTextView, optionCountTextView;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionValueTextView = itemView.findViewById(R.id.optionValueTextView);
            optionCountTextView = itemView.findViewById(R.id.optionCountTextView);
        }

        public void bind(Option option) {
            optionValueTextView.setText(option.value);
            optionCountTextView.setText(String.valueOf(option.count));
        }
    }
}

