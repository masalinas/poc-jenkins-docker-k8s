# Jenkins integration
In this repository we try to explain how we can deploy jenkins as docker container integrated with:

- Host Docker Engine
- Host Minikube Cluster

Using the jenkins vanilla `jenkins/jenkins` Docker Image. This deployment has the limitation that we must use the standard jenkins command `sh` to interact with Docker, Kubernetes or Helm to deploy our services in kubernetes.


## Deployment
With this command we will installed the last jenkins integrate with:

- Host Docker Engine
- Host Docker CLI with the configuration to connect to docker hub
- Host Minikube Cluster
- Host kubectl CLI with the configuration to use minikube context
- Host helm CLI using the kubectl configuration

We must use port 8088 because 8080 is used by minikube.

```
$ docker run -d \
  --name jenkins \
  -p 8088:8080 -p 50000:50000 \
  -v jenkins:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /usr/bin/docker:/usr/bin/docker \
  -v $HOME/.docker:/var/jenkins_home/.docker \
  --group-add $(getent group docker | cut -d: -f3) \
  -v /usr/local/bin/kubectl:/usr/local/bin/kubectl \
  -v $HOME/.kube:/var/jenkins_home/.kube \
  -v $HOME/.minikube:/home/kubernetes/.minikube \
  -v /usr/local/bin/helm:/usr/local/bin/helm \
  --network minikube \
  jenkins/jenkins
```

Where: 

-d: detach mode
--name jenkins: friendly container name
-p 8088:8080 -p 50000:50000: ports published
-v /var/run/docker.sock:/var/run/docker.sock: share the host docker engine
-v /usr/bin/docker:/usr/bin/docker: share the host docker CLI
-v $HOME/.docker:/var/jenkins_home/.docker: externalize jenkins state in a volume
--group-add $(getent group docker | cut -d: -f3): add jenkins user to the host docker group
-v /usr/local/bin/kubectl:/usr/local/bin/kubectl: share host kubectl CLI
-v $HOME/.kube:/var/jenkins_home/.kube: share host kubect configuration
-v $HOME/.minikube:/home/kubernetes/.minikube: share host kubernetes configuration (minikube) and credentials
-v /usr/local/bin/helm:/usr/local/bin/helm: share host helm CLI
--network minikube: deploy jenkins in minikube network
jenkins/jenkins: use standard jenkins docker image

