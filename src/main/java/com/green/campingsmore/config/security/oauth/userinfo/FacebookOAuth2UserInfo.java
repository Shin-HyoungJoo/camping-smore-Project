package com.green.campingsmore.config.security.oauth.userinfo;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getBirthDate() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public Integer getGender() {
        return null;
    }

    @Override
    public String getUserAddress() {
        return null;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("imageUrl");
    }

}
