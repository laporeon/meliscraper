ALTER TABLE categories DROP CONSTRAINT IF EXISTS categories_snapshot_id_fkey;
ALTER TABLE categories DROP COLUMN IF EXISTS snapshot_id;

ALTER TABLE categories DROP CONSTRAINT IF EXISTS categories_slug_snapshot_unique;

ALTER TABLE categories ADD CONSTRAINT categories_slug_unique UNIQUE (slug);

CREATE TABLE IF NOT EXISTS snapshot_categories (
    snapshot_id UUID NOT NULL,
    category_id UUID NOT NULL,
    PRIMARY KEY (snapshot_id, category_id),
    FOREIGN KEY (snapshot_id) REFERENCES snapshots (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_snapshot_categories_snapshot ON snapshot_categories(snapshot_id);
CREATE INDEX IF NOT EXISTS idx_snapshot_categories_category ON snapshot_categories(category_id);

CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    position INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    image TEXT NOT NULL,
    price INT NOT NULL,
    link TEXT NOT NULL,
    category_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_products_category_id ON products(category_id);
CREATE INDEX IF NOT EXISTS idx_products_position ON products(position);