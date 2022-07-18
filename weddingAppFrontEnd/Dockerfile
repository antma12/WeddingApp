FROM node:13.11.0 as build-step
RUN mkdir -p  wedding-app
WORKDIR /wedding-app
COPY wedding-app/package.json /wedding-app
RUN npm install
COPY ./wedding-app /wedding-app
RUN npm run build --prod

# CMD npm start
FROM nginx:1.17.1-alpine
COPY nginx.config /etc/nginx/conf.d/default.conf
COPY --from=build-step /wedding-app/dist/wedding-app /usr/share/nginx/html
EXPOSE 80

