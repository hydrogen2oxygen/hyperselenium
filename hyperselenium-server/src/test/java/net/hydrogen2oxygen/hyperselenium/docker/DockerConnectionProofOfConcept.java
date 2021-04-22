package net.hydrogen2oxygen.hyperselenium.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.util.List;

public class DockerConnectionProofOfConcept {

    public static void main(String[] args) {

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .build();

        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        List<Container> containers = dockerClient.listContainersCmd().exec();

        for (Container container : containers) {
            System.out.print(container.getId());
            for (String name : container.getNames()) {
                System.out.print(" " + name);
            }
            System.out.println("");
        }
    }
}
