package com.james.skripttraincarts.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffDestroyEventTrain extends Effect {

    static {
        Skript.registerEffect(
                EffDestroyEventTrain.class,
                "destroy event-train",
                "destroy event train"
        );
    }

    @Override
    protected void execute(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return;

        MinecartGroup group = trainEvent.getGroup();
        if (group == null) return;

        group.destroy();
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "destroy event-train";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}