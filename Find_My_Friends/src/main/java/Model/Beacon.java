
package Model;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class Beacon {

    @SerializedName("UUID")
    @Expose
    private String UUID;
    @SerializedName("Location")
    @Expose
    private String Location;
    @SerializedName("x")
    @Expose
    private String x;
    @SerializedName("y")
    @Expose
    private String y;
    @SerializedName("Building")
    @Expose
    private String Building;

    /**
     *
     * @return
     * The UUID
     */
    public String getUUID() {
        return UUID;
    }

    /**
     *
     * @param UUID
     * The UUID
     */
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    /**
     *
     * @return
     * The Location
     */
    public String getLocation() {
        return Location;
    }

    /**
     *
     * @param Location
     * The Location
     */
    public void setLocation(String Location) {
        this.Location = Location;
    }

    /**
     *
     * @return
     * The x
     */
    public String getX() {
        return x;
    }

    /**
     *
     * @param x
     * The x
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     *
     * @return
     * The y
     */
    public String getY() {
        return y;
    }

    /**
     *
     * @param y
     * The y
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     *
     * @return
     * The Building
     */
    public String getBuilding() {
        return Building;
    }

    /**
     *
     * @param Building
     * The y
     */
    public void setBuilding(String Building) {
        this.Building = Building;
    }

    @Override
    public String toString (){
        return "{"+getUUID()+","+getLocation()+","+getX()+","+getY()+","+getBuilding()+"}";
    }

}