package main.resources;
import main.resources.interfaces.IRateLimit;

/**
 * RateLimit
 * Implements a basic rate limiting using the token bucket algorithm.
 * Reference: https://github.com/bbeck/token-bucket/
 */
public class RateLimit implements IRateLimit {
    private final int maxTokens;
    private final int refillRate;
    private int tokens;
    private long lastRefillTimestamp;

    public RateLimit(int numTokens, int refillRate) {
        this.maxTokens = numTokens;
        this.refillRate = refillRate;
        this.tokens = numTokens; // Start with max capacity
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    /**
     * Refills the tokens in the rate limiter.
     * This method is synchronized to ensure thread safety.
     * It calculates the duration since the last refill and adds tokens based on the refill rate.
     * The refill rate is per second.
     * The maximum number of tokens is limited by the maxTokens variable.
     */
    private synchronized void refill() {
        long now = System.currentTimeMillis();
        long durationSinceLastRefill = now - lastRefillTimestamp;

        if (durationSinceLastRefill > 1000) { // refill rate is per second
            int tokensToAdd = (int) (durationSinceLastRefill / 1000 * refillRate);
            tokens = Math.min(tokens + tokensToAdd, maxTokens);
            lastRefillTimestamp = now;
        }
    }


    @Override
    public void GetToken() {
        while (true) {
            if (TryGetToken()) {
                return;
            }
            try {
                Thread.sleep(1000 / refillRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized boolean TryGetToken() {
        refill(); // Ensure the bucket is up to date
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }
}
