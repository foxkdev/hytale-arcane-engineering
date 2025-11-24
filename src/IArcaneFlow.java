package com.tumi.hytale.arcana.power; // TODO: Pending Hytale API

/**
 * Defines the API for Arcane Flow (Mana) transfer and storage.
 * All magical components must implement this interface.
 */
public interface IArcaneFlow {

    int MAX_FLOW_CAPACITY_BASE = 100;

    /**
     * Attempts to inject Arcane Flow into this component.
     * @param amount The amount of Flow to inject.
     * @param simulate If true, the operation is simulated without changing the state.
     * @return The amount of Flow that was NOT accepted (the remainder).
     */
    int injectFlow(int amount, boolean simulate);

    /**
     * Attempts to extract Arcane Flow from this component.
     * @param amount The amount of Flow to extract.
     * @param simulate If true, the operation is simulated without changing the state.
     * @return The amount of Flow that was ACTUALLY extracted.
     */
    int extractFlow(int amount, boolean simulate);

    /**
     * Returns the current stored amount of Arcane Flow.
     */
    int getCurrentFlow();

    /**
     * Returns the maximum storage capacity for Arcane Flow.
     */
    int getMaximumCapacity();

    /**
     * Logic for flow update and propagation, called every game tick.
     */
    void tick();
}