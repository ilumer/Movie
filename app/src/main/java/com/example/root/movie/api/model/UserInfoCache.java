package com.example.root.movie.api.model;

import java.io.Serializable;

/**
 * Created by root on 16-7-21.
 * 等到的用户信息的model
 */
public class UserInfoCache implements Serializable{

    /**
     * gravatar : {"hash":"c9e9fc152ee756a200db85757c29815d"}
     * 通过这个来获取头像
     */

    private AvatarInfo avatar;
    /**
     * avatar : {"gravatar":{"hash":"c9e9fc152ee756a200db85757c29815d"}}
     * id : 36
     * iso_639_1 : en
     * iso_3166_1 : CA
     * name : John Doe
     * include_adult : false
     * username : johndoe
     */

    private int id;
    private String iso_639_1;
    private String iso_3166_1;
    private String name;
    private boolean include_adult;
    private String username;

    public AvatarInfo getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarInfo avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInclude_adult() {
        return include_adult;
    }

    public void setInclude_adult(boolean include_adult) {
        this.include_adult = include_adult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public class AvatarInfo implements Serializable{
        /**
         * hash : c9e9fc152ee756a200db85757c29815d
         */

        private GravatarInfo gravatar;

        public GravatarInfo getGravatar() {
            return gravatar;
        }

        public void setGravatar(GravatarInfo gravatar) {
            this.gravatar = gravatar;
        }

        public class GravatarInfo implements Serializable{
            private String hash;

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }
        }
    }
}
