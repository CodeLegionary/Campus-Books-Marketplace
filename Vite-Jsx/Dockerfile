# frontend/Dockerfile
# Stage 1: Build the React/Vite application
FROM node:20 AS builder
# Use 'AS builder' to name this stage

WORKDIR /app
# Copy package.json and package-lock.json first to leverage Docker's build cache
COPY package*.json ./
RUN npm install
ARG VITE_APP_API_BASE_URL
ENV VITE_APP_API_BASE_URL=$VITE_APP_API_BASE_URL
# Copy the rest of your application code
COPY . .

# Build the frontend for production
RUN npm run build # This command generates optimized static files in the 'dist' directory

# Stage 2: Serve the built static files using a lightweight web server (e.g., Nginx)
FROM nginx:alpine
# Use a lightweight Nginx image
COPY --from=builder /app/dist /usr/share/nginx/html
# Copy built files to Nginx's default public directory

# Expose the port Nginx listens on (default is 80)
EXPOSE 80

# Command to run Nginx
CMD ["nginx", "-g", "daemon off;"]