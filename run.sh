#!/bin/bash

# RestoHub Docker Management Script
# Manages the complete RestoHub microservices stack

set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored messages
print_info() {
    echo -e "${BLUE}ℹ ${1}${NC}"
}

print_success() {
    echo -e "${GREEN}✓ ${1}${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ ${1}${NC}"
}

print_error() {
    echo -e "${RED}✗ ${1}${NC}"
}

# Function to check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker first."
        exit 1
    fi
    print_success "Docker is running"
}

# Function to start all services
start_services() {
    print_info "Starting RestoHub services..."
    docker-compose up -d
    print_success "All services started"
    show_status
}

# Function to stop all services
stop_services() {
    print_info "Stopping RestoHub services..."
    docker-compose down
    print_success "All services stopped"
}

# Function to restart all services
restart_services() {
    print_info "Restarting RestoHub services..."
    docker-compose restart
    print_success "All services restarted"
    show_status
}

# Function to show service status
show_status() {
    print_info "Service Status:"
    docker-compose ps
    echo ""
    print_info "Container Details:"
    docker ps --filter "name=resto-hub|postgres-|rabbitmq" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
}

# Function to show logs
show_logs() {
    if [ -z "$1" ]; then
        print_info "Showing logs for all services (press Ctrl+C to exit)..."
        docker-compose logs -f
    else
        print_info "Showing logs for $1 (press Ctrl+C to exit)..."
        docker-compose logs -f "$1"
    fi
}

# Function to rebuild services
rebuild_services() {
    print_info "Rebuilding RestoHub services..."
    docker-compose build --no-cache
    print_success "Services rebuilt"
}

# Function to clean up
cleanup() {
    print_warning "This will remove all containers, networks, and volumes. Continue? (y/n)"
    read -r response
    if [ "$response" = "y" ]; then
        print_info "Cleaning up..."
        docker-compose down -v
        print_success "Cleanup completed"
    else
        print_info "Cleanup cancelled"
    fi
}

# Function to check service health
check_health() {
    print_info "Checking service health..."
    echo ""
    
    # Check databases
    print_info "Database Health:"
    docker exec postgres-menu pg_isready -U admin -d menu_db && print_success "postgres-menu: healthy" || print_error "postgres-menu: unhealthy"
    docker exec postgres-order pg_isready -U admin -d order_db && print_success "postgres-order: healthy" || print_error "postgres-order: unhealthy"
    docker exec postgres-kitchen pg_isready -U admin -d kitchen_db && print_success "postgres-kitchen: healthy" || print_error "postgres-kitchen: unhealthy"
    
    echo ""
    print_info "RabbitMQ Health:"
    docker exec rabbitmq rabbitmq-diagnostics -q ping && print_success "rabbitmq: healthy" || print_error "rabbitmq: unhealthy"
    
    echo ""
    print_info "Microservices Status:"
    curl -s -o /dev/null -w "menu-service (8081): %{http_code}\n" http://localhost:8081/actuator/health || echo "menu-service: not responding"
    curl -s -o /dev/null -w "order-service (8082): %{http_code}\n" http://localhost:8082/actuator/health || echo "order-service: not responding"
    curl -s -o /dev/null -w "kitchen-service (8083): %{http_code}\n" http://localhost:8083/actuator/health || echo "kitchen-service: not responding"
    curl -s -o /dev/null -w "api-gateway (8080): %{http_code}\n" http://localhost:8080/actuator/health || echo "api-gateway: not responding"
}

# Function to show service URLs
show_urls() {
    print_info "Service URLs:"
    echo "  🌐 API Gateway:      http://localhost:8080"
    echo "  📋 Menu Service:     http://localhost:8081"
    echo "  🛒 Order Service:    http://localhost:8082"
    echo "  👨‍🍳 Kitchen Service:   http://localhost:8083"
    echo ""
    print_info "Database Connections:"
    echo "  🐘 Menu DB:          localhost:5432 (menu_db/admin/admin123)"
    echo "  🐘 Order DB:         localhost:5433 (order_db/admin/admin123)"
    echo "  🐘 Kitchen DB:       localhost:5434 (kitchen_db/admin/admin123)"
    echo ""
    print_info "Message Broker:"
    echo "  🐰 RabbitMQ:         localhost:5672"
    echo "  🎛️  RabbitMQ Admin:   http://localhost:15672 (admin/admin123)"
}

# Function to show help
show_help() {
    echo "RestoHub Docker Management Script"
    echo ""
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  start       Start all services"
    echo "  stop        Stop all services"
    echo "  restart     Restart all services"
    echo "  status      Show service status"
    echo "  logs        Show logs for all services"
    echo "  logs <svc>  Show logs for specific service"
    echo "  rebuild     Rebuild all services"
    echo "  health      Check health of all services"
    echo "  urls        Show service URLs and connection info"
    echo "  cleanup     Remove all containers, networks, and volumes"
    echo "  help        Show this help message"
    echo ""
    echo "Available services for logs:"
    echo "  menu-service, order-service, kitchen-service, api-gateway"
    echo "  postgres-menu, postgres-order, postgres-kitchen, rabbitmq"
}

# Main script logic
check_docker

case "${1:-help}" in
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    restart)
        restart_services
        ;;
    status)
        show_status
        ;;
    logs)
        show_logs "$2"
        ;;
    rebuild)
        rebuild_services
        ;;
    health)
        check_health
        ;;
    urls)
        show_urls
        ;;
    cleanup)
        cleanup
        ;;
    help|*)
        show_help
        ;;
esac