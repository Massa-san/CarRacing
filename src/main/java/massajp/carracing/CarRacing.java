package massajp.carracing;

import com.comphenix.protocol.ProtocolManager;
import massajp.carracing.car.Car;
import massajp.carracing.item.ItemEvent;
import massajp.carracing.item.ItemGetCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CarRacing extends JavaPlugin {

    static CarRacing plugin;
    ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginManager().registerEvents(new Car(), this);
        getServer().getPluginManager().registerEvents(new ItemEvent(), this);
        getCommand("item").setExecutor(new ItemGetCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CarRacing getInstance() {
        return plugin;
    }
}
