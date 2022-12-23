package com.example.myapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList = new ArrayList<>();

    public CategoryAdapter() {
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.category_item, viewGroup, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public void setData(ArrayList<CategoryModel> categoryModelList) {
        this.categoryModelList =categoryModelList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryIcon;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            categoryIcon = itemView.findViewById( R.id.category_icon );
            categoryName = itemView.findViewById( R.id.category_name );

        }

        public void bind(int position){
            CategoryModel model = categoryModelList.get( position );
            String icon = model.getCategoryIcon();
            String name = model.getCategoryName();

            categoryName.setText( name );
            if (!icon.equals( "null" )) {
                Log.e("LOG_LOG",icon);
                Glide.with( itemView.getContext() ).load( icon ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_home_24 ) ).into( categoryIcon );
            }

            itemView.setOnClickListener( view -> {
                if (position != 0) {
                    Intent categoryIntent = new Intent( itemView.getContext(), CategoryActivity.class );
                    categoryIntent.putExtra( "CategoryName", name );
                    itemView.getContext().startActivity( categoryIntent );
                }
            } );
        }
    }
}
