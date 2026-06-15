package utility;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ScriptTracker {
    private static final Set<Path> executedScripts = ConcurrentHashMap.newKeySet();

    public static boolean isExecuting(Path path) {
        return executedScripts.contains(path);
    }

    public static void markExecuting(Path path) {
        executedScripts.add(path);
    }

    public static void markFinished(Path path) {
        executedScripts.remove(path);
    }

    public static void clearAll() {
        executedScripts.clear();
    }

    private ScriptTracker() {}
}
