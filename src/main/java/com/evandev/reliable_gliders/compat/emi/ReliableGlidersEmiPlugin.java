package com.evandev.reliable_gliders.compat.emi;

//? if <26.1 {
import com.evandev.reliable_gliders.Constants;
import com.evandev.reliable_gliders.registry.ModItems;
import com.evandev.reliable_gliders.registry.ModTags;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.recipe.EmiAnvilRecipe;

@EmiEntrypoint
public class ReliableGlidersEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        EmiStack tool = EmiStack.of(ModItems.GLIDER);
        EmiIngredient resource = EmiIngredient.of(ModTags.Items.GLIDER_REPAIR_ITEMS);

        registry.addRecipe(new EmiAnvilRecipe(tool, resource, Constants.id("/glider_anvil_repair")));
    }
}
//?}
