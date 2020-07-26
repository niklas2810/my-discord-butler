package com.niklasarndt.discordbutler.modules;

/**
 * Created by Niklas on 2020/07/25
 */
public abstract class ButlerCommand {

    private final ButlerCommandInformation info;
    private ButlerModule module;

    public ButlerCommand(String name) {
        this(name, 0, 10);
    }

    public ButlerCommand(String name, int argsMin, int argsMax) {
        this(name, argsMin, argsMax, null);
    }

    public ButlerCommand(String name, int argsMin, int argsMax, String description, String... aliases) {
        this.info = new ButlerCommandInformation(name, aliases, argsMin, argsMax, description);
    }

    public final ButlerCommandInformation info() {
        return info;
    }

    public abstract void execute(ButlerContext context);

    public final ButlerModule module() {
        return module;
    }

    public final void setModule(ButlerModule module) {
        if (this.module != null) {
            throw new IllegalStateException("The module has already been defined.");
        }
        this.module = module;
    }
}
