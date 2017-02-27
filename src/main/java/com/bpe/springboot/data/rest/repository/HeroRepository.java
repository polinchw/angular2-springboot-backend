package com.bpe.springboot.data.rest.repository;

import com.bpe.springboot.data.rest.entity.Hero;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by polinchakb on 2/26/17.
 */
@RepositoryRestResource(collectionResourceRel = "heros", path = "heros")
public interface HeroRepository extends PagingAndSortingRepository<Hero, Long> {
}
