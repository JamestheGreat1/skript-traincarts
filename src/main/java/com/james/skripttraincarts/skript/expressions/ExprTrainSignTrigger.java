package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Expression;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprTrainSignTrigger extends SimpleExpression<String> {
    static {
        Skript.registerExpression(
                ExprTrainSignTrigger.class,
                String.class,
                ExpressionType.SIMPLE,
                "train[ ]carts sign trigger",
                "tc sign trigger",
                "event-trigger",
                "event trigger"
        );
    }

    @Override
    protected String[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return new String[0];
        return new String[]{trainEvent.getTrigger()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "event-trigger";
    }
}