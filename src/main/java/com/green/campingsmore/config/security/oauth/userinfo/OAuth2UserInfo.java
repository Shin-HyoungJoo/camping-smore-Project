package com.green.campingsmore.config.security.oauth.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();
    public abstract String getPassword();
    public abstract String getEmail();

    public abstract String getName();
    public abstract String getBirthDate();
    public abstract String getPhone();
    public abstract Integer getGender();
    public abstract String getUserAddress();

    public abstract String getImageUrl();
}
