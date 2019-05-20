FROM oracle/graalvm-ce:1.0.0-rc16 as graalvm
COPY . /home/app/basic-app
WORKDIR /home/app/basic-app
RUN native-image --no-server --enable-url-protocols=http -jar build/libs/basic-app-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/basic-app .
ENTRYPOINT ["./basic-app"]
