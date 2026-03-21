package marv.allib.contracts;

public enum ServerEnvironment {
    FOLIA,
    PAPER,
    SPIGOT,
    UNKNOWN;

    private static ServerEnvironment cached;

    public static ServerEnvironment detect() {
        if (cached != null) {
            return cached;
        }

        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.RegionScheduler");
            cached = FOLIA;
        } catch (ClassNotFoundException ignored) {
            try {
                Class.forName("com.destroystokyo.paper.ParticleBuilder");
                cached = PAPER;
            } catch (ClassNotFoundException ignored2) {
                cached = SPIGOT;
            }
        }

        return cached;
    }

    public static boolean isFolia() {
        return detect() == FOLIA;
    }

    public static boolean isPaper() {
        ServerEnvironment env = detect();
        return env == PAPER || env == FOLIA;
    }
}
