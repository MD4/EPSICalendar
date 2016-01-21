package epsi.md4.com.epsicalendar.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Participant {

    public static final String STATUS = "status";
    public static final String ID = "id";

    /**
     * (Required)
     */
    @SerializedName(ID)
    @Expose
    private UUID id;
    /**
     * (Required)
     */
    @SerializedName(STATUS)
    @Expose
    private Participant.Status status;

    /**
     * (Required)
     *
     * @return The id
     */
    public UUID getId() {
        return id;
    }

    /**
     * (Required)
     *
     * @param id The id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public Participant withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * (Required)
     *
     * @return The status
     */
    public Participant.Status getStatus() {
        return status;
    }

    /**
     * (Required)
     *
     * @param status The status
     */
    public void setStatus(Participant.Status status) {
        this.status = status;
    }

    public Participant withStatus(Participant.Status status) {
        this.status = status;
        return this;
    }

    public static enum Status {

        @SerializedName("present")
        PRESENT("present"),
        @SerializedName("absent")
        ABSENT("absent"),
        @SerializedName("unknown")
        UNKNOWN("unknown");
        private static Map<String, Participant.Status> constants = new HashMap<String, Participant.Status>();

        static {
            for (Participant.Status c : values()) {
                constants.put(c.value, c);
            }
        }

        private final String value;

        private Status(String value) {
            this.value = value;
        }

        public static Participant.Status fromValue(String value) {
            Participant.Status constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

    }
}
