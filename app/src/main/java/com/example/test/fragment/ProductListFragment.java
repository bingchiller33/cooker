package com.example.test.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.adapter.ProductListAdapter;
import com.example.test.bean.Product;
import com.example.test.entity.ProductEntity;
import com.example.test.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductListFragment newInstance(String param1, String param2) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private List<Product> productList = new ArrayList<>();
    private ProductRepository productRepository;

    private ProductListAdapter productListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Get data to productList
        for (int i = 0; i < 10; i++) {
            Product pd = new Product();
            pd.setName("name" + i);
            pd.setProductId("" + i);
            pd.setPrice(1.2f + i);
            productList.add(pd);
        }

        RecyclerView recyclerViewProducts = view.findViewById(R.id.fragment_recycleView_product_list);
        ProductListAdapter productListAdapter = new ProductListAdapter(productList, getContext());
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProducts.setAdapter(productListAdapter);
        return view;
    }


//    private void convertProductEntity(List<ProductEntity> productEntities) {
//        if (productEntities == null) {
//            return;
//        }
//        productList.clear();
//        for (ProductEntity productEntity : productEntities) {
//            Product product = new Product();
//            product.setProductId("" + productEntity.getId());
//            product.setName(productEntity.getName());
//            product.setPrice(productEntity.getPrice());
//            productList.add(product);
//        }
//    }
}