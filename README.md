To run this project, you need to have Docker installed on your machine. Each microservice has its own Dockerfile that specifies 
how to build the Docker image for that service.

Run the following commands in your terminal to build and run the Docker containers for each microservice:
1. Navigate to the root directory of each microservice (e.g., APIGATEWAY, delivery-service, ServiceDiscovery, restaurant-service, order-service, customer-service).
2. Build the Docker image for each service using the following command:
3. 
   ```bash
   docker build -t <service-name> .
   ```
   Replace `<service-name>` with the name of the microservice (e.g., apigateway, delivery-service, etc.).
4. After building the images, you can run the containers using the following command:
   ```bash
   docker run -p <host-port>:<container-port> <service-name>
   ```
   Replace `<host-port>` with the port you want to use on your machine, `<container-port>` with the port exposed by the service (as specified in the Dockerfile), and `<service-name>` with the name of the microservice.
5. For example, to run the APIGATEWAY service, you would use:
   ```bash
   docker run -p 8080:8080 apigateway
6. Repeat the above steps for each microservice, making sure to use different host ports for each service to avoid conflicts.
   
To run the whole system together, you can use Docker Compose to define and run multi-container Docker applications. Create a `docker-compose.yml` file in the root directory of your project and define the services, networks, and volumes as needed. Then, run the following command to start all services:
```bash
docker-compose up
```

Thank you!