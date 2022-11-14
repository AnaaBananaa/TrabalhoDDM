package com.ddm.veggie.view.components;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ddm.veggie.R;

import java.util.Map;

public class FilterDialog extends DialogFragment {
    public static final String TAG = "FilterDialog";

    //Bundle Keys
    private static final String KEY_FILTER_ORDER_SELECTED_ITEM_POSITION = "keyFilterOrderSelectedItemPosition";

    //Final
    private static final String[] filtersOrder = {"Mais Favoritadas", "Ordem alfabética A-Z", "Ordem alfabética Z-A"};

    //Variables
    private Map<String, Object> filters;
    private FilterDialogFragmentListener listener;
    private int filterOrderSelectedItemPosition = 0;

    //Components
    private View view;
    private Spinner filterOrderSpinner;
    private Toolbar toolbar;

    public FilterDialog(FilterDialogFragmentListener listener, Map<String, Object> filters) {
        this.listener = listener;
        this.filters = filters;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        if (savedInstanceState != null) {
            filterOrderSelectedItemPosition = savedInstanceState.getInt(KEY_FILTER_ORDER_SELECTED_ITEM_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize Components
        view = inflater.inflate(R.layout.dialog_filter, container, false);
        filterOrderSpinner = view.findViewById(R.id.filter_dialog_spinner);
        toolbar = view.findViewById(R.id.filter_dialog_toolbar);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, filtersOrder);

        //Configure Components
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterOrderSpinner.setAdapter(adapter);

        //Actions
        filterOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filters.replace("filterOrder", filtersOrder[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filters.replace("filterOrder", filtersOrder[0]);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(R.string.txt_filter);
        toolbar.inflateMenu(R.menu.filter_dialog_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            listener.onSaveClicked();
            dismiss();
            return true;
        });
        filterOrderSelectedItemPosition = filterOrderSpinner.getSelectedItemPosition();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_FILTER_ORDER_SELECTED_ITEM_POSITION, filterOrderSelectedItemPosition);
        super.onSaveInstanceState(outState);
    }

    public interface FilterDialogFragmentListener {
        void onSaveClicked();
    }
}