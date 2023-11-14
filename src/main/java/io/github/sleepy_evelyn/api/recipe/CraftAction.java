package io.github.sleepy_evelyn.api.recipe;

/**
 * Allows developers to decide what happens to item(s) after they have been crafted through a variety of different methods.
 * Craft Actions are optional and can be ignored entirely if you want.
 */
public enum CraftAction {
    // Return the result in the opposite direction to the last inputted ingredient within the same dimension
    RETURN,
    // Send the result through the portal
    PASS_THROUGH,
    /* Merge the resultant block(s) into the portal frame. Fails if the recipe result isn't a block */
    MERGE,
    // Ignore Craft Actions
    NONE
}
