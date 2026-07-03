package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprTrainSignLocation extends SimpleExpression<Location> {
    static {
        Skript.registerExpression(ExprTrainSignLocation.class, Location.class, ExpressionType.SIMPLE,
                "train[ ]carts sign location", "tc sign location");
    }

    @Override
    protected Location[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return new Location[0];
        Location location = trainEvent.getSignLocation();
        return location == null ? new Location[0] : new Location[]{location};
    }

    @Override public boolean isSingle() { return true; }
    @Override public Class<? extends Location> getReturnType() { return Location.class; }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "traincarts sign location";
    }
}
