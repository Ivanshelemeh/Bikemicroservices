ALTER TABLE bike_customer
ADD   version_id integer,
ADD create_at TIMESTAMP,
ADD lastmodify_at TIMESTAMP;

ALTER TABLE bike_order
ADD version integer,
ADD  created_at TIMESTAMP,
ADD  lastmod_at TIMESTAMP;