package co.com.sofka.cuenta.config;

import co.com.sofka.cuenta.clients.ClienteClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class ClienteRestConfig {

    @Bean
    public RestClient personaRestClient(@Value("${cuenta.api.url}") String urlBase) {

        return RestClient.builder()
                .requestFactory(customRequestFactory())
                .baseUrl(urlBase)
                .build();
    }


    @Bean
    public ClienteClient clienteClient (RestClient personaRestClient) {
        RestClientAdapter adapter = RestClientAdapter.create(personaRestClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ClienteClient.class);
    }


    private ClientHttpRequestFactory customRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofMillis(10000))
                .withReadTimeout(Duration.ofMillis(10000));
        return ClientHttpRequestFactories.get(settings);
    }


}
