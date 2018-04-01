package devs.team.net.repository.search;

import devs.team.net.domain.Servicio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Servicio entity.
 */
public interface ServicioSearchRepository extends ElasticsearchRepository<Servicio, Long> {
}
