# Japybara (J8) CLI

> [!Note]
> A load testing CLI similar to [`k6`](https://k6.io/) but written in Java. <br>
> (We like **Kapybara** and we are using Java, so we named it **Japybara**, and briefly **J8** CLI.)

* [Japybara (J8) CLI](#japybara-j8-cli)
    * [Project Structure](#project-structure)
        * [Latest Structure](#latest-structure)
        * [Original Structure](#original-structure)
    * [UML Diagram](#uml-diagram)

## Project Structure

### Latest Structure

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

### Original Structure

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

## UML Diagram


```mermaid
classDiagram
    %% interfaces
    class IBase {
        -Config config
        -ILogger logger
    }
    class ILogger {
        + void Log(String message)
    }
    class IExecutor {
        +void Init()
        +void Execute()
        +void Stop()
        +void WaitTermination()
    }
    class IRateLimit {
        +void GetToken()
        +boolean TryGetToken()
    }
    class IDispatcher {
        +void Execute()
        +void Stop()
    }
    class ICommandLine {
        +void Parse(String[] args)
    }
    class IResultCollector {
        +void AppendReport(TaskRecord record)
        +ArrayList<TaskRecord> GetRecords()
    }
    class IResultAggregator {
        +void RegisterResultCollector(IResultCollector collector)
        +void RegisterResultCollector(ArrayList<IResultCollector> newCollectors)
        +void ShowReport()
    }
    class Callable {
        +TaskRecord call()
    }
    %% model classes 
    class TaskRecord {
        -long Duration
        -long DataReceived
        -long Timestamp
        +TaskRecord(long duration, long dataReceived)
    }
    %% concrete classes
    class Logger {
        -Config config
        -Logger instance
        +Logger GetInstance()
        +void Log(String message)
        +void Error(String message)
        +void Warn(String message)
        +void Info(String message)
        +void Debug(String message)
        +void Trace(String message)
    }
    class Base {
        -Config config
        -Logger logger
    }
    class Config {
        +Config GetInstance()
        +long GetDuration()
    }
    class CommandLine {
        -Config config
        -Logger logger
        -IExecutor executor
        -IDispatcher dispatcher
        -IResultAggregator resultAggregator
        -IResultCollector resultCollector
        -IRateLimit rateLimit
        +void Parse(String[] args)
        +void Run()
    }
    class Dispatcher {
        -ITask task
        -IRateLimit rateLimit
        -Logger logger
        -Config config
        +void Execute()
        +void Stop()
    }
    class Executor {
        -IDispatcher dispatcher
        -ITask task
        -IRateLimit rateLimit
        -IResultCollector resultCollector
        -IResultAggregator resultAggregator
        +void Init()
        +void Execute()
        +void Stop()
        +void WaitTermination()
    }
    class ArrayStatistics~T~ {
        -ArrayList<T> data
        +ArrayStatistics()
        +void Add(T)
        +T GetMin()
        +T GetMax()
        +T GetMean()
        +T GetMedian()
        +T GetMode()
        +T GetStandardDeviation()
    }
    class ResultCollector {
        -ArrayList<TaskRecord> recordList
        +void AppendReport(TaskRecord)
        +ArrayList<TaskRecord> GetRecords()
    }
    class ResultAggregator {
        -ArrayList<IResultCollector> collectors
        -ArrayStatistics<Long> durationStatistics
        -ArrayStatistics<Long> dataReceivedStatistics
        +void RegisterResultCollector(IResultCollector)
        +void RegisterResultCollector(ArrayList<IResultCollector>)
        +void ShowReport()
        -void printStatistics(ArrayStatistics<Long>)
    }
    class RateLimit {
        -int maxTokens
        -int refillRate
        -int tokens
        -long lastRefillTimestamp
        +RateLimit(int, int)
    }
    class Task {
        -URI uri
        -HttpURLConnection connection
        -long responseTime
        -int dataSize
        -Config config
        +TaskRecord call()
        +Task()
        +void Init_uri(String uri_string)
        +TaskRecord Execute()
        +void Stop()
    }
    class TaskRecord {
        -long Duration
        -long DataReceived
        -long Timestamp
        +TaskRecord(long, long)
    }
    class Main {
        +void main(String[] args)
    }
    
    %% implements relationships
    Logger ..|> ILogger : implements
    Base ..|> IBase : implements
    CommandLine ..|> ICommandLine : implements
    Dispatcher ..|> IDispatcher : implements
    Executor ..|> IExecutor : implements
    RateLimit ..|> IRateLimit : implements
    ResultCollector ..|> IResultCollector : implements
    ResultAggregator ..|> IResultAggregator : implements
    Callable ..|> Task : implements

    %% associations
    Base -- Logger : uses
    Base -- Config : uses
    Config -- Logger : uses

    %% inheritance relationships
    Base <|-- Task : extends
    Base <|-- RateLimit : extends
    Base <|-- Executor : extends
    Base <|-- Dispatcher : extends

    %% composition relationships
    Dispatcher -- Executor : uses
    Dispatcher -- ResultAggregator : uses
    Executor -- Task : uses
    Executor -- RateLimit : uses
    Executor -- ResultCollector : uses
    ResultAggregator -- ResultCollector : registers
    ResultAggregator -- ArrayStatistics : uses
    ResultCollector -- TaskRecord : uses
    %% Main entry point
    Main -- CommandLine : uses
    Main -- Config : uses
    Main -- Logger : uses
    Main -- Dispatcher : uses

    
```