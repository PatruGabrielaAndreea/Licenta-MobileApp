package com.example.licenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<AppNotification> notificationsList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        EditText searchEditText = view.findViewById(R.id.searchEditText);
        RecyclerView searchRecyclerView = view.findViewById(R.id.searchRecyclerView);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchEditText.getText().toString().trim();
                List<SearchResult> searchResults = performSearchAndGetResults(query);
                SearchResultAdapter adapter = new SearchResultAdapter(requireContext(), searchResults);
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                searchRecyclerView.setAdapter(adapter);

                return true;
            }
            return false;
        });

        return view;
    }

    private List<SearchResult> performSearchAndGetResults(String query) {
        List<SearchResult> searchResults = new ArrayList<>();
        for (AppNotification notification : notificationsList) {
            if (notification.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    notification.getDescription().toLowerCase().contains(query.toLowerCase())) {
                // Convert the AppNotification to a SearchResult and add it to the searchResults list
                SearchResult searchResult = new SearchResult(notification.getTitle(), notification.getDescription(), notification.getCategory());
                searchResults.add(searchResult);
            }
        }
        return searchResults;
    }

}