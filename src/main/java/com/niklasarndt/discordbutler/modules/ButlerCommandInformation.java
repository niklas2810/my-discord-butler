package com.niklasarndt.discordbutler.modules;

import java.util.Objects;

/**
 * Created by Niklas on 2020/07/25
 */
public class ButlerCommandInformation {

    private final String name;
    private final String[] aliases;
    private final int argsMin;
    private final int argsMax;
    private final String description;

    protected ButlerCommandInformation(String name, String[] aliases, int argsMin, int argsMax, String description) {
        Objects.requireNonNull(name);

        this.name = name;
        this.aliases = aliases != null ? aliases : new String[0];
        this.argsMin = argsMin;
        this.argsMax = argsMax;
        this.description = description != null ? description : "n/a";

        if (this.name.length() > 16) {
            throw new IllegalArgumentException(String.format(
                    "The module name '%s' is too long (max: 16 characters, given: %d characters).",
                    name, this.name.length()));
        }
        if (argsMin < 0 || argsMax < argsMin) {
            throw new IllegalArgumentException(
                    String.format("Invalid argument limits for command \"%s\". (min: %d, max: %d)",
                            name, argsMin, argsMax));
        }
        if (this.description.length() > 128) {
            throw new IllegalArgumentException(String.format(
                    "The description for the command '%s' is too long (max: 128 characters, given: %d characters).",
                    name, this.description.length()));
        }
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public int getArgsMin() {
        return argsMin;
    }

    public int getArgsMax() {
        return argsMax;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasAlias(String name) {
        for (String al : aliases) {
            if (al.equals(name)) return true;
        }
        return false;
    }
}
