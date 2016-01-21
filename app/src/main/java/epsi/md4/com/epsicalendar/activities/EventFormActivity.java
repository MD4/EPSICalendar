package epsi.md4.com.epsicalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.UUID;

import epsi.md4.com.epsicalendar.Common;
import epsi.md4.com.epsicalendar.R;
import epsi.md4.com.epsicalendar.beans.Event;
import epsi.md4.com.epsicalendar.beans.Participant;
import epsi.md4.com.epsicalendar.beans.dao.EventDao;
import epsi.md4.com.epsicalendar.ws.ApiClient;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventFormActivity extends AppCompatActivity {

    public static final String TAG = EventFormActivity.class.getName();

    public static final int REQUEST_CODE = 1;
    private EventDao mEventDao;
    private ApiClient mApiClient;
    private TextView mTitle;
    private TextView mDesc;
    private DatePicker mDateBegin;
    private DatePicker mDateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init view
        setContentView(R.layout.activity_event_form);

        // Init field
        mEventDao = new EventDao(this);

        mApiClient = new ApiClient(this);

        mTitle = (TextView) findViewById(R.id.activity_event_form_title);
        mDesc = (TextView) findViewById(R.id.activity_event_form_desc);
        mDateBegin = (DatePicker) findViewById(R.id.activity_event_form_date_begin);
        mDateEnd = (DatePicker) findViewById(R.id.activity_event_form_date_end);
    }

    /**
     * Listener to OK button
     *
     * @param view
     */
    public void onClickOk(View view) {

        final Event event = new Event();

        event.setTitle(mTitle.getText().toString());
        event.setDescription(mDesc.getText().toString());
        event.setBegin(new DateTime(
                        mDateBegin.getYear(),
                        mDateBegin.getMonth() + 1,
                        mDateBegin.getDayOfMonth()
                        , 0, 0, 0
                )
        );
        event.setEnd(new DateTime(
                        mDateEnd.getYear(),
                        mDateEnd.getMonth() + 1,
                        mDateEnd.getDayOfMonth()
                        , 0, 0, 0
                )
        );

        SharedPreferences prefs = getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE);

        // md4 uuid : 1f854580-c5d8-44aa-af06-b84b4d89cddc
        String uuid = prefs.getString(Common.USER_ID_KEY, "1f854580-c5d8-44aa-af06-b84b4d89cddc");
        event.setAuthor(UUID.fromString(uuid));
        event.setParticipants(
                Collections.singletonList(new Participant()
                        .withId(UUID.fromString(uuid))
                        .withStatus(Participant.Status.PRESENT))
        );

        mApiClient.insertEvent(event).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                Log.d(TAG, String.format("responseCode is %d", response.code()));
                if (response.isSuccess()) {
                    Log.d(TAG, "Creation success");
                    mEventDao.insertEvent(event);
                    Intent intent = new Intent();
                    setResult(EventFormActivity.RESULT_OK, intent);
                    EventFormActivity.this.finish();
                } else {
                    Log.d(TAG, "Creation failed");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, String.format("FAILURE: %s", t.getMessage()));
            }
        });

    }

    public void onClickCancel(View view) {
        Intent intent = new Intent();
        setResult(EventFormActivity.RESULT_CANCELED, intent);
        EventFormActivity.this.finish();
    }
}
