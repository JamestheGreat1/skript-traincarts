package com.james.skripttraincarts.bukkit;

import com.bergerkiller.bukkit.common.entity.type.CommonMinecart;
import com.bergerkiller.bukkit.tc.controller.MinecartGroup;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TrainSignTriggerEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SignActionEvent signInfo;
    private final String trigger;
    private final boolean trainSign;
    private final boolean cartSign;

    private final MinecartGroup group;
    private final MinecartMember<?> member;

    public TrainSignTriggerEvent(SignActionEvent signInfo, String trigger, boolean trainSign, boolean cartSign) {
        this.signInfo = signInfo;
        this.trigger = trigger;
        this.trainSign = trainSign;
        this.cartSign = cartSign;
        this.group = signInfo.getGroup();
        this.member = signInfo.getMember();
    }

    public SignActionEvent getSignInfo() {
        return signInfo;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getData() {
        return getLine(4);
    }

    public String getActionName() {
        if (signInfo.getAction() == null) {
            return "unknown";
        }
        return signInfo.getAction().name().toLowerCase();
    }

    public boolean isTrainSign() {
        return trainSign;
    }

    public boolean isCartSign() {
        return cartSign;
    }

    @Nullable
    public MinecartGroup getGroup() {
        return group;
    }

    @Nullable
    public MinecartMember<?> getMember() {
        return member;
    }

    @Nullable
    public Block getSignBlock() {
        return signInfo.getBlock();
    }

    @Nullable
    public Block getRailBlock() {
        return signInfo.hasRails() ? signInfo.getRails() : null;
    }

    public boolean isPowered() {
        return signInfo.isPowered();
    }

    public List<Player> getPassengers() {
        List<Player> passengers = new ArrayList<>();

        if (cartSign && member != null) {
            CommonMinecart<?> entity = member.getEntity();
            if (entity != null && entity.getEntity() != null) {
                for (Entity passenger : entity.getEntity().getPassengers()) {
                    if (passenger instanceof Player player) {
                        passengers.add(player);
                    }
                }
            }
            return passengers;
        }

        if (group != null) {
            for (MinecartMember<?> cart : group) {
                CommonMinecart<?> entity = cart.getEntity();
                if (entity == null || entity.getEntity() == null) {
                    continue;
                }

                for (Entity passenger : entity.getEntity().getPassengers()) {
                    if (passenger instanceof Player player) {
                        passengers.add(player);
                    }
                }
            }
        }

        return passengers;
    }

    public String getLine(int oneBasedLine) {
        int index = oneBasedLine - 1;
        if (index < 0 || index > 3) {
            return "";
        }
        return signInfo.getLine(index);
    }

    @Nullable
    public Location getSignLocation() {
        Block block = getSignBlock();
        return block == null ? null : block.getLocation();
    }

    public String getSignType() {
        if (trainSign) return "train";
        if (cartSign) return "cart";
        return "unknown";
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}