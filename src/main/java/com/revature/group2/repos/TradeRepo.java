package com.revature.group2.repos;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.group2.beans.Trade;

public interface TradeRepo  extends ReactiveCassandraRepository<Trade, UUID> {

}
