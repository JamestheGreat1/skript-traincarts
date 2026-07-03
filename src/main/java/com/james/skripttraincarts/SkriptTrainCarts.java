package com.james.skripttraincarts;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.bergerkiller.bukkit.tc.signactions.SignAction;
import com.james.skripttraincarts.traincarts.SignActionSkript;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SkriptTrainCarts extends JavaPlugin {
    private static SkriptTrainCarts instance;
    private SkriptAddon addon;
    private SignActionSkript signAction;

    @Override
    public void onEnable() {
        instance = this;

        try {
            addon = Skript.registerAddon(this);
            addon.loadClasses("com.james.skripttraincarts", "skript");
        } catch (IOException e) {
            getLogger().severe("Could not load Skript classes: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        signAction = SignAction.register(new SignActionSkript());

        Bukkit.getConsoleSender().sendMessage(
                "§f[§bskript-§atraincarts§f] §f- made by James_theGreat1 -"
        );
        Bukkit.getConsoleSender().sendMessage(
                "§f[§bskript-§atraincarts§f] §aEnabled §7(v" + getDescription().getVersion() + ")"
        );
    }

    @Override
    public void onDisable() {
        if (signAction != null) {
            SignAction.unregister(signAction);
            signAction = null;
        }

        instance = null;
    }

    public static SkriptTrainCarts getInstance() {
        return instance;
    }
}