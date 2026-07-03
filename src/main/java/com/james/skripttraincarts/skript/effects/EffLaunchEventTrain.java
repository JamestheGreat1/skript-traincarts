package com.james.skripttraincarts.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.bergerkiller.bukkit.tc.actions.MemberActionLaunchDirection;
import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EffLaunchEventTrain extends Effect {

    private Expression<Number> speed;
    private Expression<Number> duration;
    private PatternSpec spec;

    private enum SpeedUnit {
        BPT, BPS, MPS, MPH, KPH
    }

    private enum DirectionMode {
        DEFAULT, FORWARD, BACKWARD, NORTH, EAST, SOUTH, WEST
    }

    private enum DurationMode {
        NONE, BLOCKS, SECONDS
    }

    private record PatternSpec(SpeedUnit unit, DirectionMode direction, DurationMode duration) {}

    private static final List<PatternSpec> SPECS = new ArrayList<>();
    private static final List<String> PATTERNS = new ArrayList<>();

    static {
        register(DirectionMode.DEFAULT, "");
        register(DirectionMode.FORWARD, " forward");
        register(DirectionMode.BACKWARD, " backward");
        register(DirectionMode.NORTH, " north");
        register(DirectionMode.EAST, " east");
        register(DirectionMode.SOUTH, " south");
        register(DirectionMode.WEST, " west");

        Skript.registerEffect(
                EffLaunchEventTrain.class,
                PATTERNS.toArray(new String[0])
        );
    }

    private static void register(DirectionMode direction, String directionText) {
        add(direction, SpeedUnit.BPT, DurationMode.NONE, "launch event-train" + directionText + " at %number%");
        add(direction, SpeedUnit.BPT, DurationMode.NONE, "launch event-train" + directionText + " at %number% bpt");

        add(direction, SpeedUnit.BPS, DurationMode.NONE, "launch event-train" + directionText + " at %number% bps");
        add(direction, SpeedUnit.MPS, DurationMode.NONE, "launch event-train" + directionText + " at %number% m/s");
        add(direction, SpeedUnit.MPH, DurationMode.NONE, "launch event-train" + directionText + " at %number% mph");
        add(direction, SpeedUnit.KPH, DurationMode.NONE, "launch event-train" + directionText + " at %number% kph");
        add(direction, SpeedUnit.KPH, DurationMode.NONE, "launch event-train" + directionText + " at %number% km/h");

        add(direction, SpeedUnit.BPT, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% over %number% blocks");
        add(direction, SpeedUnit.BPT, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% bpt over %number% blocks");
        add(direction, SpeedUnit.BPS, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% bps over %number% blocks");
        add(direction, SpeedUnit.MPS, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% m/s over %number% blocks");
        add(direction, SpeedUnit.MPH, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% mph over %number% blocks");
        add(direction, SpeedUnit.KPH, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% kph over %number% blocks");
        add(direction, SpeedUnit.KPH, DurationMode.BLOCKS, "launch event-train" + directionText + " at %number% km/h over %number% blocks");

        add(direction, SpeedUnit.BPT, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% over %number% seconds");
        add(direction, SpeedUnit.BPT, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% bpt over %number% seconds");
        add(direction, SpeedUnit.BPS, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% bps over %number% seconds");
        add(direction, SpeedUnit.MPS, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% m/s over %number% seconds");
        add(direction, SpeedUnit.MPH, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% mph over %number% seconds");
        add(direction, SpeedUnit.KPH, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% kph over %number% seconds");
        add(direction, SpeedUnit.KPH, DurationMode.SECONDS, "launch event-train" + directionText + " at %number% km/h over %number% seconds");
    }

    private static void add(DirectionMode direction, SpeedUnit unit, DurationMode duration, String pattern) {
        SPECS.add(new PatternSpec(unit, direction, duration));
        PATTERNS.add(pattern);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        speed = (Expression<Number>) expressions[0];

        if (expressions.length > 1) {
            duration = (Expression<Number>) expressions[1];
        }

        spec = SPECS.get(matchedPattern);
        return true;
    }

    @Override
    protected void execute(Event event) {
        if (!(event instanceof TrainSignTriggerEvent trainEvent)) return;

        MinecartGroup group = trainEvent.getGroup();
        if (group == null || group.isEmpty()) return;

        Number speedNumber = speed.getSingle(event);
        if (speedNumber == null) return;

        double force = toBlocksPerTick(speedNumber.doubleValue(), spec.unit());
        BlockFace direction = getDirection(trainEvent);

        if (spec.duration() == DurationMode.NONE) {
            if (spec.direction() == DirectionMode.BACKWARD) {
                group.setForwardForce(-force);
            } else {
                group.setForwardForce(force);
            }
            return;
        }

        Number durationNumber = duration.getSingle(event);
        if (durationNumber == null) return;

        MemberActionLaunchDirection action = new MemberActionLaunchDirection();

        if (spec.duration() == DurationMode.SECONDS) {
            int ticks = Math.max(1, (int) Math.round(durationNumber.doubleValue() * 20.0));
            action.initTime(ticks, force, direction);
        } else {
            double blocks = Math.max(0.01, durationNumber.doubleValue());
            action.initDistance(blocks, force, direction);
        }

        group.getActions().clear();
        group.tail().getActions().addGroupAction(action);
    }

    private double toBlocksPerTick(double value, SpeedUnit unit) {
        return switch (unit) {
            case BPT -> value;
            case BPS, MPS -> value / 20.0;
            case MPH -> (value * 0.44704) / 20.0;
            case KPH -> (value / 3.6) / 20.0;
        };
    }

    private BlockFace getDirection(TrainSignTriggerEvent event) {
        BlockFace enterFace = event.getSignInfo().getCartEnterFace();

        return switch (spec.direction()) {
            case DEFAULT, FORWARD -> enterFace;
            case BACKWARD -> enterFace.getOppositeFace();
            case NORTH -> BlockFace.NORTH;
            case EAST -> BlockFace.EAST;
            case SOUTH -> BlockFace.SOUTH;
            case WEST -> BlockFace.WEST;
        };
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "launch event-train";
    }
}