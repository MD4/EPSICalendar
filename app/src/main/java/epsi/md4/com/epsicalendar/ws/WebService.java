package epsi.md4.com.epsicalendar.ws;


import java.util.List;

import epsi.md4.com.epsicalendar.beans.Event;
import epsi.md4.com.epsicalendar.beans.Login;
import epsi.md4.com.epsicalendar.beans.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface WebService {

    @GET("/meetings")
    Call<List<Event>> listEvents();

    @POST("/meetings")
    Call<Void> insertEvent(@Body Event event);

    @GET("/meetings/{id}")
    Call<Event> getEvent(@Path("id") String id);

    @POST("/users")
    Call<Void> register(@Body User u);

    @POST("/login")
    Call<Void> login(@Body Login u);

    @POST("/logout")
    Call<Void> logout();

    @GET("/users")
    Call<List<User>> listUsers();

    @GET("/users/search/{username}")
    Call<User> search(@Path("username") String username);

    @POST("/meetings/{id}/accept")
    Call<Event> acceptMeeting(@Path("id") String id);

    @POST("/meetings/{id}/deny")
    Call<Event> denyMeeting(@Path("id") String id);

    @POST("/meetings/{id}/invite")
    Call<Event> inviteMeeting(@Path("id") String id, @Body String userId);
}
