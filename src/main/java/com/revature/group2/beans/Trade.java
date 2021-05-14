package com.revature.group2.beans;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import lombok.Data;

@Data
public class Trade {
	@Column
	String poster;
	@Column
	UUID posterId;
	@Column
	@CassandraType(type = Name.TEXT)
	String card1;
	@Column
	String acceptor;
	@Column
	UUID acceptorId;
	@Column
	String card2;
	@Column
	TradeStatus tradeStatus;
	@PrimaryKey
	@CassandraType(type = Name.UUID)
	UUID tradeId;

}
