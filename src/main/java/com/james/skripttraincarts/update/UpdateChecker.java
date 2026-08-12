package com.james.skripttraincarts.update;

import com.james.skripttraincarts.SkriptTrainCarts;
import org.bukkit.Bukkit;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateChecker {

    private static final String RELEASE_API =
            "https://api.github.com/repos/JamestheGreat1/skript-traincarts/releases/latest";

    private static final String RELEASE_URL =
            "https://github.com/JamestheGreat1/skript-traincarts/releases/latest";

    private final SkriptTrainCarts plugin;

    private volatile boolean updateAvailable = false;
    private volatile String latestVersion = null;

    public UpdateChecker(SkriptTrainCarts plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(RELEASE_API))
                        .header("Accept", "application/vnd.github+json")
                        .header("User-Agent", "skript-traincarts")
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    plugin.getLogger().warning(
                            "Could not check for updates. GitHub returned HTTP "
                                    + response.statusCode()
                    );
                    return;
                }

                Matcher matcher = Pattern
                        .compile("\"tag_name\"\\s*:\\s*\"([^\"]+)\"")
                        .matcher(response.body());

                if (!matcher.find()) {
                    plugin.getLogger().warning(
                            "Could not determine latest release version."
                    );
                    return;
                }

                latestVersion = normalizeVersion(matcher.group(1));

                String currentVersion =
                        normalizeVersion(plugin.getPluginMeta().getVersion());

                updateAvailable =
                        isNewerVersion(latestVersion, currentVersion);

                if (updateAvailable) {
                    plugin.getLogger().info(
                            "A new version is available: "
                                    + latestVersion
                                    + " (current: "
                                    + currentVersion
                                    + ")"
                    );
                } else {
                    plugin.getLogger().info(
                            "skript-traincarts is up to date."
                    );
                }

            } catch (Exception exception) {
                plugin.getLogger().warning(
                        "Could not check for updates: "
                                + exception.getMessage()
                );
            }
        });
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getReleaseUrl() {
        return RELEASE_URL;
    }

    private String normalizeVersion(String version) {
        String normalized = version.trim();

        if (normalized.startsWith("v")) {
            normalized = normalized.substring(1);
        }

        normalized = normalized.replace("-release", "");

        return normalized;
    }

    private boolean isNewerVersion(String latest, String current) {
        String[] latestParts = latest.split("\\.");
        String[] currentParts = current.split("\\.");

        int length = Math.max(
                latestParts.length,
                currentParts.length
        );

        for (int i = 0; i < length; i++) {
            int latestPart =
                    i < latestParts.length
                            ? parseVersionPart(latestParts[i])
                            : 0;

            int currentPart =
                    i < currentParts.length
                            ? parseVersionPart(currentParts[i])
                            : 0;

            if (latestPart > currentPart) {
                return true;
            }

            if (latestPart < currentPart) {
                return false;
            }
        }

        return false;
    }

    private int parseVersionPart(String part) {
        try {
            return Integer.parseInt(
                    part.replaceAll("[^0-9]", "")
            );
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}