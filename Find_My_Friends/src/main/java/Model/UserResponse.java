package Model;

import java.util.ArrayList;
import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class UserResponse {

    @SerializedName("Users")
    @Expose
    private List<User> Users = new ArrayList<User>();

    /**
     *
     * @return
     * The Users
     */
    public List<User> getUsers() {
        return Users;
    }

    /**
     *
     * @param Users
     * The Users
     */
    public void setUsers(List<User> Users) {
        this.Users = Users;
    }

}