package marv.allib.adapter;

import marv.allib.contracts.AlibAdapter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;

@AlibAdapter(targetPlugin = "LuckPerms", version = "5.x")
public class LuckPermsAdapter extends AbstractAdapter {

    private LuckPerms luckPerms;

    @Override
    protected String getTargetPluginId() {
        return "LuckPerms";
    }

    @Override
    protected String getSupportedVersionRange() {
        return ">=5.0";
    }

    @Override
    protected Class<?> getServiceInterface() {
        return ILuckPermsBridge.class;
    }

    @Override
    protected void onLoad() {
        this.luckPerms = LuckPermsProvider.get();
    }

    @Override
    protected void onUnload() {
        this.luckPerms = null;
    }

    @Override
    public String serviceId() {
        return "luckperms";
    }

    @Override
    public String version() {
        org.bukkit.plugin.Plugin lp = Bukkit.getPluginManager().getPlugin("LuckPerms");
        return lp != null ? lp.getDescription().getVersion() : "unknown";
    }

    public LuckPerms getApi() {
        return luckPerms;
    }

    public interface ILuckPermsBridge extends marv.allib.contracts.IAlibService {
        LuckPerms getApi();

        String getVersion();
    }
}
