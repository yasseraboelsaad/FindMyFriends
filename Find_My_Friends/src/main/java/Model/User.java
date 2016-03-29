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

    public User(String Name, String email, String id, String password){
        this.Name=Name;
        this.email=email;
        this.id=id;
        this.password=password;
    }

    public User(String Name, String email, String id, String password, Double xcoord, Double ycoord, String image){
        this.Name=Name;
        this.email=email;
        this.id=id;
        this.password=password;
        this.xcoord=xcoord;
        this.ycoord=ycoord;
        this.image=image;
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


    @Override
    public String toString() {
        return "{"+getName()+","+getId()+","+getEmail()+","+getPassword()+","+getXcoord()+","+getYcoord()+","+getImage()+"}";
    }

}