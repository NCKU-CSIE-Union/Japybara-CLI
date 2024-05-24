package main.resources.interfaces;

/**
 * The IRateLimit interface represents a rate limiting mechanism.
 * It provides methods to check if a token can be obtained or tried to be obtained.
 */
public interface IRateLimit {
    /**
     * Call and Wait until a token can be obtained.
     */
    public void GetToken();

    /**
     * Tries to obtain a token.
     *
     * @return true if a token is obtained, false otherwise.
     */
    public boolean TryGetToken();
}