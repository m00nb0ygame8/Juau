package com.moonboygamer.juau;

import com.moonboygamer.juau.runner.LuauRunner;

public class Example {
    public static void main(String[] args) {
        LuauRunner runner = new LuauRunner();
        runner.run("""
                print('Hello from Luau!')
                """);
        runner.close();
    }
}
