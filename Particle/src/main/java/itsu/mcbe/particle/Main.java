package itsu.mcbe.particle;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.level.particle.DustParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {

    private double time;

    public void onEnable() {
        getLogger().info("Enabled.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final double startX = event.getPlayer().getX();
        final double startZ = event.getPlayer().getZ();

        time = 0;
        DustParticle particle = new DustParticle(new Vector3(0, 0, 0),214, 117, 0);

        getServer().getScheduler().scheduleRepeatingTask(this, () -> {
            double r = time;//円の半径
            double y = -2 *  Math.pow(time - 0.5, 2) + 2.5;//y座標　y=ax^2+bの形にしてある。

            //円の半径の-r<d<rの範囲（dはプレイヤーのx座標と円上のx座標との差）の数値を出して、円を描く
            //z座標は円の方程式の基本形x^2+y^2=r^2（Minecraft上ではこの式のyはzとなる）を変形・整理してz=√(r^2-d^2)で求めた値をz座標としている。
            for (double d = -time; d < time; d = d + 0.02) {
                //円のz=√y^2の正のほう
                particle.setComponents(startX + d, event.getPlayer().getY() + y, startZ + Math.sqrt(r * r - d * d));
                event.getPlayer().getLevel().addParticle(particle);

                //円のz=√y^2の負のほう
                particle.setComponents(startX + d,event.getPlayer().getY() + y, startZ - Math.sqrt(r * r - d * d));
                event.getPlayer().getLevel().addParticle(particle);
            }

            time = time + 0.04;//
        }, 1);
    }
}
