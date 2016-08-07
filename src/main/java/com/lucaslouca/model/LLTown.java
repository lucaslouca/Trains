package com.lucaslouca.model;

/**
 * Model representing a town.
 */
public class LLTown {
    private String name;

    /**
     * Create a new {@code LLTown} with the given name.
     *
     * @param name name of the town.
     */
    public LLTown(String name) {
        this.name = name;
    }

    /**
     * Name of this town.
     *
     * @return name of this town.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LLTown llTown = (LLTown) o;

        return name != null ? name.equals(llTown.name) : llTown.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
