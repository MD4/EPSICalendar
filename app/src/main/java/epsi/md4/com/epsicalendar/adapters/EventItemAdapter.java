package epsi.md4.com.epsicalendar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import epsi.md4.com.epsicalendar.R;
import epsi.md4.com.epsicalendar.beans.Event;
import epsi.md4.com.epsicalendar.beans.User;
import epsi.md4.com.epsicalendar.beans.dao.UserDao;

public class EventItemAdapter extends GenericAdapter<Event> {

    private final String TAG = EventItemAdapter.class.getName();


    private static DateTimeFormatter mDateFormatter = DateTimeFormat.mediumDateTime().withLocale(Locale.FRANCE);
    private static UserDao mUserDao;

    public EventItemAdapter(Context context, List<Event> events) {
        super(context, events);
        if (mUserDao == null) {
            mUserDao = new UserDao(context);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_event_list_item, null);
        }

        Event event = getItem(position);

        return buildView(convertView, event);

    }

    public static View buildView(View convertView, Event event) {
        TextView tvTitle = (TextView) convertView.findViewById(R.id.event_item_title);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.event_item_desc);
        TextView tvDate = (TextView) convertView.findViewById(R.id.event_item_date);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.event_item_author);

        if (event == null) {
            return null;
        }

        tvTitle.setText(event.getTitle());
        tvDesc.setText(event.getDescription());
        tvDate.setText(event.getBegin().toString(mDateFormatter));
        tvAuthor.setText(findUserName(event.getAuthor()));
        return convertView;
    }

    public static String findUserName(UUID authorId) {
        User userById = mUserDao.getUserById(authorId.toString());
        return (userById == null) ? authorId.toString().substring(0, 4) : userById.getName();
    }
}
