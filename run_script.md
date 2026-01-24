# How to Use the RestoHub Run Script

## Overview

The `run.sh` script is a convenient management tool for your RestoHub microservices project. It simplifies Docker operations with easy-to-use commands and provides helpful status information.

## Quick Start

```bash
# Make the script executable (first time only)
chmod +x run.sh

# Start all services
./run.sh start

# Check if everything is running
./run.sh status
```

## All Available Commands

### 🚀 **Start Services**
```bash
./run.sh start
```
- Starts all RestoHub microservices and databases
- Runs containers in the background (detached mode)
- Automatically shows status after starting

### 🛑 **Stop Services**
```bash
./run.sh stop
```
- Stops all running services
- Removes containers but keeps data (volumes are preserved)

### 🔄 **Restart Services**
```bash
./run.sh restart
```
- Restarts all services without rebuilding
- Useful when you need a quick refresh
- Shows status after restarting

### 📊 **Check Status**
```bash
./run.sh status
```
Shows:
- Which containers are running
- Container health status
- Port mappings
- Uptime information

### 📝 **View Logs**
```bash
# View logs for all services
./run.sh logs

# View logs for a specific service
./run.sh logs menu-service
./run.sh logs order-service
./run.sh logs kitchen-service
./run.sh logs api-gateway
./run.sh logs postgres-menu
./run.sh logs rabbitmq
```
- Press `Ctrl+C` to exit log viewing
- Logs are shown in real-time (follow mode)

### 🏗️ **Rebuild Services**
```bash
./run.sh rebuild
```
- Rebuilds all Docker images from scratch
- Use this when you've made code changes
- Uses `--no-cache` to ensure fresh builds
- **Note:** After rebuilding, run `./run.sh start` to start the services

### 🏥 **Health Check**
```bash
./run.sh health
```
Checks the health of:
- **Databases**: postgres-menu, postgres-order, postgres-kitchen
- **RabbitMQ**: Message broker
- **Microservices**: menu-service, order-service, kitchen-service, api-gateway

Shows HTTP status codes and connection status for each service.

### 🌐 **Show URLs**
```bash
./run.sh urls
```
Displays all service URLs and connection information:
- API Gateway and microservice endpoints
- Database connection details (host, port, username, password)
- RabbitMQ management console URL

### 🧹 **Cleanup**
```bash
./run.sh cleanup
```
- **WARNING**: This removes everything including data!
- Removes all containers, networks, AND volumes
- You'll be asked to confirm (y/n) before proceeding
- Use this for a complete fresh start

### ❓ **Help**
```bash
./run.sh help
# or just
./run.sh
```
Shows all available commands and usage information.

## Common Workflows

### First Time Setup
```bash
# 1. Make script executable
chmod +x run.sh

# 2. Start everything
./run.sh start

# 3. Check if services are healthy
./run.sh health

# 4. View service URLs
./run.sh urls
```

### Daily Development
```bash
# Start your day
./run.sh start

# Check everything is running
./run.sh status

# Make code changes...

# Rebuild and restart
./run.sh rebuild
./run.sh start

# Check logs if something isn't working
./run.sh logs order-service
```

### Debugging Issues
```bash
# Check overall status
./run.sh status

# Check health of all services
./run.sh health

# View logs for problematic service
./run.sh logs kitchen-service

# Restart if needed
./run.sh restart
```

### Complete Reset
```bash
# Stop everything
./run.sh stop

# Clean up all data (WARNING: deletes volumes!)
./run.sh cleanup

# Start fresh
./run.sh start
```

## Service Information

### Available Services

| Service | Port | Description |
|---------|------|-------------|
| **api-gateway** | 8080 | Main entry point for all API requests |
| **menu-service** | 8081 | Manages restaurant menu items |
| **order-service** | 8082 | Handles customer orders |
| **kitchen-service** | 8083 | Manages kitchen operations |
| **postgres-menu** | 5432 | Database for menu service |
| **postgres-order** | 5433 | Database for order service |
| **postgres-kitchen** | 5434 | Database for kitchen service |
| **rabbitmq** | 5672 | Message broker |
| **rabbitmq-admin** | 15672 | RabbitMQ management UI |

### Database Credentials

All PostgreSQL databases use the same credentials:
- **Username**: `admin`
- **Password**: `admin123`
- **Databases**: `menu_db`, `order_db`, `kitchen_db`

### RabbitMQ Credentials

- **Username**: `admin`
- **Password**: `admin123`
- **Management UI**: http://localhost:15672

## Troubleshooting

### "Docker is not running" error
**Solution**: Start Docker Desktop or Docker daemon first
```bash
# On Linux
sudo systemctl start docker
```

### Services won't start
**Solution**: Check if ports are already in use
```bash
# Check what's using port 8080
sudo lsof -i :8080

# Or stop everything and try again
./run.sh stop
./run.sh start
```

### Service is unhealthy
**Solution**: Check logs for errors
```bash
./run.sh logs <service-name>
./run.sh health
```

### Need a fresh start
**Solution**: Complete cleanup and restart
```bash
./run.sh cleanup  # Type 'y' to confirm
./run.sh start
```

### Changes not reflecting
**Solution**: Rebuild the services
```bash
./run.sh rebuild
./run.sh start
```

## Tips & Best Practices

1. **Always check status after starting**
   ```bash
   ./run.sh start && ./run.sh health
   ```

2. **Monitor logs during development**
   ```bash
   ./run.sh logs order-service
   ```

3. **Use health check before debugging**
   ```bash
   ./run.sh health
   ```
   This quickly shows which service is having issues.

4. **Keep the script executable**
   ```bash
   chmod +x run.sh
   ```

5. **Use specific service logs for faster debugging**
   ```bash
   # Instead of viewing all logs
   ./run.sh logs
   
   # View only the service you're working on
   ./run.sh logs menu-service
   ```

## Script Features

✅ **Color-coded output** - Easy to see success/error messages  
✅ **Docker health check** - Verifies Docker is running before operations  
✅ **Service health monitoring** - Checks database and microservice health  
✅ **Real-time logs** - Follow logs as they happen  
✅ **Safety confirmations** - Asks before destructive operations  
✅ **Helpful URLs** - Shows all endpoints and credentials  
✅ **Error handling** - Exits on errors to prevent cascading failures  

## What the Script Does Behind the Scenes

- **start**: Runs `docker-compose up -d`
- **stop**: Runs `docker-compose down`
- **restart**: Runs `docker-compose restart`
- **status**: Runs `docker-compose ps` and `docker ps`
- **logs**: Runs `docker-compose logs -f`
- **rebuild**: Runs `docker-compose build --no-cache`
- **cleanup**: Runs `docker-compose down -v` (removes volumes!)
- **health**: Checks PostgreSQL with `pg_isready`, RabbitMQ with diagnostics, and microservices with HTTP health endpoints

## Need More Help?

Run the help command to see all available options:
```bash
./run.sh help
```

Or check the [docker-compose.yml](file:///home/zeiny/Documents/resto-hub/docker-compose.yml) file to see the complete service configuration.
