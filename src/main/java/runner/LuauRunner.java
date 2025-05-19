package runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class LuauRunner {
    static {
        System.load("C:\\Users\\zacha\\Documents\\Juau\\src\\main\\java\\runner\\luaujni.dll");
    }

    private long state;
    private static final HashMap<String, Function<Object[], Object>> handlers = new HashMap<>();

    public LuauRunner() {
        state = createState();
    }

    // Native bindings
    public native long createState();
    public native void runScript(long state, String code);
    public native void nativeExpose(long state, String name, List<String> argTypes, String returnType);
    public native void setModule(long state, String name, String source);
    public native void closeState(long state);
    public native Object callLua(long state, String name, Object[] args);

    // Internal access
    public long getState() {
        return state;
    }

    public void run(String code) {
        runScript(state, code);
    }

    public void runFile(String path) {
        try {
            String source = Files.readString(Path.of(path));
            run(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void expose(String name, Function<Object[], Object> handler) {
        handlers.put(name, handler);
    }

    public Object handleMethod(String name, Object[] args) {
        Function<Object[], Object> fn = handlers.get(name);
        if (fn == null) throw new RuntimeException("No handler for " + name);
        return fn.apply(args);
    }

    public void close() {
        closeState(state);
    }
}
