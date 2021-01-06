package com.johnymuffin.beta.wildtp;

public class JWildTPWorld {
    private final boolean enabled;
    private final int centerX;
    private final int centerZ;
    private final int minimumRadius;
    private final int maximumRadius;
    private final int coolDown;

    public JWildTPWorld(boolean enabled, int centerX, int centerZ, int minimumRadius, int maximumRadius, int coolDown) {
        this.enabled = enabled;
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.minimumRadius = minimumRadius;
        this.maximumRadius = maximumRadius;
        this.coolDown = coolDown;
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
}
