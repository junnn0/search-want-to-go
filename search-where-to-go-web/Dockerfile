FROM node:current-alpine
MAINTAINER plus4070@gmail.com
COPY . /search-where-to-go-web
WORKDIR /search-where-to-go-web
RUN npm install
EXPOSE 8081
CMD ["npm", "run", "serve"]
