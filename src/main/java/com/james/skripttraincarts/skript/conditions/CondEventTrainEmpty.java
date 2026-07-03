package com.james.skripttraincarts.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondEventTrainEmpty extends Condition {

    private boolean hasPassengersPattern;

    static {
        Skript.registerCondition(
                CondEventTrainEmpty.class,
                "event-train is empty",
                "event train is empty",
                "event-train (has|contains) passengers",
                "event train (has|contains) passengers"
        );
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        hasPassengersPattern = matchedPattern == 2 || matchedPattern == 3;
        return true;
    }

    @Override
    public boolean check(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) {
            return false;
        }

        boolean hasPassengers = !trainEvent.getPassengers().isEmpty();
        boolean result = hasPassengersPattern ? hasPassengers : !hasPassengers;

        return isNegated() ? !result : result;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return hasPassengersPattern ? "event-train has passengers" : "event-train is empty";
    }
}