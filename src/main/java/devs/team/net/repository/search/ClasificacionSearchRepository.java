package devs.team.net.repository.search;

import devs.team.net.domain.Clasificacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Clasificacion entity.
 */
public interface ClasificacionSearchRepository extends ElasticsearchRepository<Clasificacion, Long> {
}
