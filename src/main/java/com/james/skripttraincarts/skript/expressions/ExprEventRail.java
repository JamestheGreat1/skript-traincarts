package com.james.skripttraincarts.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprEventRail extends SimpleExpression<Block> {

    static {
        Skript.registerExpression(
                ExprEventRail.class,
                Block.class,
                ExpressionType.SIMPLE,
                "event-rail",
                "event rail"
        );
    }

    @Override
    protected Block[] get(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) {
            return new Block[0];
        }

        Block block = trainEvent.getRailBlock();

        return block == null ? new Block[0] : new Block[]{block};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Block> getReturnType() {
        return Block.class;
    }

    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "event-rail";
    }
}