package epsi.md4.com.epsicalendar.activities;

import android.test.ActivityInstrumentationTestCase2;

public class EventFormActivityTest extends ActivityInstrumentationTestCase2<EventFormActivity> {

    private EventFormActivity mEventFormActivity;

    public EventFormActivityTest() {
        super(EventFormActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEventFormActivity = getActivity();
    }

    public void testPreconditions() {
        assertTrue(mEventFormActivity != null);
    }

}