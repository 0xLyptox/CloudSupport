package eu.thesystems.cloud.global.events.process.server;
/*
 * Created by derrop on 25.10.2019
 */

import eu.thesystems.cloud.CloudSystem;
import eu.thesystems.cloud.global.events.process.CloudProcessStopEvent;
import eu.thesystems.cloud.global.info.ServerInfo;

/**
 * This event is called when a server is stopped in the network.
 * <p>
 * This event is only called on clouds that distinguish between servers and proxies.
 *
 * @see CloudSystem#distinguishesProxiesAndServers()
 */
public class CloudServerStopEvent extends CloudProcessStopEvent {

    public CloudServerStopEvent(ServerInfo serverInfo) {
        super(serverInfo);
    }

    public ServerInfo getServerInfo() {
        return (ServerInfo) super.getProcessInfo();
    }

}
