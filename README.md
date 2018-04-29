#Deploy Local

When using minikube you deploy the service by running

```$xslt
kubectl create -f deployment.yaml 
```

This will create the pods, replica sets and services.

To expose the services nodePort run 
```$xslt
minikube service demo-microservices --url
```
It will give you an ind port to hit.


#Consul setup

Consul is used for service discovery and Externalized configurations I am using helm's consul setup to deploy to k8's
to deploy consul run
```$xslt
helm install stable/consul --name config-svc -f consul_values.yaml
```
This will read the configs from the consul_values.yaml and deploy to k8's enviroment