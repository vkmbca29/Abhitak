package softpro.abhitak.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import softpro.abhitak.R;
import softpro.abhitak.models.JanpadModel;

public class JanpadAdapter extends ArrayAdapter<JanpadModel> {
    public JanpadAdapter(Context context, ArrayList<JanpadModel> model) {
        super(context, 0, model);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        JanpadModel janpad = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drop_down, parent, false);
        }
        // Lookup view for data population
        TextView janpadName = convertView.findViewById(R.id.janpadName);
        janpadName.setText(janpad.getName());
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        JanpadModel janpad = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drop_down, parent, false);
        }
        // Lookup view for data population
        TextView janpadName = convertView.findViewById(R.id.janpadName);
        janpadName.setText(janpad.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
