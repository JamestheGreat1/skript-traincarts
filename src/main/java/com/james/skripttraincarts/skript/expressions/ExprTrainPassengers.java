package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprTrainPassengers extends SimpleExpression<Player> {

    static {
        Skript.registerExpression(
                ExprTrainPassengers.class,
                Player.class,
                ExpressionType.SIMPLE,
                "event-passengers",
                "event passengers",
                "train[ ]carts passengers",
                "tc passengers"
        );
    }

    @Override
    protected Player[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) {
            return new Player[0];
        }

        return trainEvent.getPassengers().toArray(new Player[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "event-passengers";
    }
}