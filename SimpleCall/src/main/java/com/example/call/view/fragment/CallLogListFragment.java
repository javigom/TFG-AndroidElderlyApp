package com.example.call.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.call.model.CallModel;
import com.example.call.R;
import com.example.call.view.adapter.CallLogListAdapter;
import com.example.call.model.ContactModel;

import java.util.List;
import java.util.Map;

public class CallLogListFragment extends Fragment {

    private List<CallModel> callModelList;
    private Map<String, ContactModel> phoneContactMap;
    private CallLogListAdapter callLogListAdapter;
    private CallLogListAdapter.SelectedCall selectedCall;
    private RecyclerView recyclerView;
    private View view;

    public CallLogListFragment(List<CallModel> callModelList, Map<String, ContactModel> phoneContactMap, CallLogListAdapter.SelectedCall selectedCall) {
        this.callModelList = callModelList;
        this.phoneContactMap = phoneContactMap;
        this.selectedCall = selectedCall;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_call_log_list, container, false);
        recyclerView = view.findViewById(R.id.rvCallsFCLL);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        callLogListAdapter = new CallLogListAdapter(this.callModelList, this.phoneContactMap, this.selectedCall);
        recyclerView.setAdapter(callLogListAdapter);

        return view;
    }

    public void update(int newCalls){

        for(int i = 0; i < newCalls; i++){
            callLogListAdapter.notifyItemInserted(i);
        }

        recyclerView.scrollToPosition(0);
    }

}
