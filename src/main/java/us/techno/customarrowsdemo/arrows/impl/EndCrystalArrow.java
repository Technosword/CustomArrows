package us.techno.customarrowsdemo.arrows.impl;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.RecipeChoice;
import us.techno.customarrowsdemo.arrows.CustomArrow;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EndCrystalArrow extends CustomArrow {

    public static HashMap<Character, RecipeChoice> recipeIngredientsMap = new HashMap<Character, RecipeChoice>(){{
            put('E', new RecipeChoice.MaterialChoice(Material.END_CRYSTAL));
            put('A', new RecipeChoice.ExactChoice(new FishArrow().getItem()));
        }};

    public EndCrystalArrow() {
        super("&6End Crystal Arrow", 400,  recipeIngredientsMap, true, "&3Flies through the air until it hits something", "&2Zero gravity!");
    }

    @Override
    public List<Sound> getSounds() {
        return Collections.singletonList(Sound.ENTITY_ENDER_DRAGON_GROWL);
    }
}