package massajp.carracing.item.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface ItemTMP {

    public ItemStack Item();
    public ItemMeta getItemMeta();
    public boolean hasItem(PlayerInteractEvent event);
    public void ItemClick(PlayerInteractEvent event);

}
