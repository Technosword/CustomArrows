package us.techno.customarrowsdemo.arrows;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import us.techno.customarrowsdemo.utils.ItemFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
public abstract class CustomArrow {
    private final String arrowName;
    private List<String> lore = new ArrayList<>();
    private final int customModelDataNum;
    private final String[] recipeShape;
    private final HashMap<Character, RecipeChoice> recipeIngredientsMap;
    private boolean isShapeless = false;

    public CustomArrow(String arrowName, int dataNum, String[] recipeShape, HashMap<Character, RecipeChoice> recipeIngredientsMap, String... lore) {
        this.arrowName = ChatColor.translateAlternateColorCodes('&', arrowName);
        this.customModelDataNum = dataNum;
        this.recipeShape = recipeShape;
        this.recipeIngredientsMap = recipeIngredientsMap;
        Arrays.asList(lore).forEach(loreLine -> this.lore.add(ChatColor.translateAlternateColorCodes('&', loreLine)));
    }

    public CustomArrow(String arrowName, int dataNum, HashMap<Character, RecipeChoice> recipeIngredientsMap, boolean isShapeless, String... lore) {
        this.arrowName = ChatColor.translateAlternateColorCodes('&', arrowName);
        this.customModelDataNum = dataNum;
        this.recipeShape = null;
        this.recipeIngredientsMap = recipeIngredientsMap;
        this.isShapeless = isShapeless;
        Arrays.asList(lore).forEach(loreLine -> this.lore.add(ChatColor.translateAlternateColorCodes('&', loreLine)));
    }


    public abstract List<Sound> getSounds();

    protected List<Sound> soundsToList(Sound... sounds) {
        return Arrays.asList(sounds);
    }

    public ItemStack getItem() {
        return new ItemFactory(Material.ARROW)
                .setDisplayName(getArrowName())
                .setLore(getLore())
                .addCustomModelData(getCustomModelDataNum())
                .build();
    }

}
