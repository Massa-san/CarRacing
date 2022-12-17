package massajp.carracing.item.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Objects;

public class SpeedApple implements ItemTMP{
    @Override
    public ItemStack Item() {
        ItemStack item = new ItemStack(Material.APPLE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("☆速度アップりんご☆");

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemMeta getItemMeta() {
        ItemStack item = new ItemStack(Material.APPLE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("☆速度アップりんご☆");
        return meta;
    }

    @Override
    public boolean hasItem(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.APPLE) return false;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName() == "☆速度アップりんご☆") return false;
        return true;
    }

    @Override
    public void ItemClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInHand().getAmount() == 1) {
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        } else {
            player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount()-1);
        }

        Vector vehicleSpeed = event.getPlayer().getVehicle().getVelocity();

        event.getPlayer().getVehicle().setVelocity(vehicleSpeed.multiply(2.3));
    }
}
