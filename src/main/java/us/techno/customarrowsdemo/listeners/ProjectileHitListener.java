package us.techno.customarrowsdemo.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.techno.customarrowsdemo.CustomArrowsDemo;

public class ProjectileHitListener implements Listener {
    @EventHandler
    public void hit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            CustomArrowsDemo.getPlugin().getArrowSet().remove((Arrow) event.getEntity());
        }
    }
}