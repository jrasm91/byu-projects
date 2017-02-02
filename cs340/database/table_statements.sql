drop table if exists node;
drop table if exists neighbors;
drop table if exists s_neighbors;
drop table if exists inverse_s_neighbors;
drop table if exists remote_nodes;

create table node
(
	webid			integer 	not null 	primary key,
	fold			integer,
	s_fold 			integer,
	inverse_s_fold	integer,
	height			integer 	not null,
	state			text		not null,
	local_id 		integer		not null
);

create table neighbors
(
	node_webid			integer 	not null,
	neighbor_webid		integer		not null
);

create table s_neighbors
(
	node_webid			integer 	not null,
	s_neighbor_webid	integer		not null
);

create table inverse_s_neighbors
(
	node_webid					integer		not null,
	inverse_s_neighbor_webid	integer		not null
);

create table remote_nodes
(
	node_webid			integer		not null	primary key,
	local_id			integer		not null,
	remote_server		text,
	remote_port			integer		not null
);
