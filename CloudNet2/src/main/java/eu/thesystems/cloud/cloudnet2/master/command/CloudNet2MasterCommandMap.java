package eu.thesystems.cloud.cloudnet2.master.command;
/*
 * Created by derrop on 16.11.2019
 */

import eu.thesystems.cloud.cloudnet2.master.CloudNet2Master;
import eu.thesystems.cloud.global.command.CloudCommand;
import eu.thesystems.cloud.global.command.CommandMap;

public class CloudNet2MasterCommandMap implements CommandMap {

    private CloudNet2Master cloudNet2Master;

    public CloudNet2MasterCommandMap(CloudNet2Master cloudNet2Master) {
        this.cloudNet2Master = cloudNet2Master;
    }

    @Override
    public void registerCommand(CloudCommand command) {
        this.cloudNet2Master.getCloudNet().getCommandManager().registerCommand(new CloudNet2MasterCommandWrapper(command));
    }
}
