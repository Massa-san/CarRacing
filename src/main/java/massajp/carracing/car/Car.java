package massajp.carracing.car;

import massajp.carracing.car.cars.ChickenCar;
import massajp.carracing.car.cars.TurtleCar;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftChicken;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPig;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftTurtle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Car implements Listener{

    @EventHandler
    public void ridecar(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().getType() != Material.STICK) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            ChickenCar chickenCar = new ChickenCar(event.getPlayer().getLocation());
            ((CraftWorld) event.getPlayer().getWorld()).getHandle().addEntity(chickenCar, CreatureSpawnEvent.SpawnReason.CUSTOM);
            LivingEntity entity =(LivingEntity) chickenCar.getBukkitEntity();
            entity.setAI(false);
            entity.setPassenger(event.getPlayer());
            ((CraftChicken) entity).getHandle().yaw = event.getPlayer().getLocation().getYaw();
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            TurtleCar turtleCar = new TurtleCar(event.getPlayer().getLocation());
            ((CraftWorld) event.getPlayer().getWorld()).getHandle().addEntity(turtleCar, CreatureSpawnEvent.SpawnReason.CUSTOM);
            LivingEntity entity =(LivingEntity) turtleCar.getBukkitEntity();
            entity.setAI(false);
            entity.setPassenger(event.getPlayer());
            ((CraftTurtle) entity).getHandle().yaw = event.getPlayer().getLocation().getYaw();
        }
    }

}
