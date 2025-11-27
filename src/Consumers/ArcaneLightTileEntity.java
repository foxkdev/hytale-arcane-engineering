package com.tumi.hytale.arcana.tile.consumers;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import com.tumi.hytale.arcana.tile.ArcaneFlowBaseTileEntity;

/**
 * Arcane Light - Flow-powered light source
 * 
 * Behavior:
 * - Consumes flow to emit light
 * - Minimum consumption: 1 flow/tick (dim light)
 * - Maximum consumption: 10 flow/tick (bright light)
 * - Light intensity scales with flow consumption
 * - Turns off when no flow is available
 */
public class ArcaneLightTileEntity extends ArcaneFlowBaseTileEntity {

    private static final int MIN_CONSUMPTION = 1;   // Minimum flow to stay lit
    private static final int MAX_CONSUMPTION = 10;  // Maximum consumption for full brightness
    private static final int LIGHT_LEVEL_MIN = 5;   // Minecraft light level when dim (0-15 scale)
    private static final int LIGHT_LEVEL_MAX = 15;  // Minecraft light level when bright
    
    private int currentLightLevel = 0;
    
    public ArcaneLightTileEntity() {
        // Small capacity - just enough for smooth operation
        super(IArcaneFlow.MAX_FLOW_CAPACITY_BASE);
    }
    
    @Override
    public void tick() {
        // 1. Determine how much flow we want to consume
        int desiredConsumption = calculateDesiredConsumption();
        
        // 2. Try to consume flow (limited by what we have available)
        int actualConsumption = Math.min(desiredConsumption, currentFlow);
        
        // 3. Consume the flow
        if (actualConsumption > 0) {
            this.extractFlow(actualConsumption, false);
        }
        
        // 4. Update light level based on actual consumption
        updateLightLevel(actualConsumption);
        
        // 5. Update block light emission in world
        // this.updateBlockLight(); // TODO: Implement with Hytale API
    }
    
    /**
     * Calculates how much flow this light wants to consume.
     * By default tries to consume maximum for brightest light.
     * Can be configured per instance.
     */
    private int calculateDesiredConsumption() {
        // Always try to consume MAX for full brightness
        // If not enough flow is available, it will consume what it can
        return MAX_CONSUMPTION;
    }
    
    /**
     * Updates the light level based on actual flow consumption.
     * @param consumption The actual flow consumed this tick
     */
    private void updateLightLevel(int consumption) {
        if (consumption == 0) {
            // No flow = no light
            currentLightLevel = 0;
        } else if (consumption < MIN_CONSUMPTION) {
            // Below minimum = flickering/very dim
            currentLightLevel = 1;
        } else {
            // Scale light level between MIN and MAX based on consumption
            // consumption range: 1-10 → light level range: 5-15
            float consumptionRatio = (float)(consumption - MIN_CONSUMPTION) / (MAX_CONSUMPTION - MIN_CONSUMPTION);
            currentLightLevel = (int)(LIGHT_LEVEL_MIN + consumptionRatio * (LIGHT_LEVEL_MAX - LIGHT_LEVEL_MIN));
        }
        
        // this.markDirty();
    }
    
    // --- Query Methods ---
    
    /**
     * Returns the current light level (0-15).
     * @return Light level for rendering/world lighting
     */
    public int getLightLevel() {
        return currentLightLevel;
    }
    
    /**
     * Checks if the light is currently on.
     * @return true if emitting light, false otherwise
     */
    public boolean isLit() {
        return currentLightLevel > 0;
    }
    
    /**
     * Gets the brightness percentage (0-100).
     * @return Brightness as percentage
     */
    public int getBrightnessPercent() {
        if (currentLightLevel == 0) return 0;
        return (int)((float)currentLightLevel / LIGHT_LEVEL_MAX * 100);
    }
    
    /**
     * Gets the current consumption rate.
     * @return Flow consumed per tick
     */
    public int getConsumptionRate() {
        // This is the actual consumption from last tick
        // Can be tracked if needed for UI/debugging
        return MAX_CONSUMPTION; // Target consumption
    }
}
