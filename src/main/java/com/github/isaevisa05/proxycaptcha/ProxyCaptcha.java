package com.github.isaevisa05.proxycaptcha;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(id = "proxycaptcha", name = "ProxyCaptcha", version = "0.0.1", url = "https://www.LaimCraft.Ru", authors = {"LaimCraft", "isaevisa05"})
public class ProxyCaptcha {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        TunnelRunner tunnelRunner = new TunnelRunner(logger);
    }
}
