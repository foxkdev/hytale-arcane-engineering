package com.tumi.hytale.arcana.tile;

import com.tumi.hytale.arcana.power.IArcaneFlow;
// Assumed base Hytale TileEntity class:
// import com.hytale.api.block.TileEntity; 

public abstract class ArcaneFlowBaseTileEntity implements IArcaneFlow { // extends TileEntity

    protected int currentFlow = 0;
    protected final int maximumCapacity;

    public ArcaneFlowBaseTileEntity(int capacity) {
        this.maximumCapacity = capacity;
    }

    // --- IArcaneFlow Implementation: Storage ---

    @Override
    public int injectFlow(int amount, boolean simulate) {
        int spaceAvailable = this.maximumCapacity - this.currentFlow;
        int flowAccepted = Math.min(amount, spaceAvailable);

        if (!simulate) {
            this.currentFlow += flowAccepted;
            // Notify game engine of state change: this.markDirty(); 
        }

        return amount - flowAccepted;
    }

    @Override
    public int extractFlow(int amount, boolean simulate) {
        int flowExtracted = Math.min(amount, this.currentFlow);

        if (!simulate) {
            this.currentFlow -= flowExtracted;
            // this.markDirty();
        }

        return flowExtracted;
    }

    @Override
    public int getCurrentFlow() {
        return this.currentFlow;
    }

    @Override
    public int getMaximumCapacity() {
        return this.maximumCapacity;
    }
    
    // tick() remains abstract or is overridden by subclasses for logic.
}