package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprEventCart extends SimpleExpression<MinecartMember> {
    static {
        Skript.registerExpression(ExprEventCart.class, MinecartMember.class, ExpressionType.SIMPLE,
                "event-cart", "train[ ]carts cart", "tc cart");
    }

    @Override
    protected MinecartMember[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return new MinecartMember[0];
        MinecartMember<?> member = trainEvent.getMember();
        return member == null ? new MinecartMember[0] : new MinecartMember[]{member};
    }

    @Override public boolean isSingle() { return true; }
    @Override public Class<? extends MinecartMember> getReturnType() { return MinecartMember.class; }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "event cart";
    }
}
