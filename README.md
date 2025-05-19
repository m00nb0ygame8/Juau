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
