# JUAU
Juau is Java + Luau, you can run Luau code in Java using this.

## Getting Started
First you want to make the Luau Runner.
```java
LuauRunner runner = new LuauRunner();
```
Next, lets run the Hello World program in Luau.
```java
runner.run("""
  print('hi from luau')
  """);
```
Make sure at the end of your code you call
```java
runner.close();
```
## Other Features
Now, java can define modules for Luau to use. Here is an example.
```java
runner.setModule(runner.getState(), "magic", """
local test = {}
                
function test.magic()
  print('hello')
end
function test.abc()
  print('efg')
end
                
return test
""");

//Then you can run it by doing this
runner.run("""
  local magic = require("magic")
  magic.magic()
  magic.abc()
  """);
```
You can also call Java code from Luau by doing.
```java
runner.expose("javaAdd", args -. {
  double a = ((Number) args[0]).doubleValue();
  double b = ((Number) args[1]).doubleValue();
  return a + b;
});
//This will be moved all into runner.expose soon
runner.nativeExpose(runner.getState(), "javaAdd", List.of("number", "number"), "number");
```
You can call Lua code from Java by doing
```java
//All functions callable by Java must be global
runner.run("""
  function myFunc(a: number, b: string, c: boolean)
    print("a:", a, "b:", b, "c:", c)
    return a .. b
  end
""");
  Object result = runner.callLua(runner.getState(), "myFunc", new Object[] { 1.5, "hello", true });
  System.out.println("Result: " + result);
//Console:
//1) Result: 1.5hello
//2) a:  1.5 b:  hello c:  true
```
