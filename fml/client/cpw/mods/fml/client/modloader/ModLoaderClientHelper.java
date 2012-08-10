package cpw.mods.fml.client.modloader;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.src.BaseMod;
import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;

public class ModLoaderClientHelper
{

    /**
     * @param mod
     * @param inventoryRenderer
     * @return
     */
    public static int obtainBlockModelIdFor(BaseMod mod, boolean inventoryRenderer)
    {
        int renderId=RenderingRegistry.instance().getNextAvailableRenderId();
        ModLoaderBlockRendererHandler bri=new ModLoaderBlockRendererHandler(renderId, inventoryRenderer, mod);
        RenderingRegistry.instance().registerBlockHandler(bri);
        return renderId;
    }


    public static void handleFinishLoadingFor(ModLoaderModContainer mc, Minecraft game)
    {
        BaseMod mod = (BaseMod) mc.getMod();

        Map<Class<? extends Entity>, Render> renderers = Maps.newHashMap();

        mod.addRenderer(renderers);

        for (Entry<Class<? extends Entity>, Render> e : renderers.entrySet())
        {
            RenderingRegistry.instance().registerEntityRenderingHandler(e.getKey(), e.getValue());
        }

        mod.registerAnimation(game);
    }
}
