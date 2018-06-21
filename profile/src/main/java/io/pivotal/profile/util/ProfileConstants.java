package io.pivotal.profile.util;

public @interface ProfileConstants {
    public final String PROFILE_ROOT = "/api/profile";
    public final String USERID = "/{userId}";
    public final String PRIVACY = "/privacy" + USERID;
    public final String PRIVACIES = "/privacies";
    public final String PRIVACY_RANDOM = "/privacy/random" + USERID;
    public final String CONTACT = "/contact" + USERID;
    public final String CONTACTS = "/contacts";
    public final String CONTACT_RANDOM = "/contact/random" + USERID;
}
