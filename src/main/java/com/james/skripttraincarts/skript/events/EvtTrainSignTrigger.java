package com.james.skripttraincarts.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtTrainSignTrigger extends SkriptEvent {
    static {
        Skript.registerEvent("TrainCarts Sign Trigger", EvtTrainSignTrigger.class, TrainSignTriggerEvent.class,
                "train[ ]carts sign trigger [%-string%]",
                "tc sign trigger [%-string%]");
    }

    @Nullable
    private Literal<String> trigger;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        //noinspection unchecked
        trigger = (Literal<String>) args[0];
        return true;
    }

    @Override
    public boolean check(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return false;
        if (trigger == null) return true;

        String wanted = trigger.getSingle(event);
        if (wanted == null) return true;
        return wanted.equalsIgnoreCase(trainEvent.getTrigger());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "traincarts sign trigger";
    }
}
