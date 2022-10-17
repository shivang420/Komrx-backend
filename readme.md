# TrackX

TRACKx is an artificial intelligence enabled contract management platform addressing financial leakages resulting from internal procurement processes.

## Deployment Process

1. Copy the jar file to AWS EC2.

```
scp  <Path-to-jar-file/>trackx-backend-0.0.1-SNAPSHOT.jar kmrzd@3.6.184.116:~/Deployment/Trackx/Backend/
```

2. Stop the current running java process from kmrzd.


3. Run the jar file.

```
nohup java -jar trackx-backend-0.0.1-SNAPSHOT.jar &
```
