package com.johnymuffin.beta.wildtp;

public class JWildTPWorld {
    private final boolean enabled;
    private final int centerX;
    private final int centerZ;
    private final int minimumRadius;
    private final int maximumRadius;
    private final int coolDown;

    private final boolean redirectWorld;
    private final String worldName;

    public JWildTPWorld(boolean enabled, int centerX, int centerZ, int minimumRadius, int maximumRadius, int coolDown, boolean redirectWorld, String redirectWorldName) {
        this.enabled = enabled;
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.minimumRadius = minimumRadius;
        this.maximumRadius = maximumRadius;
        this.coolDown = coolDown;
        this.redirectWorld = redirectWorld;
        this.worldName = redirectWorldName;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterZ() {
        return centerZ;
    }

    public int getMinimumRadius() {
        return minimumRadius;
    }

    public int getMaximumRadius() {
        return maximumRadius;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public boolean isRedirectWorld() {
        return redirectWorld;
    }

    public String getRedirectWorldName() {
        return worldName;
    }
}
