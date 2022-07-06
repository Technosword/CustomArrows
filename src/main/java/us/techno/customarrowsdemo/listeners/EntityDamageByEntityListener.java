package us.techno.customarrowsdemo.listeners;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import us.techno.customarrowsdemo.arrows.enums.ArrowType;
import us.techno.customarrowsdemo.arrows.impl.DiamondArrow;
import us.techno.customarrowsdemo.arrows.impl.FishArrow;
import us.techno.customarrowsdemo.utils.ItemFactory;

import java.util.Objects;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow)) return;
        Arrow arrow = (Arrow) event.getDamager();

        if (!arrow.hasMetadata("arrowType")) return;
        String arrowMetaData = arrow.getMetadata("arrowType").get(0).asString();

        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity) || entity instanceof Player || entity instanceof Boss) return;

        LivingEntity livingEntity = (LivingEntity) entity;
        double entityHealth = livingEntity.getHealth() - event.getFinalDamage();

        //logic for diamond arrow
        if (entityHealth <= 0 && arrowMetaData.equalsIgnoreCase(ArrowType.DIAMOND.getName())) {
            if (!(arrow.getShooter() instanceof Player)) return;
            Player shooter = (Player) arrow.getShooter();
            shooter.playSound(entity, new DiamondArrow().getSounds().get(0), 3f, 1);
            if (livingEntity instanceof Creature) {
                if (livingEntity instanceof Monster) {
                    entity.getWorld().dropItem(entity.getLocation(), new ItemFactory(Material.DIAMOND)
                            .setAmount(15)
                            .addLoreLine("Your reward for killing " + livingEntity.getName() + "!")
                            .build());
                } else {
                    entity.getWorld().dropItem(entity.getLocation(), new ItemFactory(Material.GOLD_INGOT)
                            .setAmount(20)
                            .addLoreLines("Your reward for killing " + livingEntity.getName() + "!", "Hint: Kill aggressive mobs for higher reward!")
                            .build());
                }
            }
        }

        //logic for fish arrow
        if (arrowMetaData.equalsIgnoreCase(ArrowType.FISH.getName())) {
            double possibleHealth = Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
            if (!(arrow.getShooter() instanceof Player)) return;
            Player shooter = (Player) arrow.getShooter();
            shooter.playSound(entity, new FishArrow().getSounds().get(0), 3f, 1);

            if (possibleHealth <= 5) {
                spawnFish(livingEntity, EntityType.TROPICAL_FISH); //tropical = easy
                return;
            }
            if (possibleHealth <= 10) {
                spawnFish(livingEntity, EntityType.COD); //regular fish for normal health
                return;
            }
            if (possibleHealth <= 15) {
                spawnFish(livingEntity, EntityType.SALMON); //salmon are a little more tough in my mind, just like the health of these mobs
                return;
            }
            if (possibleHealth > 15) {
                spawnFish(livingEntity, EntityType.PUFFERFISH); //hard to kill mobs are the toughest "fish"
                return;
            }
            throw new IllegalArgumentException("Health of entity " + entity + " is impossible!"); //should never happen
        }
    }

    private void spawnFish(LivingEntity entity, EntityType fish) {
        Entity newEntity = entity.getWorld().spawnEntity(entity.getLocation(), fish);
        if (!(newEntity instanceof LivingEntity)) throw new IllegalArgumentException("Non-Living Entity spawned when " + fish + " should have spawned!");
        LivingEntity newLivingEntity = (LivingEntity) newEntity;
        Objects.requireNonNull(newLivingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(entity.getHealth());
        newLivingEntity.setHealth(entity.getHealth()); //can't make it too easy
        entity.remove();
    }
}
