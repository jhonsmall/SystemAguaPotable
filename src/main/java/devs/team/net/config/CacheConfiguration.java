package devs.team.net.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(devs.team.net.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(devs.team.net.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(devs.team.net.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Usuario.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Usuario.class.getName() + ".usuarioRecibos", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Usuario.class.getName() + ".usuarioMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Servicio.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Servicio.class.getName() + ".servicioCostos", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Clasificacion.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Clasificacion.class.getName() + ".clasificacionCostos", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Clasificacion.class.getName() + ".clasificacionEscalasDelMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Clasificacion.class.getName() + ".clasificacionMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.EscalasDelMedidor.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Costo.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Costo.class.getName() + ".costoCostoMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Sector.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Sector.class.getName() + ".sectorCostos", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Sector.class.getName() + ".sectorMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.CostoMedidor.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Medidor.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Medidor.class.getName() + ".medidorCostoMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Medidor.class.getName() + ".medidorLecturaMedidors", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.LecturaMedidor.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.LecturaMedidor.class.getName() + ".lecturamedidorRecibos", jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Recibo.class.getName(), jcacheConfiguration);
            cm.createCache(devs.team.net.domain.Recibo.class.getName() + ".lecturaMedidors", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
