package com.james.skripttraincarts.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffEjectEventPassengers extends Effect {

    static {
        Skript.registerEffect(
                EffEjectEventPassengers.class,
                "eject event-passengers",
                "eject event passengers"
        );
    }

    @Override
    protected void execute(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) {
            return;
        }

        for (Player player : trainEvent.getPassengers()) {
            player.leaveVehicle();
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "eject event-passengers";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}