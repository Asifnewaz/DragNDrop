package com.securepenny.horizontalrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CustomItem> mCustomers;

    private ItemTouchHelper mItemTouchHelper;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String LIST_OF_SORTED_DATA_ID = "json_list_sorted_data_id";
    public final static String PREFERENCE_FILE = "preference_file";

    Button btnSeeMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSeeMore =(Button) findViewById(R.id.btnSeeMore);

        mSharedPreferences = this.getApplicationContext()
                .getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();


        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCustomers = getSampleData();

        //setup the adapter with empty list
        mAdapter = new CustomAdapter(mCustomers, this);
        mRecyclerView.setAdapter(mAdapter);

        btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GridActivity.class );
                startActivity(intent);
            }
        });
    }

    private List<CustomItem> getSampleData(){

        //Get the sample data
        List<CustomItem> customerList = new ArrayList<CustomItem>();

        CustomItem customer1 = new CustomItem((long) 1,"Sub Zero", R.drawable.sub_zero);
        customerList.add(customer1);

        CustomItem customer2 = new CustomItem((long) 2,"Scorpion", R.drawable.sub_zero);
        customerList.add(customer2);

        CustomItem customer3 = new CustomItem((long) 3,"Kano", R.drawable.sub_zero);
        customerList.add(customer3);

        CustomItem customer4 = new CustomItem((long) 4,"Guru", R.drawable.sub_zero);
        customerList.add(customer4);

        CustomItem customer5 = new CustomItem((long) 5,"Guru2", R.drawable.sub_zero);
        customerList.add(customer5);

        CustomItem customer6 = new CustomItem((long) 6,"Guru3", R.drawable.sub_zero);
        customerList.add(customer6);

        CustomItem customer7 = new CustomItem((long) 7,"Guru4", R.drawable.sub_zero);
        customerList.add(customer7);


        //create an empty array to hold the list of sorted Customers
        List<CustomItem> sortedCustomers = new ArrayList<CustomItem>();

        //get the JSON array of the ordered of sorted customers
        String jsonListOfSortedCustomerId = mSharedPreferences.getString(LIST_OF_SORTED_DATA_ID, "");


        //check for null
        if (!jsonListOfSortedCustomerId.isEmpty()){

            //convert JSON array into a List<Long>
            Gson gson = new Gson();
            List<Long> listOfSortedCustomersId = gson.fromJson(jsonListOfSortedCustomerId, new TypeToken<List<Long>>(){}.getType());

            //build sorted list
            if (listOfSortedCustomersId != null && listOfSortedCustomersId.size() > 0){
                for (Long id: listOfSortedCustomersId){
                    for (CustomItem customer: customerList){
                        if (customer.getId().equals(id)){
                            sortedCustomers.add(customer);
                            customerList.remove(customer);
                            break;
                        }
                    }
                }
            }

            //if there are still customers that were not in the sorted list
            //maybe they were added after the last drag and drop
            //add them to the sorted list
            if (customerList.size() > 0){
                sortedCustomers.addAll(customerList);
            }

            return sortedCustomers;
        }else {
            return customerList;
        }
    }
}
