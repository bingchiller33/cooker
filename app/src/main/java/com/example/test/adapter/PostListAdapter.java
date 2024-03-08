package com.example.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.ProductDetailActivity;
import com.example.test.ProductListActivity;
import com.example.test.R;
import com.example.test.bean.PostInfo;
import com.example.test.bean.Product;
import com.example.test.repository.ProductRepository;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {

    private Context context;
    private List<PostInfo> products;

    private int position;

    public PostListAdapter(List<PostInfo> productList, Context context) {
        this.products = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false);
        return new PostListViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder holder, int position) {
        PostInfo product = products.get(position);
        holder.tvProductId.setText(product.getId());
        holder.tvProductName.setText(product.getTitle());
        holder.tvProductPrice.setText(product.getBody());
        this.position = position;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class PostListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvProductId, tvProductName, tvProductPrice;
        private PostListAdapter productListAdapter;

        public PostListViewHolder(@NonNull View itemView, PostListAdapter productListAdapter) {
            super(itemView);
            tvProductId = itemView.findViewById(R.id.tv_post_id);
            tvProductName = itemView.findViewById(R.id.tv_post_title);
            tvProductPrice = itemView.findViewById(R.id.tv_post_body);
//            ((ProductListActivity) context).registerForContextMenu(tvProductName);
            this.productListAdapter = productListAdapter;
            tvProductName.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menu_popup_delete) {
                        // Assuming products is a list of ProductEntity
                        PostInfo productToDelete = products.get(position);

                        int id = Integer.parseInt(productToDelete.getId());
                        // Delete the product from the database
                        ProductRepository productRepository = new ProductRepository(context);
                        productRepository.deleteProduct(id);
                        products.remove(position);
                        productListAdapter.notifyDataSetChanged();
                    } else if (item.getItemId() == R.id.menu_popup_view) {
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        context.startActivity(intent);
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }

}
