# Japybara (J8) CLI

> [!Note]
> A load testing CLI similar to [`k6`](https://k6.io/) but written in Java. <br>
> (We like **Kapybara** and we are using Java, so we named it **Japybara**, and briefly **J8** CLI.)

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Structure

```mermaid
flowchart TD
%% Nodes
    CLI("CLI input")
    Config("J8 Config")
    Dispatcher("Dispatcher")
    Dispatcher
    %% Queue("Task Queue")
    
    subgraph OS["OS"]
        SIGTERM
        SIGKILL
        SIGTERM ~~~ SIGKILL
    end

    subgraph ShareMem["Share Memory"]
        LockArray["Lock Array"]
        Iteration
        Duration
        Data["Data received"]
    end
    
    subgraph RateLimit["`Rate Limit`"]
        B1
        B2
        B3
        B..
        Bucket
    end

    subgraph Threads["`Thread depends on VUs`"]
        T1
        T2
        T3
        Tn
        Thread
    end 
    
    subgraph Task1
        Request1
        WriteResult1["Write Result"]
        Request1 ~~~ WriteResult1
        Request1 -. "`try get token
        (distribute by hash`" .-> B1
    end
    
    subgraph Task2
        Request2
        WriteResult2["Write Result"]
        Request2 ~~~ WriteResult2
        Request2 -. "`try get token
        (distribute by hash`" .-> B3
    end
%% Connection
    CLI --> Config -->Dispatcher
    Dispatcher -. "`For graceful shutdown`" .-> OS
    Dispatcher --> T1 & T2 & T3 & Tn & Thread["(Thread)"]
    Thread --> Task1
    Tn --> Task2
    WriteResult1 --> ShareMem
    WriteResult2 --> ShareMem
```