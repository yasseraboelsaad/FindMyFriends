package Model;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("xcoord")
    @Expose
    private Double xcoord;
    @SerializedName("ycoord")
    @Expose
    private Double ycoord;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("gx")
    @Expose
    private Float gx;
    @SerializedName("gy")
    @Expose
    private Float gy;
    @SerializedName("room")
    @Expose
    private String room;

    public User(String Name, String email, String id, String password, String image){
        this.Name=Name;
        this.email=email;
        this.id=id;
        this.password=password;
        this.image=image;
    }

    public User(String Name, String email, String id, String password, Double xcoord, Double ycoord, String image, String room){
        this.Name=Name;
        this.email=email;
        this.id=id;
        this.password=password;
        this.xcoord=xcoord;
        this.ycoord=ycoord;
        this.image=image;
        this.room=room;
    }
    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The xcoord
     */
    public Double getXcoord() {
        return xcoord;
    }

    /**
     *
     * @param xcoord
     * The xcoord
     */
    public void setXcoord(Double xcoord) {
        this.xcoord = xcoord;
    }

    /**
     *
     * @return
     * The ycoord
     */
    public Double getYcoord() {
        return ycoord;
    }

    /**
     *
     * @param ycoord
     * The ycoord
     */
    public void setYcoord(Double ycoord) {
        this.ycoord = ycoord;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The ycoord
     */
    public void setImage(String image) {
        this.image = image;
    }

    public String getstatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getGx() {
        return gx;
    }

    public void setGx(Float gx) {
        this.gx = gx;
    }

    public Float getGy() {
        return gy;
    }

    public void setGy(Float gy) {
        this.gy = gy;
    }

    /**
     *
     * @return
     * The room
     */
    public String getRoom() {
        return room;
    }

    /**
     *
     * @param room
     * The room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "{"+getName()+","+getId()+","+getEmail()+","+getPassword()+","+getXcoord()+","+getYcoord()+","+getImage()+","+getGx()+","+getGy()+"}";
    }

}