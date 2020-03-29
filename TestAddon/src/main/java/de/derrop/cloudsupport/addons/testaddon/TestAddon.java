package de.derrop.cloudsupport.addons.testaddon;
/*
 * Created by derrop on 16.11.2019
 */

import eu.thesystems.cloud.addon.CloudAddon;

public class TestAddon extends CloudAddon {
    
    @Override
    public void onEnable() {
        System.out.println("enable");
        System.out.println(String.format("Processes: %d", this.getCloud().getProcesses()));
    }

    @Override
    public void onDisable() {
        System.out.println("disable");
    }
    
}
