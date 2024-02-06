package io.github.sleepy_evelyn.api.exception;

import net.minecraft.util.Identifier;

public class PortalCraftException extends Exception {

    public PortalCraftException(Identifier id, String cause) {
        super("An exception occurred trying to craft portal recipe: " + id.toString() + ". " + cause);
    }
}
