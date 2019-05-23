package com.twodarray.helloworld.repository;

import com.twodarray.helloworld.entity.AnniversaryConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnniversaryConfigRepo extends JpaRepository<AnniversaryConfig, Long>
{
}
