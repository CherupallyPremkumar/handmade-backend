package com.homebase.ecom.constants;

/**
 * Constants for OAuth2 and OIDC token claims.
 */
public final class OAuth2Constants {

    private OAuth2Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // OAuth2 Opaque Token Claims
    public static final String CLAIM_USER_ID = "user_id";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_NAME = "name";

    // OIDC/JWT Standard Claims
    public static final String CLAIM_SUB = "sub";
    public static final String CLAIM_GIVEN_NAME = "given_name";
    public static final String CLAIM_FAMILY_NAME = "family_name";
    public static final String CLAIM_PREFERRED_USERNAME = "preferred_username";

    // Keycloak Specific Claims
    public static final String CLAIM_REALM_ACCESS = "realm_access";
    public static final String CLAIM_RESOURCE_ACCESS = "resource_access";
}
