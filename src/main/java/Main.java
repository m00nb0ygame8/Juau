import runner.LuauRunner;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LuauRunner runner = new LuauRunner();
        runner.setModule(runner.getState(), "abcdefu", """
                local test = {}
                
                function test.magic()
                    print('hello')
                end
                function test.abc()
                    print('efg')
                end
                
                return test
                """);
        runner.run("""
                print(type(require))
                """);

        runner.run("""
                print('hi from luau')
                """);


        runner.expose("javaAdd", q -> {
            double a = ((Number) q[0]).doubleValue();
            double b = ((Number) q[1]).doubleValue();
            return a + b;
        });

        runner.nativeExpose(runner.getState(), "javaAdd", List.of("number", "number"), "number");

        runner.run("""
        print("Calling Java from Lua:")
        print(javaAdd(3.5, 2.5))  -- should print 6.0
    """);

        runner.close();
    }

}
