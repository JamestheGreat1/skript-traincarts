package com.james.skripttraincarts.traincarts;

import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent;
import com.bergerkiller.bukkit.tc.signactions.SignAction;
import com.bergerkiller.bukkit.tc.signactions.SignActionType;
import com.bergerkiller.bukkit.tc.utils.SignBuildOptions;
import com.james.skripttraincarts.bukkit.TrainSignTriggerEvent;
import org.bukkit.Bukkit;

public class SignActionSkript extends SignAction {

    @Override
    public boolean match(SignActionEvent info) {
        return info.isType("skript", "script");
    }

    @Override
    public void execute(SignActionEvent info) {
        if (!verify(info)) {
            return;
        }

        boolean trainSign = info.isTrainSign();
        boolean cartSign = info.isCartSign();

        if (!trainSign && !cartSign) {
            return;
        }

        boolean validAction =
                (trainSign && info.isAction(
                        SignActionType.GROUP_ENTER,
                        SignActionType.REDSTONE_ON,
                        SignActionType.REDSTONE_OFF
                ))
                        ||
                        (cartSign && info.isAction(
                                SignActionType.MEMBER_ENTER,
                                SignActionType.REDSTONE_ON,
                                SignActionType.REDSTONE_OFF
                        ));

        if (!validAction) {
            return;
        }

        if (trainSign && !info.hasGroup()) {
            return;
        }

        if (cartSign && !info.hasMember()) {
            return;
        }

        if (!info.isPowered()) {
            return;
        }

        String trigger = info.getLine(2);
        if (trigger == null || trigger.isBlank()) {
            trigger = "default";
        }

        Bukkit.getPluginManager().callEvent(
                new TrainSignTriggerEvent(info, trigger, trainSign, cartSign)
        );
    }

    @Override
    public boolean build(SignChangeActionEvent event) {
        return SignBuildOptions.create()
                .setPermission("train.build.skript")
                .setName("skript trigger")
                .setDescription("trigger custom Skript events")
                .handle(event);
    }
}