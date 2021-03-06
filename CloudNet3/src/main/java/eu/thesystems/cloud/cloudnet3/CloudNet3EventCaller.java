package eu.thesystems.cloud.cloudnet3;
/*
 * Created by derrop on 26.10.2019
 */

import com.google.gson.JsonParser;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.channel.ChannelMessageReceiveEvent;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceInfoUpdateEvent;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceStartEvent;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceStopEvent;
import de.dytanic.cloudnet.ext.bridge.event.BridgeProxyPlayerDisconnectEvent;
import de.dytanic.cloudnet.ext.bridge.event.BridgeProxyPlayerLoginSuccessEvent;
import de.dytanic.cloudnet.ext.bridge.event.BridgeUpdateCloudOfflinePlayerEvent;
import de.dytanic.cloudnet.ext.bridge.event.BridgeUpdateCloudPlayerEvent;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import eu.thesystems.cloud.cloudnet3.util.CloudNet3Util;
import eu.thesystems.cloud.converter.CloudObjectConverter;
import eu.thesystems.cloud.event.EventManager;
import eu.thesystems.cloud.global.events.player.*;
import eu.thesystems.cloud.global.events.process.CloudProcessStartEvent;
import eu.thesystems.cloud.global.events.process.CloudProcessStopEvent;
import eu.thesystems.cloud.global.events.process.CloudProcessUpdateEvent;
import eu.thesystems.cloud.global.events.process.proxy.CloudProxyStartEvent;
import eu.thesystems.cloud.global.events.process.proxy.CloudProxyStopEvent;
import eu.thesystems.cloud.global.events.process.proxy.CloudProxyUpdateEvent;
import eu.thesystems.cloud.global.events.process.server.CloudServerStartEvent;
import eu.thesystems.cloud.global.events.process.server.CloudServerStopEvent;
import eu.thesystems.cloud.global.events.process.server.CloudServerUpdateEvent;
import eu.thesystems.cloud.global.info.ProcessInfo;
import eu.thesystems.cloud.global.info.ProxyInfo;
import eu.thesystems.cloud.global.info.ServerInfo;

public class CloudNet3EventCaller {

    private CloudNet3 cloudNet3;
    private EventManager eventManager;
    private IPlayerManager playerManager;

    public CloudNet3EventCaller(CloudNet3 cloudNet3, EventManager eventManager, IPlayerManager playerManager) {
        this.cloudNet3 = cloudNet3;
        this.eventManager = eventManager;
        this.playerManager = playerManager;
    }

    private CloudObjectConverter converter() {
        return this.cloudNet3.getConverter();
    }

    @EventListener
    public void handleServiceStart(CloudServiceStartEvent event) {
        ProcessInfo processInfo = CloudNet3Util.getProcessInfoFromService(this.converter(), event.getServiceInfo());
        this.eventManager.callEvent(new CloudProcessStartEvent(processInfo));
        if (processInfo instanceof ServerInfo) {
            this.eventManager.callEvent(new CloudServerStartEvent((ServerInfo) processInfo));
        } else if (processInfo instanceof ProxyInfo) {
            this.eventManager.callEvent(new CloudProxyStartEvent((ProxyInfo) processInfo));
        }
    }

    @EventListener
    public void handleServiceUpdate(CloudServiceInfoUpdateEvent event) {
        ProcessInfo processInfo = CloudNet3Util.getProcessInfoFromService(this.converter(), event.getServiceInfo());
        this.eventManager.callEvent(new CloudProcessUpdateEvent(processInfo));
        if (processInfo instanceof ServerInfo) {
            this.eventManager.callEvent(new CloudServerUpdateEvent((ServerInfo) processInfo));
        } else if (processInfo instanceof ProxyInfo) {
            this.eventManager.callEvent(new CloudProxyUpdateEvent((ProxyInfo) processInfo));
        }
    }

    @EventListener
    public void handleServiceStop(CloudServiceStopEvent event) {
        ProcessInfo processInfo = CloudNet3Util.getProcessInfoFromService(this.converter(), event.getServiceInfo());
        this.eventManager.callEvent(new CloudProcessStopEvent(processInfo));
        if (processInfo instanceof ServerInfo) {
            this.eventManager.callEvent(new CloudServerStopEvent((ServerInfo) processInfo));
        } else if (processInfo instanceof ProxyInfo) {
            this.eventManager.callEvent(new CloudProxyStopEvent((ProxyInfo) processInfo));
        }
    }

    @EventListener
    public void handleChannelMessage(ChannelMessageReceiveEvent event) {
        this.eventManager.callEvent(new eu.thesystems.cloud.global.events.channel.ChannelMessageReceiveEvent(event.getChannel(), event.getMessage(), JsonParser.parseString(event.getData().toJson()).getAsJsonObject()));
    }

    @EventListener
    public void handleOnlinePlayerUpdate(BridgeUpdateCloudPlayerEvent event) {
        this.eventManager.callEvent(new CloudPlayerUpdateEvent(this.converter().convertOnlinePlayer(event.getCloudPlayer())));
    }

    @EventListener
    public void handleOfflinePlayerUpdate(BridgeUpdateCloudOfflinePlayerEvent event) {
        this.eventManager.callEvent(new CloudOfflinePlayerUpdateEvent(this.converter().convertOfflinePlayer(event.getCloudOfflinePlayer())));
    }

    @EventListener
    public void handlePlayerLogin(BridgeProxyPlayerLoginSuccessEvent event) {
        this.eventManager.callEvent(new CloudPlayerLoginEvent(this.converter().convertOnlinePlayer(event.getNetworkConnectionInfo())));
        this.playerManager.getOnlineCountAsync().onComplete(onlineCount -> this.eventManager.callEvent(new CloudPlayerUpdateOnlineCountEvent(onlineCount)));
    }

    @EventListener
    public void handlePlayerLogout(BridgeProxyPlayerDisconnectEvent event) {
        this.eventManager.callEvent(new CloudPlayerLogoutEvent(this.converter().convertOnlinePlayer(event.getNetworkConnectionInfo())));
        this.playerManager.getOnlineCountAsync().onComplete(onlineCount -> this.eventManager.callEvent(new CloudPlayerUpdateOnlineCountEvent(onlineCount)));
    }

    @EventListener
    public void handlePlayerUpdateEvent(BridgeUpdateCloudPlayerEvent event) {
        this.eventManager.callEvent(new CloudPlayerUpdateEvent(this.converter().convertOnlinePlayer(event.getCloudPlayer())));
    }

    @EventListener
    public void handleOfflinePlayerUpdateEvent(BridgeUpdateCloudOfflinePlayerEvent event) {
        this.eventManager.callEvent(new CloudOfflinePlayerUpdateEvent(this.converter().convertOfflinePlayer(event.getCloudOfflinePlayer())));
    }

}
