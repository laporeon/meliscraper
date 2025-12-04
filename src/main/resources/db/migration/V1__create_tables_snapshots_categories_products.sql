CREATE TABLE IF NOT EXISTS snapshots (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    snapshot_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT snapshots_date_unique UNIQUE (snapshot_date)
);

CREATE TABLE IF NOT EXISTS categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT categories_slug_unique UNIQUE (slug)
);

CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    position INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    image TEXT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    link TEXT NOT NULL,
    category_id UUID NOT NULL,
    snapshot_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT,
    CONSTRAINT fk_products_snapshot FOREIGN KEY (snapshot_id) REFERENCES snapshots (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_products_category ON products(category_id);
CREATE INDEX IF NOT EXISTS idx_products_snapshot ON products(snapshot_id);
CREATE INDEX IF NOT EXISTS idx_products_position ON products(position);