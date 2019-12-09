package eu.thesystems.cloud.cloudnet3.wrapper.bungee;
/*
 * Created by derrop on 25.10.2019
 */

import de.dytanic.cloudnet.api.CloudAPI;
import eu.thesystems.cloud.cloudnet3.wrapper.CloudNet3Wrapper;
import eu.thesystems.cloud.detection.SupportedCloudSystem;

public class CloudNet3Bungee extends CloudNet3Wrapper {
    public CloudNet3Bungee() {
        super(SupportedCloudSystem.CLOUDNET_3_BUNGEE);
        new CloudAPI().bootstrap();
        System.out.println(CloudAPI.getInstance().getServers());
    }
}
