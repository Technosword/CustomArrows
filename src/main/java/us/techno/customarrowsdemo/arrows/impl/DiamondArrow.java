package us.techno.customarrowsdemo.arrows.impl;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.RecipeChoice;
import us.techno.customarrowsdemo.arrows.CustomArrow;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DiamondArrow extends CustomArrow {

    public static HashMap<Character, RecipeChoice> recipeIngredientsMap = new HashMap<Character, RecipeChoice>(){{
        put('D', new RecipeChoice.MaterialChoice(Material.DIAMOND));
        put('A', new RecipeChoice.ExactChoice(new BundleArrow().getItem()));
    }};
    public static String[] recipeShape = new String[]{"DDD", "DAD", "DDD"};

    public DiamondArrow() {
        super("&6Diamond Arrow", 200, recipeShape, recipeIngredientsMap,"&3Killed mobs drop cool loot!", "&2Hint: Higher health = higher reward!");
    }

    @Override
    public List<Sound> getSounds() {
        return Collections.singletonList(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }
}
