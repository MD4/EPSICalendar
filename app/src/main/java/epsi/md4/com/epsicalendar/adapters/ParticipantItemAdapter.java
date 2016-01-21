package epsi.md4.com.epsicalendar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import epsi.md4.com.epsicalendar.R;
import epsi.md4.com.epsicalendar.beans.Participant;

import static epsi.md4.com.epsicalendar.adapters.EventItemAdapter.findUserName;

public class ParticipantItemAdapter extends GenericAdapter<Participant> {

    public ParticipantItemAdapter(Context context, List<Participant> mList) {
        super(context, mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_participant_list_item, null);
        }

        Participant item = getItem(position);
        if (item == null) {
            return convertView;
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.participant_item_name);
        TextView statusView = (TextView) convertView.findViewById(R.id.participant_item_status);


        nameView.setText(findUserName(item.getId()));
        statusView.setText(item.getStatus().toString());

        return convertView;
    }
}
