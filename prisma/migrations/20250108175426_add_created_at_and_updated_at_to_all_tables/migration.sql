/*
  Warnings:

  - You are about to drop the column `created_at` on the `categories` table. All the data in the column will be lost.
  - You are about to drop the column `created_at` on the `products` table. All the data in the column will be lost.
  - Added the required column `updatedAt` to the `categories` table without a default value. This is not possible if the table is not empty.
  - Added the required column `updatedAt` to the `products` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "categories" DROP COLUMN "created_at",
ADD COLUMN     "createdAt" TIMESTAMPTZ(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "updatedAt" TIMESTAMPTZ(3) NOT NULL;

-- AlterTable
ALTER TABLE "products" DROP COLUMN "created_at",
ADD COLUMN     "createdAt" TIMESTAMPTZ(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "updatedAt" TIMESTAMPTZ(3) NOT NULL;

-- AlterTable
ALTER TABLE "scrapings" ADD COLUMN     "createdAt" TIMESTAMPTZ(3) NOT NULL DEFAULT CURRENT_TIMESTAMP;
