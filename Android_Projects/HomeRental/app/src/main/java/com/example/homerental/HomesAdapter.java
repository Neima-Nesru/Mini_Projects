package com.example.homerental;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomesAdapter extends RecyclerView.Adapter<HomesAdapter.HomeViewHolder> {

    private Context context;
    private List<Home> homeList;

    public HomesAdapter(Context context, List<Home> homeList) {
        this.context = context;
        this.homeList = homeList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Home home = homeList.get(position);
        holder.title.setText(home.getTitle());
        holder.description.setText(home.getDescription());
        holder.price.setText(String.valueOf(home.getPrice()));
        holder.location.setText(home.getLocation());
        holder.image.setImageURI(Uri.parse(home.getImageUri()));

        holder.image.setOnClickListener(v -> {
            FragmentActivity activity = (FragmentActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, HomeDetailFragment.newInstance(String.valueOf(home.getId())))
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, price, location;
        ImageView image;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.homeTitle);
            description = itemView.findViewById(R.id.homeDescription);
            price = itemView.findViewById(R.id.homePrice);
            location = itemView.findViewById(R.id.homeLocation);
            image = itemView.findViewById(R.id.homeImage);
        }
    }
}
