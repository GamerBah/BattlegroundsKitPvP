package com.battlegroundspvp.worldpvp.utils;

/*
 * #%L
 * PlugMan
 * %%
 * Copyright (C) 2010 - 2014 PlugMan
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import com.battlegroundspvp.BattlegroundsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities for managing plugins.
 *
 * @author rylinaux
 */
public class PluginUtil {

    /**
     * Returns an array of Strings as a single String.
     *
     * @param args  the array
     * @param start the index to start at
     * @return the array as a String
     */
    private static String consolidateStrings(String[] args, int start) {
        String ret = args[start];
        if (args.length > (start + 1)) {
            for (int i = (start + 1); i < args.length; i++)
                ret = ret + " " + args[i];
        }
        return ret;
    }

    /**
     * Disable a plugin.
     *
     * @param plugin the plugin to disable
     */
    private static void disable(Plugin plugin) {
        if (plugin != null && plugin.isEnabled())
            Bukkit.getPluginManager().disablePlugin(plugin);
    }

    /**
     * Disable all plugins.
     */
    private static void disableAll() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            disable(plugin);
        }
    }

    /**
     * Enable a plugin.
     *
     * @param plugin the plugin to enable
     */
    private static void enable(Plugin plugin) {
        if (plugin != null && !plugin.isEnabled())
            Bukkit.getPluginManager().enablePlugin(plugin);
    }

    /**
     * Enable all plugins.
     */
    private static void enableAll() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
            enable(plugin);
    }

    /**
     * Returns the formatted name of the plugin.
     *
     * @param plugin          the plugin to format
     * @param includeVersions whether to include the version
     * @return the formatted name
     */
    private static String getFormattedName(Plugin plugin, boolean includeVersions) {
        ChatColor color = plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED;
        String pluginName = color + plugin.getName();
        if (includeVersions)
            pluginName += " (" + plugin.getDescription().getVersion() + ")";
        return pluginName;
    }

    /**
     * Returns a plugin from an array of Strings.
     *
     * @param args  the array
     * @param start the index to start at
     * @return the plugin
     */
    static Plugin getPluginByName(String[] args, int start) {
        return getPluginByName(consolidateStrings(args, start));
    }

    /**
     * Returns a plugin from a String.
     *
     * @param name the name of the plugin
     * @return the plugin
     */
    static Plugin getPluginByName(String name) {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (name.equalsIgnoreCase(plugin.getName()))
                return plugin;
        }
        return null;
    }

    /**
     * Loads and enables a plugin.
     *
     * @param plugin plugin to load
     */
    private static void load(Plugin plugin) throws InvalidPluginException, InvalidDescriptionException {
        load(plugin.getName());
    }

    /**
     * Loads and enables a plugin.
     *
     * @param name plugin's name
     */
    public static void load(String name) throws InvalidPluginException, InvalidDescriptionException {

        Plugin target;

        File pluginDir = new File("plugins");

        if (!pluginDir.isDirectory()) {
            BattlegroundsCore.getInstance().getLogger().severe("Plugins directory does not exist!");
            return;
        }

        File pluginFile = new File(pluginDir, name + ".jar");

        if (!pluginFile.isFile()) {
            for (File f : pluginDir.listFiles()) {
                if (f.getName().endsWith(".jar")) {
                    PluginDescriptionFile desc = BattlegroundsCore.getInstance().getPluginLoader().getPluginDescription(f);
                    if (desc.getName().equalsIgnoreCase(name)) {
                        pluginFile = f;
                        break;
                    }
                }
            }
        }

        target = Bukkit.getPluginManager().loadPlugin(pluginFile);


        target.onLoad();
        Bukkit.getPluginManager().enablePlugin(target);

        BattlegroundsCore.getInstance().getLogger().info("Successfully loaded plugin " + name + "!");
    }

    /**
     * Reload a plugin.
     *
     * @param plugin the plugin to reload
     */
    static void reload(Plugin plugin) {
        if (plugin != null) {
            unload(plugin);
            try {
                load(plugin);
            } catch (InvalidDescriptionException e) {
                plugin.getLogger().severe("Invalid Plugin Description!");
            } catch (InvalidPluginException e) {
                plugin.getLogger().severe("Invalid Plugin!");
            }
        }
    }

    /**
     * Reload all plugins.
     */
    static void reloadAll() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            reload(plugin);
        }
    }

    /**
     * Unload a plugin.
     *
     * @param plugin the plugin to unload
     */
    public static void unload(Plugin plugin) {

        String name = plugin.getName();

        PluginManager pluginManager = Bukkit.getPluginManager();

        SimpleCommandMap commandMap = null;

        List<Plugin> plugins = null;

        Map<String, Plugin> names = null;
        Map<String, Command> commands = null;
        Map<Event, SortedSet<RegisteredListener>> listeners = null;

        if (pluginManager != null) {

            try {

                Field pluginsField = Bukkit.getPluginManager().getClass().getDeclaredField("plugins");
                pluginsField.setAccessible(true);
                plugins = (List<Plugin>) pluginsField.get(pluginManager);

                Field lookupNamesField = Bukkit.getPluginManager().getClass().getDeclaredField("lookupNames");
                lookupNamesField.setAccessible(true);
                names = (Map<String, Plugin>) lookupNamesField.get(pluginManager);

                try {
                    Field listenersField = Bukkit.getPluginManager().getClass().getDeclaredField("listeners");
                    listenersField.setAccessible(true);
                    listeners = (Map<Event, SortedSet<RegisteredListener>>) listenersField.get(pluginManager);
                } catch (Exception ignored) {
                }

                Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);
                commandMap = (SimpleCommandMap) commandMapField.get(pluginManager);

                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                commands = (Map<String, Command>) knownCommandsField.get(commandMap);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                BattlegroundsCore.getInstance().getLogger().severe("Failed to unload plugin " + name + "!");
                return;
            }

            pluginManager.disablePlugin(plugin);
        }

        if (plugins != null)
            plugins.remove(plugin);

        if (names != null)
            names.remove(name);

        if (listeners != null) {
            for (SortedSet<RegisteredListener> set : listeners.values()) {
                for (Iterator<RegisteredListener> it = set.iterator(); it.hasNext(); ) {
                    RegisteredListener value = it.next();
                    if (value.getPlugin() == plugin) {
                        it.remove();
                    }
                }
            }
        }

        if (commandMap != null) {
            for (Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Command> entry = it.next();
                if (entry.getValue() instanceof PluginCommand) {
                    PluginCommand c = (PluginCommand) entry.getValue();
                    if (c.getPlugin() == plugin) {
                        c.unregister(commandMap);
                        it.remove();
                    }
                }
            }
        }

        // Attempt to close the classloader to unlock any handles on the plugin's
        // jar file.
        ClassLoader cl = plugin.getClass().getClassLoader();

        if (cl instanceof URLClassLoader) {
            try {
                ((URLClassLoader) cl).close();
            } catch (IOException ex) {
                Logger.getLogger(PluginUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Will not work on processes started with the -XX:+DisableExplicitGC flag,
        // but lets try it anyway. This tries to get around the issue where Windows
        // refuses to unlock jar files that were previously loaded into the JVM.
        System.gc();

        BattlegroundsCore.getInstance().getLogger().info("Unloaded plugin " + name + "!");
    }
}