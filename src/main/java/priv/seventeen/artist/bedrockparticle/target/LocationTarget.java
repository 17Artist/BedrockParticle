package priv.seventeen.artist.bedrockparticle.target;

/**
 * @program: bedrockparticle
 * @description: 地标跟随
 * @author: 17Artist
 * @create: 2025-05-12 23:55
 **/
public class LocationTarget implements ParticleTarget{

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public LocationTarget(double x, double y, double z, float yaw, float pitch){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public double getX(float partialTicks) {
        return x;
    }

    @Override
    public double getY(float partialTicks) {
        return y;
    }

    @Override
    public double getZ(float partialTicks) {
        return z;
    }

    @Override
    public float getYaw(float partialTicks) {
        return yaw;
    }

    @Override
    public float getPitch(float partialTicks) {
        return pitch;
    }
}
