package us.techno.customarrowsdemo.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import us.techno.customarrowsdemo.CustomArrowsDemo;
import us.techno.customarrowsdemo.arrows.enums.ArrowType;
import us.techno.customarrowsdemo.arrows.impl.InfinityArrow;

import java.util.Arrays;
import java.util.Objects;

public class BowShootListener implements Listener {

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {

        if (!(event.getProjectile() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getProjectile();

        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        PlayerInventory inventory = player.getInventory();

        int arrowSlot = isShotFromMainHand(player) ? inventory.first(Material.ARROW) : inventory.getHeldItemSlot();
        ItemStack arrowItem = inventory.getItem(arrowSlot);

        if (arrowItem == null || !arrowItem.getType().equals(Material.ARROW)) {
            arrowSlot = inventory.first(Material.ARROW);
            arrowItem = inventory.getItem(arrowSlot);
        }

        assert arrowItem != null;
        if (!arrowItem.hasItemMeta()) return;
        if (!Objects.requireNonNull(arrowItem.getItemMeta()).hasDisplayName()) return;

        ItemStack finalArrowItem = arrowItem;
        int customModelData = finalArrowItem.getItemMeta().getCustomModelData();

        //Bundle arrow logic
        if (customModelData == ArrowType.BUNDLE.getModelData()) {
            Arrow arrow1 = player.getWorld().spawnArrow(player.getEyeLocation(), arrow.getVelocity().rotateAroundY(Math.toRadians(30)), .6F, 12);
            arrow1.setShooter(player);
            arrow1.setDamage(arrow.getDamage()/2);

            Arrow arrow2 = player.getWorld().spawnArrow(player.getEyeLocation(), arrow.getVelocity().rotateAroundY(Math.toRadians(-30)), .6F, 12);
            arrow2.setShooter(player);
            arrow2.setDamage(arrow.getDamage()/2);

            Arrow arrow3 = player.getWorld().spawnArrow(player.getEyeLocation(), arrow.getVelocity().rotateAroundY(Math.toRadians(60)), .6F, 12);
            arrow3.setShooter(player);
            arrow3.setDamage(arrow.getDamage()/2);

            Arrow arrow4 = player.getWorld().spawnArrow(player.getEyeLocation(), arrow.getVelocity().rotateAroundY(Math.toRadians(-60)), .6F, 12);
            arrow4.setShooter(player);
            arrow4.setDamage(arrow.getDamage()/2);
            addArrowTrail(arrow, arrow1, arrow2, arrow3, arrow4);
            return; //no metadata needed!
        }

        //Infinity arrow logic
        if (customModelData == ArrowType.INFINITY.getModelData()) {
            arrow.setShooter(null); //player can't pick up arrow now
            arrow.setDamage(arrow.getDamage() * 2);
            final int[] count = {0};
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (count[0] == 2) {
                        player.playSound(arrow.getLocation(), new InfinityArrow().getSounds().get(0), SoundCategory.NEUTRAL, 3f, 1);
                        finalArrowItem.setAmount(1);
                        player.getInventory().addItem(finalArrowItem);
                        player.updateInventory();
                        player.sendMessage(ChatColor.GREEN + "Your infinity arrow has been returned to your inventory!");
                        arrow.remove();
                        cancel();
                        return;
                    }
                    count[0]++;
                }
            }.runTaskTimer(CustomArrowsDemo.getPlugin(), 0, 20);
            addArrowTrail(arrow);
            return; //no metadata
        }

        //End Crystal arrow logic
        if (customModelData == ArrowType.END_CRYSTAL.getModelData()) {
            arrow.setGravity(false);
            arrow.setVelocity(arrow.getVelocity().multiply(5));
            addArrowTrail(arrow);
            return; //no metadata
        }

        CustomArrowsDemo.getPlugin().getArrowTypesMap().forEach((arrowType, customArrow) -> {
            if (customModelData == arrowType.getModelData()) {
                arrow.setMetadata("arrowType", new FixedMetadataValue(CustomArrowsDemo.getPlugin(), arrowType.getName()));
            }
        });
        addArrowTrail(arrow);
    }

    private boolean isShotFromMainHand(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        return mainHand.getType().equals(Material.BOW);
    }

    private void addArrowTrail(Arrow... arrows) {
        Arrays.stream(arrows).forEach(arrow -> CustomArrowsDemo.getPlugin().getArrowSet().add(arrow));
    }
}
