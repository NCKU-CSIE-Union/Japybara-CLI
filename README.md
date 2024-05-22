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

## Latest Structure

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


    subgraph Executors["`Executors depends on VUs`"]
        subgraph E1
            B1["Bucket"]
        end
        
        subgraph E2
            B2["Bucket"]
        end
        
        subgraph En
            Bn["Bucket"]
        end
        
        subgraph Executor
            B["`Token Bucket
            ( for Rate Limit )`"]
        end
    end 

%% E1 Tasks
    subgraph E1_Task1["Task1"]
        E1_Request1["Result1"]
        E1_WriteResult1["Write Result"]
        E1_Request1 ~~~ E1_WriteResult1
    end
    
    subgraph E1_Task..["Task.."]
        E1_Request2["Result2"]
        E1_WriteResult2["Write Result"]
        E1_Request2 ~~~ E1_WriteResult2
    end

    subgraph E1_Task..n["Task..n"]
        E1_Request3["Result3"]
        E1_WriteResult3["Write Result"]
        E1_Request3 ~~~ E1_WriteResult3
    end
    
%% Executor Tasks 
    subgraph Task1
        Request1["Result1"]
        WriteResult1["Write Result"]
        Request1 ~~~ WriteResult1
    end
    
    subgraph Task..
        Request2["Result2"]
        WriteResult2["Write Result"]
        Request2 ~~~ WriteResult2
    end

    subgraph Task..n
        Request3["Result3"]
        WriteResult3["Write Result"]
        Request3 ~~~ WriteResult3
    end
%% Connection
    CLI --> Config -->Dispatcher
    Dispatcher -. "`For graceful shutdown`" .-> OS
    Dispatcher --> E1 & E2 & En & Executor
%% Executor conn
    Executor --> Task1 & Task.. & Task..n
    WriteResult1 & WriteResult2 & WriteResult3--> ShareMem
%% E1 conn
    E1 --> E1_Task1 & E1_Task.. & E1_Task..n
    E1_WriteResult1 & E1_WriteResult2 & E1_WriteResult3--> ShareMem

%% E2 , En
    E2 & En --"`Manage
    their own tasks`"--> ShareMem
``` 

## Original Structure

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

    subgraph Executors["`Executors depends on VUs`"]
        E1
        E2
        E3
        En
        Executor
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
    Dispatcher --> E1 & E2 & E3 & En & Executor
    Executor --> Task1
    Executors --> Task2
    WriteResult1 --> ShareMem
    WriteResult2 --> ShareMem
```