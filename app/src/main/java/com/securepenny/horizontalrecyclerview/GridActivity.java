package com.securepenny.horizontalrecyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepenny.horizontalrecyclerview.listener.OnCustomerListChangedListener;
import com.securepenny.horizontalrecyclerview.listener.OnStartDragListener;
import com.securepenny.horizontalrecyclerview.utilities.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity implements OnCustomerListChangedListener, OnStartDragListener {
    private RecyclerView mRecyclerView;
    private CustomAdapterGrid mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CustomItem> mCustomers;

    private ItemTouchHelper mItemTouchHelper;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String LIST_OF_SORTED_DATA_ID = "json_list_sorted_data_id";
    public final static String PREFERENCE_FILE = "preference_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        int numberOfColumns = 3;
        mSharedPreferences = this.getApplicationContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCustomers = getSampleData();

        //setup the adapter with empty list
        mAdapter = new CustomAdapterGrid(mCustomers, this,this,this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onNoteListChanged(List<CustomItem> customeItem) {
        //after drag and drop operation, the new list of Customers is passed in here

        //create a List of Long to hold the Ids of the
        //Customers in the List
        List<Long> listOfSortedCustomerId = new ArrayList<Long>();

        for (CustomItem customItem: customeItem){
            listOfSortedCustomerId.add(customItem.getId());
        }

        //convert the List of Longs to a JSON string
        Gson gson = new Gson();
        String jsonListOfSortedCustomerIds = gson.toJson(listOfSortedCustomerId);


        //save to SharedPreference
        mEditor.putString(LIST_OF_SORTED_DATA_ID, jsonListOfSortedCustomerIds).commit();
        mEditor.commit();



    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
