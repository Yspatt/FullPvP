package com.yan.fullpvp.helpers;

import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Optional;

public abstract class CorePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    @Override
    public void onLoad() {
        load();
    }

    public abstract void enable();
    public abstract void disable();
    public abstract void load();

    public <T> T getService(Class<T> service) {
        Objects.requireNonNull(service, "clazz");

        return Optional
                .ofNullable(Bukkit.getServicesManager().getRegistration(service))
                .map(RegisteredServiceProvider::getProvider)
                .orElseThrow(() -> new IllegalStateException("No registration present for service '" + service.getName() + "'"));
    }

    public <T> T provideService(Class<T> clazz, T instance, ServicePriority priority) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(instance, "instance");
        Objects.requireNonNull(priority, "priority");

        Bukkit.getServicesManager().register(clazz, instance, this, priority);

        return instance;
    }

    public <T> T provideService(Class<T> clazz, T instance) {
        provideService(clazz, instance, ServicePriority.Normal);
        return instance;
    }

    public void listeners(Plugin plugin, Listener... listeners){
        for (Listener listener : listeners){
            plugin.getServer().getPluginManager().registerEvents(listener,plugin);
        }
    }
    public void commands(Plugin plugin, Command... commands){
        for (Command command : commands){
            ((CraftServer)plugin.getServer()).getCommandMap().register(plugin.getName().toLowerCase(),command);
        }
    }

    @SneakyThrows
    public HikariDataSource getDataSourceFromConfig() {
        final FileConfiguration fileConfiguration = getConfig();
        final HikariDataSource dataSource = new HikariDataSource();

        dataSource.setMaximumPoolSize(20);
        dataSource.setJdbcUrl("jdbc:mysql://" + fileConfiguration.getString("MySql.host", "localhost") + ":" + fileConfiguration.getString("MySql.port", "3306") + "/" + fileConfiguration.getString("MySql.database", "test"));
        dataSource.setUsername(fileConfiguration.getString("MySql.user", "root"));
        dataSource.setPassword(fileConfiguration.getString("MySql.password", ""));
        dataSource.addDataSourceProperty("autoReconnect", "true");

        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");

        return dataSource;
    }
}
