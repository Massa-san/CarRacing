package massajp.carracing.item;

import massajp.carracing.item.items.SpeedApple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ItemGetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof BlockCommandSender)) {
            sender.sendMessage("このコマンドはコマンドブロック専用です！");
            return true;
        }
        BlockCommandSender cb = (BlockCommandSender) sender;
        Player player = getNearbyPlayer(cb.getBlock().getLocation());
        Random random = new Random();
        int num = random.nextInt(40);
        if (num <= 4) {
            player.getInventory().addItem(new ItemStack(Material.NETHER_STAR));
        } else if (5 <= num && num <= 10) {
            player.getInventory().addItem(new ItemStack(Material.YELLOW_DYE));
        } else if (11 <= num && num <= 20) {
            player.getInventory().addItem(new ItemStack(Material.INK_SAC));
        } else if (21 <= num && num <= 30) {
            player.getInventory().addItem(new SpeedApple().Item());
        } else {        // num == 19
            player.sendMessage(player.getName() + " は アイテムガチャのハズレを引いた");
        }
        return true;
    }

    public static Player getNearbyPlayer(Location loc) {
        double nearest = 100;
        Player nearestPlayer = null;
        for (Entity entity : loc.getNearbyEntities(3, 3, 3)) {
            if (entity instanceof Player) {
                double distance = entity.getLocation().distance(loc);
                if (nearest > distance) {
                    nearest = distance;
                    nearestPlayer = (Player) entity;
                }
            }
        }
        return nearestPlayer;
    }


}
