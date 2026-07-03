package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprTrainSignLine extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprTrainSignLine.class, String.class, ExpressionType.SIMPLE,
                "line %number% of train[ ]carts sign", "train[ ]carts sign line %number%",
                "line %number% of tc sign", "tc sign line %number%");
    }

    private Expression<Number> line;

    @Override
    protected String[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return new String[0];
        Number number = line.getSingle(event);
        if (number == null) return new String[0];
        return new String[]{trainEvent.getLine(number.intValue())};
    }

    @Override public boolean isSingle() { return true; }
    @Override public Class<? extends String> getReturnType() { return String.class; }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        //noinspection unchecked
        line = (Expression<Number>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "line of traincarts sign";
    }
}
