DROP SCHEMA IF EXISTS "paymentorder" CASCADE;

CREATE SCHEMA "paymentorder";

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('PENDING', 'CANCELLED', 'COMPLETED');

DROP TABLE IF EXISTS "paymentorder".payment_orders CASCADE;
CREATE TABLE "paymentorder".payment_orders
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    idempotent_key uuid NOT NULL,
    amount numeric(10,2) NOT NULL,
    payment_order_status order_status NOT NULL,
    failure_reason character varying COLLATE pg_catalog."default",
    CONSTRAINT payment_orders_pkey PRIMARY KEY (id)
);