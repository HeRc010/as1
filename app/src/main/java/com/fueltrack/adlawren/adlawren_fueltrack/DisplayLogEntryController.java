package com.fueltrack.adlawren.adlawren_fueltrack;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adlawren on 26/01/16.
 *
 * Purpose: This class is used as a factory for various callbacks used by the DisplayLogEntryActivity class.
 *
 * Design: This class consists of privately defined class which implement the appropriate callback interfaces
 *         needed for the particular callbacks of Views in the DisplayLogEntryActivity.
 *
 * Outstanding Issues: Alternative design patterns need to be utilized in 3 of the callback classes,
 *                     in these classes the Context member variable used within the callback methods
 *                     is assumed to be an Activity in an explicit cast, in order to retrieve specific
 *                     TextViews from the DisplayLogEntryActivity for the purposes of dynamically updating
 *                     the content of these Views. An Observer design pattern might remedy the issues.
 */
public class DisplayLogEntryController {
    private static DisplayLogEntryController instance = new DisplayLogEntryController();

    public static DisplayLogEntryController getInstance() {
        return instance;
    }

    private DisplayLogEntryController() {
    }

    // Callback triggered when the user selects sets a different date using a DatePickerDialog
    private class EntryDateOnDateSetListener implements DatePickerDialog.OnDateSetListener {
        Context context = null;

        public EntryDateOnDateSetListener(Context initialContext) {
            context = initialContext;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, day);

            // Update the date recorded in the DisplayLogEntryDataStore
            DisplayLogEntryDataStore.getInstance().updateDate(new Date(newDate.getTimeInMillis()));

            // TODO: find an alternative method; this is hacky
            // Update the EntryTotalCost TextView
            Activity activity = (Activity) context;

            TextView entryDateView = (TextView) activity.findViewById(R.id.entry_date);
            entryDateView.setText("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(DisplayLogEntryDataStore.getInstance().getDisplayedEntry().getDate()));
        }
    }

    public EntryDateOnDateSetListener getEntryDateOnDateSetListener(Context context) {
        return new EntryDateOnDateSetListener(context);
    }

    // Callback triggered when the user alters the text in the Station EditText View
    private class StationInputTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            DisplayLogEntryDataStore.getInstance().updateStation(editable.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public StationInputTextWatcher getStationInputTextWatcher() {
        return new StationInputTextWatcher();
    }

    // Callback triggered when the user alters the text in the FuelGrade EditText View
    private class FuelGradeInputTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            DisplayLogEntryDataStore.getInstance().updateFuelGrade(editable.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public FuelGradeInputTextWatcher getFuelGradeInputTextWatcher() {
        return new FuelGradeInputTextWatcher();
    }

    // Callback triggered when the user alters the text in the OdometerReading EditText View
    private class OdometerInputTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().equals("")) {
                DisplayLogEntryDataStore.getInstance().updateOdometerReading(0.0);
            } else {
                DisplayLogEntryDataStore.getInstance().updateOdometerReading(Double.valueOf(editable.toString()));
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public OdometerInputTextWatcher getOdometerInputTextWatcher() {
        return new OdometerInputTextWatcher();
    }

    // Callback triggered when the user alters the text in the FuelAmount EditText View
    private class FuelAmountTextWatcher implements TextWatcher {
        private Context context;

        public FuelAmountTextWatcher(Context initialContext) {
            context = initialContext;
        }

        @Override
        public void afterTextChanged(Editable editable) {

            // Ensure the text is not blank
            if (editable.toString().equals("")) {
                DisplayLogEntryDataStore.getInstance().updateFuelAmount(0.0);
            } else {
                DisplayLogEntryDataStore.getInstance().updateFuelAmount(Double.valueOf(editable.toString()));
            }

            // TODO: find an alternative method; this is hacky
            // Update the EntryTotalCost TextView
            Activity activity = (Activity) context;

            TextView entryTotalCostView = (TextView) activity.findViewById(R.id.entry_total_cost);
            entryTotalCostView.setText("Total Cost: $" + new DecimalFormat("#0.00").format(DisplayLogEntryDataStore.getInstance().getDisplayedEntry().getFuelCost()));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public FuelAmountTextWatcher getFuelAmountInputTextWatcher(Context context) {
        return new FuelAmountTextWatcher(context);
    }

    // Callback triggered when the user alters the text in the FuelUnitCost EditText View
    private class FuelUnitCostTextWatcher implements TextWatcher {
        Context context;

        public FuelUnitCostTextWatcher(Context initialContext) {
            context = initialContext;
        }

        @Override
        public void afterTextChanged(Editable editable) {

            // Ensure the text is not blank
            if (editable.toString().equals("")) {
                DisplayLogEntryDataStore.getInstance().updateFuelUnitCost(0.0);
            } else {
                DisplayLogEntryDataStore.getInstance().updateFuelUnitCost(Double.valueOf(editable.toString()));
            }

            // TODO: find an alternative method; this is hacky
            // Update the EntryTotalCost TextView
            Activity activity = (Activity) context;

            TextView entryTotalCostView = (TextView) activity.findViewById(R.id.entry_total_cost);
            entryTotalCostView.setText("Total Cost: $" + new DecimalFormat("#0.00").format(DisplayLogEntryDataStore.getInstance().getDisplayedEntry().getFuelCost()));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public FuelUnitCostTextWatcher getFuelUnitCostInputTextWatcher(Context context) {
        return new FuelUnitCostTextWatcher(context);
    }

    // Callback triggered when the user selects the SaveEntry Button
    private class SaveEntryOnClickListener implements View.OnClickListener {
        private Context context = null;
        private Intent displayIntent = null;

        public SaveEntryOnClickListener(Context initialContext, Intent initialDisplayIntent) {
            context = initialContext;
            displayIntent = initialDisplayIntent;
        }

        @Override
        public void onClick(View view) {
            LogEntry displayedEntry = DisplayLogEntryDataStore.getInstance().getDisplayedEntry();

            // Determine whether the user is updating an existing LogEntry or adding a new LogEntry
            if (displayIntent.getBooleanExtra(FuelTrackController.NEW_LOG_ENTRY_EXTRA, false)) {
                FuelTrackDataStore.getInstance().addLogEntry(context, displayedEntry);
            } else {
                LogEntry existingEntry = (LogEntry) displayIntent.getSerializableExtra(FuelTrackController.LOG_ENTRY_EXTRA);
                FuelTrackDataStore.getInstance().updateLogEntry(context, existingEntry, displayedEntry);
            }

            // Transition to the FuelTrackActivity
            Intent intent = new Intent(context, FuelTrackActivity.class);
            context.startActivity(intent);
        }
    }

    public SaveEntryOnClickListener getSaveEntryOnClickListener(Context context, Intent displayIntent) {
        return new SaveEntryOnClickListener(context, displayIntent);
    }
}
