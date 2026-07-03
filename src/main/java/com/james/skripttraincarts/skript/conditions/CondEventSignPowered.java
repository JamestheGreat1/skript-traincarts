package com.james.skripttraincarts.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondEventSignPowered extends Condition {

    static {
        Skript.registerCondition(
                CondEventSignPowered.class,
                "event-sign is powered",
                "event sign is powered"
        );
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) {
            return false;
        }

        boolean result = trainEvent.isPowered();
        return isNegated() ? !result : result;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "event-sign is powered";
    }
}