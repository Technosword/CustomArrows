package us.techno.customarrowsdemo.utils;


import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import us.techno.customarrowsdemo.arrows.CustomArrow;

public class RecipeHelper {
    public static void addRecipe(JavaPlugin plugin, CustomArrow arrow) {
        String itemName = arrow.getArrowName().replace(' ', '_').replace("§", "").replaceAll("\\d", "");
        NamespacedKey key = new NamespacedKey(plugin, itemName);
        if (!arrow.isShapeless()) {
            ShapedRecipe recipe = new ShapedRecipe(key, arrow.getItem());

            recipe.shape(arrow.getRecipeShape());

            arrow.getRecipeIngredientsMap().forEach(recipe::setIngredient);

            Bukkit.addRecipe(recipe);
        } else {
            ShapelessRecipe recipe = new ShapelessRecipe(key, arrow.getItem());
            arrow.getRecipeIngredientsMap().forEach((character, recipeChoice) ->
                recipe.addIngredient(recipeChoice)
            );
            Bukkit.addRecipe(recipe);
        }
    }

}
