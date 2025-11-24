package com.tumi.hytale.arcana.tile;

import com.tumi.hytale.arcana.power.IArcaneFlow;

public class CrystalPillarTileEntity extends ArcaneChannelTileEntity {

    private static final int BASE_GENERATION_RATE = 5; 
    private int currentGenerationRate = BASE_GENERATION_RATE; 

    public CrystalPillarTileEntity() {
        // Higher capacity to act as a primary energy buffer.
        super(IArcaneFlow.MAX_FLOW_CAPACITY_BASE * 5); 
    }
    
    @Override
    public void tick() {
        // 1. Generation Logic (before propagation):
        int spaceAvailable = this.getMaximumCapacity() - this.getCurrentFlow();
        int flowToGenerate = Math.min(currentGenerationRate, spaceAvailable);
        
        if (flowToGenerate > 0) {
            // Add directly to currentFlow (more efficient than injectFlow).
            this.currentFlow += flowToGenerate;
            // this.markDirty(); // Uncomment when Hytale API is available
        }
        
        // 2. Propagation: 
        // Call the parent's tick() method (ArcaneChannelTileEntity) to distribute 
        // the newly generated flow to connected blocks.
        super.tick();
        
        // 3. Update generation rate based on world conditions
        updateGenerationRate();
    }
    
    // Method to check Hytale world conditions and adjust generation rate.
    private void updateGenerationRate() {
        // TODO: Check world conditions (e.g., proximity to Mana Nexus, time of day, biome).
        // Example implementation:
        // if (isNearManaNexus()) {
        //     this.currentGenerationRate = BASE_GENERATION_RATE * 2;
        // } else {
        //     this.currentGenerationRate = BASE_GENERATION_RATE;
        // }
        this.currentGenerationRate = BASE_GENERATION_RATE; // Placeholder
    }
    
    // Optional: Getter for debugging or UI display
    public int getGenerationRate() {
        return this.currentGenerationRate;
    }
}