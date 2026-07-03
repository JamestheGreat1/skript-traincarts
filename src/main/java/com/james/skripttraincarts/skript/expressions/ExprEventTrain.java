package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprEventTrain extends SimpleExpression<MinecartGroup> {
    static {
        Skript.registerExpression(ExprEventTrain.class, MinecartGroup.class, ExpressionType.SIMPLE,
                "event-train", "train[ ]carts train", "tc train");
    }

    @Override
    protected MinecartGroup[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return new MinecartGroup[0];
        MinecartGroup group = trainEvent.getGroup();
        return group == null ? new MinecartGroup[0] : new MinecartGroup[]{group};
    }

    @Override public boolean isSingle() { return true; }
    @Override public Class<? extends MinecartGroup> getReturnType() { return MinecartGroup.class; }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "event train";
    }
}
