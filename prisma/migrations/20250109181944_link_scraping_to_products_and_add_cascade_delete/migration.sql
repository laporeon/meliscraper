/*
  Warnings:

  - You are about to drop the `_CategoryToScraping` table. If the table is not empty, all the data it contains will be lost.
  - Added the required column `scrapingId` to the `products` table without a default value. This is not possible if the table is not empty.

*/
-- DropForeignKey
ALTER TABLE "_CategoryToScraping" DROP CONSTRAINT "_CategoryToScraping_A_fkey";

-- DropForeignKey
ALTER TABLE "_CategoryToScraping" DROP CONSTRAINT "_CategoryToScraping_B_fkey";

-- AlterTable
ALTER TABLE "products" ADD COLUMN     "scrapingId" TEXT NOT NULL;

-- DropTable
DROP TABLE "_CategoryToScraping";

-- AddForeignKey
ALTER TABLE "products" ADD CONSTRAINT "products_scrapingId_fkey" FOREIGN KEY ("scrapingId") REFERENCES "scrapings"("id") ON DELETE CASCADE ON UPDATE CASCADE;
