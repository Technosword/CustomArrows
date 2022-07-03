package us.techno.customarrowsdemo;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.techno.customarrowsdemo.arrows.CustomArrow;
import us.techno.customarrowsdemo.arrows.enums.ArrowType;
import us.techno.customarrowsdemo.arrows.impl.*;
import us.techno.customarrowsdemo.listeners.BowShootListener;
import us.techno.customarrowsdemo.listeners.EntityDamageByEntityListener;
import us.techno.customarrowsdemo.listeners.ProjectileHitListener;
import us.techno.customarrowsdemo.utils.RecipeHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class CustomArrowsDemo extends JavaPlugin {

    @Getter
    public static CustomArrowsDemo plugin;
    @Getter
    public HashMap<ArrowType, CustomArrow> arrowTypesMap = new HashMap<>();
    @Getter
    private HashSet<Arrow> arrowSet= new HashSet<>();
    @Getter
    public HashMap<String, List<ItemStack>> mobDropsMap = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        //so we know which custom model data corresponds to our arrow types
        arrowTypesMap.put(ArrowType.BUNDLE, new BundleArrow());
        arrowTypesMap.put(ArrowType.DIAMOND, new DiamondArrow());
        arrowTypesMap.put(ArrowType.END_CRYSTAL, new EndCrystalArrow());
        arrowTypesMap.put(ArrowType.FISH, new FishArrow());
        arrowTypesMap.put(ArrowType.INFINITY, new InfinityArrow());

        getServer().getPluginManager().registerEvents(new BowShootListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);


        setUpRecipes();
        setUpRunnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setUpRecipes() {
    arrowTypesMap.forEach((arrowType, customArrow) -> {
        RecipeHelper.addRecipe(this, customArrow);
    });
    }

    public void setUpRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
            arrowSet.forEach(arrow -> {
                for(int offset = 0; offset < 4; ++offset) {
                    Location location = arrow.getLocation().clone();
                    location.setX(location.getX() + arrow.getVelocity().getX() * (double) offset / 4.0D);
                    location.setY(location.getY() + arrow.getVelocity().getY() * (double) offset / 4.0D);
                    location.setZ(location.getZ() + arrow.getVelocity().getZ() * (double) offset / 4.0D);
                    arrow.getWorld().spawnParticle(Particle.LAVA, location, 1,
                            0, 0, 0,
                            0);
                }
            });
            }
        }.runTaskTimer(plugin, 0, 1);
    }

}
