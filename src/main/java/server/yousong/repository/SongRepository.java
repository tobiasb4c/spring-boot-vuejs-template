package server.yousong.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import server.yousong.entity.Song;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "songs", path = "songs")
public interface SongRepository extends PagingAndSortingRepository<Song, Long> {
    
}