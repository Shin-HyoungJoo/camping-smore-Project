package com.green.campingsmore.config.security.oauth.userinfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo  {
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) { return null; }
        return (String) response.get("id");
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) { return null; }
        return (String) response.get("name");
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
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) { return null; }
        return (String) response.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) {
            return null;
        }
        return (String) response.get("profile_image");
    }
}
