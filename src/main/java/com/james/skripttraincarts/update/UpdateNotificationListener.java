package com.james.skripttraincarts.update;

import com.james.skripttraincarts.SkriptTrainCarts;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateNotificationListener implements Listener {

    private final SkriptTrainCarts plugin;

    public UpdateNotificationListener(SkriptTrainCarts plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission(
                "skripttraincarts.update"
        )) {
            return;
        }

        UpdateChecker checker = plugin.getUpdateChecker();

        if (checker == null || !checker.isUpdateAvailable()) {
            return;
        }

        Component message = Component.text()
                .append(Component.text(
                        "[",
                        NamedTextColor.WHITE
                ))
                .append(Component.text(
                        "skript-",
                        NamedTextColor.AQUA
                ))
                .append(Component.text(
                        "traincarts",
                        NamedTextColor.GREEN
                ))
                .append(Component.text(
                        "] ",
                        NamedTextColor.WHITE
                ))
                .append(Component.text(
                        "An update is available! ",
                        NamedTextColor.YELLOW
                ))
                .append(Component.text(
                        "v" + checker.getLatestVersion(),
                        NamedTextColor.GREEN
                ))
                .append(Component.text(
                        " [Click to view]",
                        NamedTextColor.AQUA
                ).clickEvent(
                        ClickEvent.openUrl(
                                checker.getReleaseUrl()
                        )
                ))
                .build();

        event.getPlayer().sendMessage(message);
    }
}