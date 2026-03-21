package marv.allib.adapter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.reflect.StructureModifier;
import marv.allib.contracts.AlibAdapter;
import org.bukkit.Bukkit;

@AlibAdapter(targetPlugin = "ProtocolLib", version = "5.x")
public class ProtocolLibAdapter extends AbstractAdapter {

    private ProtocolManager protocolManager;

    @Override
    protected String getTargetPluginId() {
        return "ProtocolLib";
    }

    @Override
    protected String getSupportedVersionRange() {
        return ">=5.0";
    }

    @Override
    protected Class<?> getServiceInterface() {
        return IProtocolLibBridge.class;
    }

    @Override
    protected void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    protected void onUnload() {
        this.protocolManager = null;
    }

    @Override
    public String serviceId() {
        return "protocollib";
    }

    @Override
    public String version() {
        try {
            return protocolManager.getVersion().getVersion();
        } catch (Exception e) {
            return "unknown";
        }
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public interface IProtocolLibBridge extends marv.allib.contracts.IAlibService {
        ProtocolManager getProtocolManager();
        String getVersion();
    }
}
