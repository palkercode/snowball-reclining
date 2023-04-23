package me.palkercode.snowballreclining;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SnowballReclining extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().addDefault("knockbackForce", 1);
        getConfig().addDefault("useOnPlayersOnly", true);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onSnowballHit(ProjectileHitEvent event) {
        Entity snowball = event.getEntity();
        Entity hitEntity = event.getHitEntity();
        FileConfiguration config = this.getConfig();

        if (snowball instanceof Snowball && hitEntity != null) {
            if (!config.getBoolean("useOnPlayersOnly")) {
                giveKnockback(snowball, hitEntity, config);
            }

            else {
                if (hitEntity instanceof Player) {
                    giveKnockback(snowball, hitEntity, config);
                }
            }
        }
    }

    private static void giveKnockback(Entity snowball, Entity hitEntity, FileConfiguration config) {
        hitEntity.setVelocity(
                snowball.getVelocity().add(
                        hitEntity.getLocation().toVector().subtract(snowball.getLocation().toVector()).normalize()
                                .multiply(config.getDouble("knockbackForce"))
                )
        );
    }
}
