package itsu.mcbe.economysystemjob.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ModalFormResponsePacket;
import cn.nukkit.plugin.RegisteredListener;
import itsu.mcbe.economysystem.api.EconomySystemAPI;
import itsu.mcbe.economysystemjob.api.EconomySystemJobAPI;

public class EventManager implements Listener {

    private EconomySystemJobAPI jobAPI;
    private EconomySystemAPI api;
    private MainAccessPoint owner;

    public EventManager(EconomySystemJobAPI jobAPI, EconomySystemAPI api, MainAccessPoint owner) {
        this.jobAPI = jobAPI;
        this.api = api;
        this.owner = owner;
    }
    
    @EventHandler
    public void onReceive(DataPacketReceiveEvent e) {
    	
        if(!(e.getPacket() instanceof ModalFormResponsePacket)) return;
        
        List<RegisteredListener> listeners = Arrays.asList(DataPacketReceiveEvent.getHandlers().getRegisteredListeners());
        List<RegisteredListener> formListeners = new ArrayList<>();
        
        listeners.stream()
    		.filter(listener -> listener.getListener().getClass().getSimpleName().contains("NukkitFormEventListener"))
    		.forEach(listener -> formListeners.add(listener));
        
        for(RegisteredListener l : formListeners) {
        	System.out.println(l.getListener().getClass().getName());
        }
    	
    	if(formListeners.size() > 1) {
    		for(int i = 0;i < formListeners.size() - 1;i++) {
    			DataPacketReceiveEvent.getHandlers().unregister(formListeners.get(i));
    		}
    	}
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(owner.getTreeCutters().contains(e.getPlayer().getName())) {
            if(owner.getTreeCutterData().containsKey(e.getBlock().getId() + "-" + e.getBlock().getDamage())) {
                api.increaseMoney(e.getPlayer().getName(), owner.getTreeCutterData().get((e.getBlock().getId() + "-" + e.getBlock().getDamage())));
            }

        } else if(owner.getMiners().contains(e.getPlayer().getName())) {
            if(owner.getMinerData().containsKey(e.getBlock().getId() + "-" + e.getBlock().getDamage())) {
                api.increaseMoney(e.getPlayer().getName(), owner.getMinerData().get((e.getBlock().getId() + "-" + e.getBlock().getDamage())));
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(owner.getTreePlanters().contains(e.getPlayer().getName())) {
            if(owner.getTreePlanterData().containsKey(e.getBlock().getId() + "-" + e.getBlock().getDamage())) {
                api.increaseMoney(e.getPlayer().getName(), owner.getTreePlanterData().get((e.getBlock().getId() + "-" + e.getBlock().getDamage())));
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(jobAPI.existsUser(e.getPlayer().getName())) {
            owner.setJob(e.getPlayer().getName(), jobAPI.getJob(e.getPlayer().getName()));
        }
    }

}
