package Model;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class Friend {

    @SerializedName("User1")
    @Expose
    private String User1;
    @SerializedName("User2")
    @Expose
    private String User2;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The User1
     */
    public String getUser1() {
        return User1;
    }

    /**
     *
     * @param User1
     * The User1
     */
    public void setUser1(String User1) {
        this.User1 = User1;
    }

    /**
     *
     * @return
     * The User2
     */
    public String getUser2() {
        return User2;
    }

    /**
     *
     * @param User2
     * The User2
     */
    public void setUser2(String User2) {
        this.User2 = User2;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}