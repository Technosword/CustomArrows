package us.techno.customarrowsdemo.arrows.impl;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.RecipeChoice;
import us.techno.customarrowsdemo.arrows.CustomArrow;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class InfinityArrow extends CustomArrow {

    public static HashMap<Character, RecipeChoice> recipeIngredientsMap = new HashMap<Character, RecipeChoice>(){{
            put('B', new RecipeChoice.ExactChoice(new BundleArrow().getItem()));
            put('D', new RecipeChoice.ExactChoice(new DiamondArrow().getItem()));
            put('E', new RecipeChoice.ExactChoice(new EndCrystalArrow().getItem()));
            put('F', new RecipeChoice.ExactChoice(new FishArrow().getItem()));
            put('d', new RecipeChoice.MaterialChoice(Material.DIAMOND_BLOCK));
        }};
    public static String[] recipeShape = new String[]{" B ", "DdE", " F "};

    public InfinityArrow() {
        super("&6Infinity Arrow", 600, recipeIngredientsMap, true, "&2Hint: Wait 2 seconds and it will appear");
    }

    @Override
    public List<Sound> getSounds() {
        return Collections.singletonList(Sound.ENTITY_ENDERMAN_TELEPORT);
    }
}
