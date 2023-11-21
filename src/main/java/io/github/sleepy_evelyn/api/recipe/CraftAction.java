package io.github.sleepy_evelyn.api.recipe;

/**
 * Defines what happens to the resultant item(s) after they have been crafted.
 **/
public enum CraftAction {
    // Return the result within the same dimension
    RETURN,
    // Send the result through the portal.
    PASS_THROUGH,
    // Merge the resultant block(s) into the portal frame. Fails if the recipe result isn't a block.
    MERGE,
    // The action is ignored. Useful for modded portal crafting recipes that wish to define their own behaviour
    NONE
}
