package us.techno.customarrowsdemo.arrows.impl;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.RecipeChoice;
import us.techno.customarrowsdemo.arrows.CustomArrow;

import java.util.HashMap;
import java.util.List;

public class BundleArrow extends CustomArrow {

    public static HashMap<Character, RecipeChoice> recipeIngredientsMap = new HashMap<Character, RecipeChoice>() {{
        put('A', new RecipeChoice.MaterialChoice(Material.ARROW));
    }};
    public static String[] recipeShape = new String[]{"AAA", "AAA", "AAA"};
    public BundleArrow() {
        super("&6Bundle O Arrows",
                100, recipeShape,
                recipeIngredientsMap, "&3Shoots 5 arrows at once!",
                "&2Try to miss now...");
    }

    @Override
    public List<Sound> getSounds() {
        return null;
    }
}
