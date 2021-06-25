# Tutorial

## Log in to OpenShift cluster

```
oc login ...
```

## Create a project
```
oc new-project test
```

## Get a secret to authenticato to registry.redhat.io

Download secret file from Registry Service Account Managemenet Application, then:

```
oc create secret -f XXX-secret.yaml

oc secrets link default NNN-XXX-pull-secret --for=pull
oc secrets link builder NNN-XXX-pull-secret --for=pull
```

## Update the templates of JWS 5.4

```
for resource in \
   jws54-openjdk11-tomcat9-ubi8-basic-s2i.json \
   jws54-openjdk11-tomcat9-ubi8-https-s2i.json \
   jws54-openjdk11-tomcat9-ubi8-image-stream.json
do
   oc replace -n openshift --force -f \
      https://raw.githubusercontent.com/jboss-container-images/jboss-webserver-5-openshift-image/jws54el8-v1.0/templates/${resource}
done

oc -n openshift import-image jboss-webserver54-openjdk11-tomcat9-openshift-ubi8:1.0
```

## Build the source code locally

```
mvn clean package
```

## Create new build configuration

```
oc new-build --binary=true --image-stream=jboss-webserver54-openjdk11-tomcat9-openshift-ubi8:latest --name=jws
```

## Start a new build

```
oc start-build jws --from-dir=$PWD/openshift --follow
```

## Create the relative app

```
oc new-app jws
```

## Expose the app to the world

```
oc expose svc/jws
```

## Test the app(s)

```
HOST=$(oc get routes --no-headers -o custom-columns='host:spec.host' jws)

curl -v http://$HOST/app1/
curl -v http://$HOST/app2/
```
