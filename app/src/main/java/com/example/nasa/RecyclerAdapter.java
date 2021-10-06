package com.example.nasa;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    // list of nasa to populate list
    ArrayList<Nasa> nasaImages;

    // constructor
    public RecyclerAdapter(ArrayList<Nasa> nasaImages) {
        this.nasaImages = nasaImages;
    }

    @NonNull
    @Override
    public com.example.nasa.RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        create a layout inflater and then inflate the view with the correct xml layout
        and container that holds the row. Then, pass it into a the viewholder constructor
        to create the view of each row before returning it.
        */
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get the drawing object
        Nasa n = nasaImages.get(position);
        String title = n.getTitle();
        byte[] image = n.getImage();

        // set up the holder with the drawing title and image
        holder.textView.setText(title);
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
    }

    @Override
    public int getItemCount() {
        return nasaImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        //        set the contents in each row to their respective text
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
