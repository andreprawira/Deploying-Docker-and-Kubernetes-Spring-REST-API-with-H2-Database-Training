# Spring MVC REST API using H2 
 This Spring Sync REST API uses H2 database, since it uses in-memory database, this API is the best if you want to dockerize it and deploy it in Kubernetes for tutorial. You will need the JAR file and it is located in root. Here are the commands to create an image and push it to Azure Container Registry and deploy it in Azure Kubernetes Service

Login:

- az acr login --name <registry-name>
- az aks get-credentials --resource-group <resource-group-name> --name <kubernetes-name>



Create deployment.yml and service.yml for Azure Kubernetes Service

```apiVersion: apps/v1
kind: Deployment
metadata:
  name: <MY-DEPLOYMENT>
spec:
  selector:
    matchLabels:
      app: <MY-POD-MUST-MATCH>
  template:
    metadata:
      labels:
        app: <MY-POD-MUST-MATCH>
    spec:
      containers:
      - name: <MY-CONTAINER>
        image: <LOGIN-SERVER/REPO-NAME:TAG>
        resources:
          limits:
            memory: "128Mi"
            cpu: "500m"
        ports:
        - containerPort: 80```

Service YAML template for people to access the pod in AKS

apiVersion: v1
kind: Service
metadata:
  name: <MY-SERVICE
spec:
  selector:
    app: <MY-POD-MUST-MATCH>
  ports:
  - port: 8080
    targetPort: <MUST MATCH containerPort> ex 80
  type: LoadBalancer



Creating image and pushing it to ACR and AKS: 

- docker build -t <image-name:tag> .
- docker tag <image-name:tag> <login-server/reponame:tag> 
- docker push yashmicroregistry.azurecr.io/andre-helloaspnetcore:v1
- kubectl apply -f .\<deployment-file.yml>
- kubectl get deployment
- kubectl get pods

You should see the external IP and the port after you execute "kubectl get pods" type the IP and the port and test it with Postman http://<external-ip>:<port>/api/employees/

If you want to test the app locally, run the app and test it using Postman http://localhost:8081/api/employees/

GET ALL method http://localhost:8081/api/employees/

GET by id method http://localhost:8081/api/employees/1

POST request body:

{
	"id" : "1",
	"name" : "nyadog",
	"salary" : "123"
	}
	
PUT method http://localhost:8081/api/employees/1 and add the body

{
	"id" : "1",
	"name" : "nyanya",
	"salary" : "321"
	}

DELETE method http://localhost:8081/api/employees/1
