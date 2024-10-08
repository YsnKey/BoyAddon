package boy.addon.tronware;

import boy.addon.tronware.commands.MeteorFriends;
import com.google.gson.JsonObject;
import dev.boze.api.addon.Addon;
import dev.boze.api.addon.module.ToggleableModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TronWare extends Addon {

    public static final String ID = "boyaddon";
    public static final String NAME = "BoyAddon";
    public static final String DESCRIPTION = "TronWare";
    public static final String VERSION = "Non-Fuctinol";

    public static final TronWare INSTANCE = new TronWare();

    public static final Logger LOG = LogManager.getLogger();

    public TronWare() {
        super(ID, NAME, DESCRIPTION, VERSION);
    }

    @Override
    public boolean initialize() {
        LOG.info("Initializing " + name);
        MeteorFriends meteorfriends = new MeteorFriends();

        modules.add(meteorfriends);
        LOG.info("Successfully initialized " + name);

        return super.initialize();
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();

        JsonObject modulesObject = new JsonObject();

        for (ToggleableModule module : modules) {
            modulesObject.add(module.getName(), module.toJson());
        }

        object.add("modules", modulesObject);

        return object;
    }

    @Override
    public Addon fromJson(JsonObject jsonObject) {
        if (!jsonObject.has("modules")) {
            return this;
        }

        JsonObject modulesObject = jsonObject.getAsJsonObject("modules");

        for (ToggleableModule module : modules) {
            if (modulesObject.has(module.getName())) {
                module.fromJson(modulesObject.getAsJsonObject(module.getName()));
            }
        }

        return this;
    }
}
