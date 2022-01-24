#!/bin/bash

# Start 2 network nodes, then allocate the resources using the first one as a gateway
java NetworkNode -ident 1 -tcpport 9000 A:1 B:1 &
sleep 1
java NetworkNode -ident 2 -tcpport 9001 -gateway localhost:9000 A:1 C:1 &
sleep 1
java NetworkClient -ident 1 -gateway localhost:9000 A:1 B:1
java NetworkClient -ident 2 -gateway localhost:9000 A:1 C:1
java NetworkClient -gateway localhost:9000 terminate
