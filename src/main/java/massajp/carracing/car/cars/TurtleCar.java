package massajp.carracing.car.cars;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TurtleCar extends EntityTurtle {

    public TurtleCar(Location loc) {
        super(EntityTypes.TURTLE, ((CraftWorld) loc.getWorld()).getHandle());

        this.setPosition(loc.getX(), loc.getY(), loc.getZ());

        this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 1.0F));
    }

    @Override
    public void e(Vec3D vec3d) {
        if (!this.isVehicle()) {
            super.e(vec3d);
            return;
        }

        EntityLiving passenger = (EntityLiving) this.getPassengers().get(0);

        if (this.a(TagsFluid.WATER)) {
            this.setMot(this.getMot().add(0, 0.4, 0));
        }

        // apply pitch & yaw
        this.lastYaw = (this.yaw = passenger.yaw);
        this.pitch = passenger.pitch * 0.5F;
        setYawPitch(this.yaw, this.pitch);
        this.aK = (this.aI = this.yaw);

        // get motion from passenger (player)
        double motionForward = passenger.bb;

        // backwards is slower
        if (motionForward <= 0.0F) {
            motionForward *= 0.25F;
        }

        float speed = 0.22222F * (1F + (1.5f));
        ride(motionForward, vec3d.y, speed); // apply motion

        // throw player move event
        double delta = Math.pow(this.locX() - this.lastX, 2.0D) + Math.pow(this.locY() - this.lastY, 2.0D)
                + Math.pow(this.locZ() - this.lastZ, 2.0D);
        float deltaAngle = Math.abs(this.yaw - lastYaw) + Math.abs(this.pitch - lastPitch);
        if (delta > 0.00390625D || deltaAngle > 10.0F) {
            Location to = getBukkitEntity().getLocation();
            Location from = new Location(world.getWorld(), this.lastX, this.lastY, this.lastZ, this.lastYaw,
                    this.lastPitch);
            if (from.getX() != Double.MAX_VALUE) {
                Location oldTo = to.clone();
                PlayerMoveEvent event = new PlayerMoveEvent((Player) passenger.getBukkitEntity(), from, to);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    passenger.getBukkitEntity().teleport(from);
                    return;
                }
                if ((!oldTo.equals(event.getTo())) && (!event.isCancelled())) {
                    passenger.getBukkitEntity().teleport(event.getTo(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    return;
                }
            }
        }
        super.a(vec3d);
    }



    private void ride(double motionForward, double motionUpwards, float speedModifier) {
        double locY;
        float f2;
        float speed;
        float swimSpeed;
        float friction = 0.91F;

        speed = speedModifier * (0.16277136F / (friction * friction * friction));

        this.a(speed, new Vec3D(0, motionUpwards, motionForward));

        double motX = this.getMot().x;
        double motY = this.getMot().y;
        double motZ = this.getMot().z;

        if (this.isClimbing()) {
            swimSpeed = 0.15F;
            motX = MathHelper.a(motX, -swimSpeed, swimSpeed);
            motZ = MathHelper.a(motZ, -swimSpeed, swimSpeed);
            this.fallDistance = 0.0F;
            if (motY < -0.15D) {
                motY = -0.15D;
            }
        }

        Vec3D mot = new Vec3D(motX, motY, motZ);

        this.move(EnumMoveType.SELF, mot);
        if (this.positionChanged && this.isClimbing()) {
            motY = 0.2D;
        }

        motY -= 0.08D;

        motY *= 0.9800000190734863D;
        motX *= friction;
        motZ *= friction;

        this.setMot(motX, motY, motZ);

        this.aC = this.aD;
        locY = this.locX() - this.lastX;
        double d1 = this.locZ() - this.lastZ;
        f2 = MathHelper.sqrt(locY * locY + d1 * d1) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.aD += (f2 - this.aD) * 0.4F;
        this.aE += this.aD;
    }
}
