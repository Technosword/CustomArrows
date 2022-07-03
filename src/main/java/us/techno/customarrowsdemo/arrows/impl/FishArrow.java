package us.techno.customarrowsdemo.arrows.impl;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.RecipeChoice;
import us.techno.customarrowsdemo.arrows.CustomArrow;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FishArrow extends CustomArrow {

    public static HashMap<Character, RecipeChoice> recipeIngredientsMap = new HashMap<Character, RecipeChoice>(){{
            put('A', new RecipeChoice.MaterialChoice(Material.ARROW));
            put('C', new RecipeChoice.MaterialChoice(Material.COD));
            put('S', new RecipeChoice.MaterialChoice(Material.SALMON));
        }};
    public static String[] recipeShape = new String[]{"SCS", "CAC", "SCS"};
    public FishArrow() {
        super("&6Fish Arrow", 500, recipeShape, recipeIngredientsMap,"&3Turn your mobs into fish!", "&2Hint: Won't work on players...");
    }

    @Override
    public List<Sound> getSounds() {
        return Collections.singletonList(Sound.ENTITY_FISH_SWIM);
    }
}