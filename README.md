## What for?
There is a sample app to illustrate a bug in cf-jvmkill. In rare cases, the jvmkill would fail to kill the 
process due to additional memory allocation made by jvmkill agent.
Specifically, [`PoolStats`](https://github.com/cloudfoundry/jvmkill/blob/master/jvmkill/src/agentcontroller/poolstats.rs).

## What happen if I push the app?
Here is the log output

```
   2019-07-31T22:32:57.49-0600 [API/0] OUT Updated app with guid beae1595-87d0-4489-8cda-c5650f283555 ({"state"=>"STARTED"})
   2019-07-31T22:32:57.53-0600 [CELL/0] OUT Cell de6b4aa1-d4ee-4e1a-a8c6-a2df9ea95938 creating container for instance 6aad8a04-e178-479a-41fe-06c0
   2019-07-31T22:32:58.09-0600 [CELL/0] OUT Cell de6b4aa1-d4ee-4e1a-a8c6-a2df9ea95938 successfully created container for instance 6aad8a04-e178-479a-41fe-06c0
   2019-07-31T22:32:58.18-0600 [CELL/0] OUT Downloading droplet...
   2019-07-31T22:33:01.22-0600 [CELL/0] OUT Downloaded droplet (62.9M)
   2019-07-31T22:33:01.33-0600 [CELL/0] OUT Starting health monitoring of container
   2019-07-31T22:33:02.12-0600 [APP/PROC/WEB/0] OUT JVM Memory Configuration: -Xmx439914K -Xss1M -XX:ReservedCodeCacheSize=240M -XX:MaxDirectMemorySize=10M -XX:MaxMetaspaceSize=96661K
   2019-07-31T22:33:05.62-0600 [APP/PROC/WEB/0] OUT   .   ____          _            __ _ _
   2019-07-31T22:33:05.63-0600 [APP/PROC/WEB/0] OUT  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
   2019-07-31T22:33:05.63-0600 [APP/PROC/WEB/0] OUT ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
   2019-07-31T22:33:05.63-0600 [APP/PROC/WEB/0] OUT  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
   2019-07-31T22:33:05.63-0600 [APP/PROC/WEB/0] OUT   '  |____| .__|_| |_|_| |_\__, | / / / /
   2019-07-31T22:33:05.63-0600 [APP/PROC/WEB/0] OUT  =========|_|==============|___/=/_/_/_/
   2019-07-31T22:33:05.63-0600 [APP/PROC/WEB/0] OUT  :: Spring Boot ::        (v2.1.6.RELEASE)
   2019-07-31T22:33:06.32-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:06.313  INFO 13 --- [           main] pertySourceApplicationContextInitializer : 'cloud' property source added
   2019-07-31T22:33:06.32-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:06.327  INFO 13 --- [           main] nfigurationApplicationContextInitializer : Reconfiguration enabled
   2019-07-31T22:33:06.35-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:06.354  INFO 13 --- [           main] c.f.cfjvmkill.CfjvmkillApplication       : Starting CfjvmkillApplication on 6aad8a04-e178-479a-41fe-06c0 with PID 13 (/home/vcap/app/BOOT-INF/classes started by vcap in /home/vcap/app)
   2019-07-31T22:33:06.35-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:06.355  INFO 13 --- [           main] c.f.cfjvmkill.CfjvmkillApplication       : The following profiles are active: cloud
   2019-07-31T22:33:08.87-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:08.874  INFO 13 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
   2019-07-31T22:33:08.96-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:08.963  INFO 13 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
   2019-07-31T22:33:08.96-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:08.964  INFO 13 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.21]
   2019-07-31T22:33:09.22-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:09.222  INFO 13 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
   2019-07-31T22:33:09.22-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:09.223  INFO 13 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2740 ms
   2019-07-31T22:33:09.87-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:09.872  INFO 13 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
   2019-07-31T22:33:10.32-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:10.324  INFO 13 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
   2019-07-31T22:33:10.34-0600 [APP/PROC/WEB/0] OUT 2019-08-01 04:33:10.339  INFO 13 --- [           main] c.f.cfjvmkill.CfjvmkillApplication       : Started CfjvmkillApplication in 6.309 seconds (JVM running for 8.211)
   2019-07-31T22:33:10.35-0600 [APP/PROC/WEB/0] OUT starting to allocate memory after 5s
   2019-07-31T22:33:12.22-0600 [CELL/0] OUT Container became healthy
   2019-07-31T22:33:19.64-0600 [APP/PROC/WEB/0] ERR Resource exhaustion event: the JVM was unable to allocate memory from the heap.
   2019-07-31T22:33:19.64-0600 [APP/PROC/WEB/0] ERR ResourceExhausted! (1/0)
   2019-07-31T22:33:23.00-0600 [APP/PROC/WEB/0] OUT | Instance Count | Total Bytes | Class Name                                                                                                       |
   2019-07-31T22:33:23.00-0600 [APP/PROC/WEB/0] OUT | 1496533        | 419081264   | [B                                                                                                               |
   2019-07-31T22:33:23.01-0600 [APP/PROC/WEB/0] OUT | 3116           | 7474488     | [Ljava/lang/Object;                                                                                              |
   2019-07-31T22:33:23.01-0600 [APP/PROC/WEB/0] OUT | 29184          | 2795688     | [C                                                                                                               |
   ......
   2019-07-31T22:33:23.12-0600 [APP/PROC/WEB/0] OUT | 40             | 4480        | Lorg/springframework/beans/factory/annotation/AnnotatedGenericBeanDefinition;                                    |
   2019-07-31T22:33:23.12-0600 [APP/PROC/WEB/0] OUT | 183            | 4392        | Lorg/hibernate/validator/internal/engine/constraintvalidation/ClassBasedValidatorDescriptor;                     |
   2019-07-31T22:33:23.13-0600 [APP/PROC/WEB/0] OUT | 176            | 4224        | Lorg/springframework/boot/web/server/MimeMappings$Mapping;                                                       |
   2019-07-31T22:33:23.13-0600 [APP/PROC/WEB/0] OUT | 261            | 4176        | Lorg/springframework/core/convert/support/GenericConversionService$ConvertersForPair;                            |
   2019-07-31T22:33:23.13-0600 [APP/PROC/WEB/0] OUT | 130            | 4160        | Lcom/sun/jmx/mbeanserver/ConvertingMethod;                                                                       |
   2019-07-31T22:33:23.65-0600 [APP/PROC/WEB/0] OUT Memory usage:
```

And it will just halt forever. The state from `cf app <cfjvmkill>` still shows `running`.
