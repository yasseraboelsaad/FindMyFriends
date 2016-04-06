package com.bachelorproject.yasser.findmyfriends.Model;

import java.util.ArrayList;
import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class FriendResponse {

    @SerializedName("Friends")
    @Expose
    private List<Friend> Friends = new ArrayList<Friend>();

    /**
     *
     * @return
     * The Friends
     */
    public List<Friend> getFriends() {
        return Friends;
    }

    /**
     *
     * @param Friends
     * The Friends
     */
    public void setFriends(List<Friend> Friends) {
        this.Friends = Friends;
    }

}