package mcserver.gui.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import mcserver.gui.core.MessageDialog;
import mcserver.gui.plugin.event.EventExecutor;

public class PluginLoader implements Loader{

    private final Map<String, Class> classes = new HashMap<>();
    private final Map<String, PluginClassLoader> classLoaders = new HashMap<>();

	public PluginLoader(){

	}

	@Override
	public Plugin loadPlugin(String filepath) throws Exception {
		return this.loadPlugin(new File(filepath));
	}

	@Override
	public Plugin loadPlugin(File file) throws Exception {
		Description description = this.getDescription(file);
		System.out.println(description.getName() + "プラグインのマニフェストファイルの読み込みが完了しました。");
        if (description != null) {
            File dataFolder = new File(file.getParentFile(), description.getName());
            if (dataFolder.exists() && !dataFolder.isDirectory()) {
                throw new IllegalStateException("Projected dataFolder '" + dataFolder.toString() + "' for " + description.getName() + " exists and is not a directory");
            }

            String className = description.getMain();
            PluginClassLoader classLoader = new PluginClassLoader(this, this.getClass().getClassLoader(), file);
            this.classLoaders.put(description.getName(), classLoader);
            MCServerGUIPlugin plugin;
            try {
                Class javaClass = classLoader.loadClass(className);

                try {
                    @SuppressWarnings("unchecked")
					Class<? extends MCServerGUIPlugin> pluginClass = javaClass.asSubclass(MCServerGUIPlugin.class);

                    plugin = pluginClass.newInstance();
                    this.initPlugin(plugin, description, file);
                    
                    System.out.println(description.getName() + "プラグインのクラスの読み込みが完了しました。");

                    return plugin;
                } catch (ClassCastException e) {
                	MessageDialog.freeDialog("メインクラス: `" + description.getMain() + "' はMCServerGUIPluginを継承していません。");
                	System.err.println("メインクラス: `" + description.getMain() + "' はMCServerGUIPluginを継承していません。");
                	e.printStackTrace();
                } catch (InstantiationException | IllegalAccessException e) {
                    MessageDialog.freeDialog("メインクラス: `" + description.getMain() + "' はMCServerGUIPluginを継承していません。");
                    System.err.println("メインクラス: `" + description.getMain() + "' はMCServerGUIPluginを継承していません。");
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
            	MessageDialog.freeDialog(description.getName() + "は見つかりませんでした。");
            	System.err.println(description.getName() + "は見つかりませんでした。");
            	e.printStackTrace();
            }
        }

        return null;
	}

    @Override
    public Description getDescription(File file) {
        try (JarFile jar = new JarFile(file)) {
            JarEntry entry = jar.getJarEntry("MCServerGUI.txt");
            if (entry == null) {
                entry = jar.getJarEntry("plugin.txt");
                if (entry == null) {
                    return null;
                }
            }
            try (InputStream stream = jar.getInputStream(entry)) {
                return new Description(Utils.readFile(stream));
            }
        } catch (IOException e) {
        	e.printStackTrace();
            return null;
        }
    }

    Class<?> getClassByName(final String name) {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (PluginClassLoader loader : this.classLoaders.values()) {

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException e) {
                    //ignore
                }
                if (cachedClass != null) {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    void setClass(final String name, final Class<?> clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    private void removeClass(String name) {
        Class<?> clazz = classes.remove(name);
    }

    private void initPlugin(MCServerGUIPlugin plugin, Description description,  File file) {
        plugin.init(this, description, file);
        plugin.onLoad();
        EventExecutor.addPlugin(plugin);
    }

}
