package com.fueltrack.adlawren.adlawren_fueltrack;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by adlawren on 24/01/16.
 *
 * Purpose: This class is used as a factory for various callbacks used by the FuelTrackActivity class.
 *
 * Design: This class consists of privately defined class which implement the appropriate callback interfaces
 *         needed for the particular callbacks of Views in the FuelTrackActivity.
 *
 * Outstanding Issues: At present, none observed.
 */
public class FuelTrackController {
    private static FuelTrackController instance = new FuelTrackController();

    public static FuelTrackController getInstance() {
        return instance;
    }

    private FuelTrackController() {

    }

    // Keys used in subsequent callbacks to retrieve information sent in Intents
    public static final String LOG_ENTRY_EXTRA = "com.fueltrack.adlawren.adlawren_fuel_track.log.entry";
    public static final String NEW_LOG_ENTRY_EXTRA = "com.fueltrack.adlawren.adlawren_fuel_track.new.log.entry";

    // Callback triggered when the user selects a LogEntry from the ListView of LogEntries
    private class LogEntryOnItemClickListener implements AdapterView.OnItemClickListener {
        private Context context = null;

        public LogEntryOnItemClickListener(Context initialContext) {
            context = initialContext;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get the selected LogEntry
            LogEntry selectedLogEntry = (LogEntry) parent.getItemAtPosition(position);

            // Initialize the contents of the DisplayLogEntryDataStore
            DisplayLogEntryDataStore.getInstance().updateLogEntry(new LogEntry(selectedLogEntry));

            // Start the DisplayLogEntryActivity
            Intent intent = new Intent(context, DisplayLogEntryActivity.class);
            intent.putExtra(LOG_ENTRY_EXTRA, selectedLogEntry);
            intent.putExtra(NEW_LOG_ENTRY_EXTRA, false);
            context.startActivity(intent);
        }
    }

    public LogEntryOnItemClickListener getLogEntryOnClickListener(Context context) {
        return new LogEntryOnItemClickListener(context);
    }

    // Callback associated with the selection of the Add/"plus" Button displayed in the main Activity
    private class AddNewEntryOnClickListener implements View.OnClickListener {
        private Context context = null;

        public AddNewEntryOnClickListener(Context initialContext) {
            context = initialContext;
        }

        @Override
        public void onClick(View view) {

            // Initialize the contents of the DisplayLogEntryDataStore
            DisplayLogEntryDataStore.getInstance().updateLogEntry(new LogEntry());

            // Transition to the DisplayLogEntryActivity
            Intent intent = new Intent(context, DisplayLogEntryActivity.class);
            intent.putExtra(NEW_LOG_ENTRY_EXTRA, true);
            context.startActivity(intent);
        }
    }

    public AddNewEntryOnClickListener getNewEntryOnClickListener(Context context) {
        return new AddNewEntryOnClickListener(context);
    }
}
