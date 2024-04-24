package com.abs.herosofhappiness.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abs.herosofhappiness.entity.Client;

public interface ClientRepo extends JpaRepository<Client, Integer> {

}
