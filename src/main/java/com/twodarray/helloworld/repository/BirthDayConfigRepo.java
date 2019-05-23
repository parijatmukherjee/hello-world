package com.twodarray.helloworld.repository;

import com.twodarray.helloworld.entity.BirthDayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthDayConfigRepo extends JpaRepository<BirthDayConfig, Long>
{
}
