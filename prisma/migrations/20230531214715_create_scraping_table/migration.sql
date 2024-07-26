/*
  Warnings:

  - You are about to drop the column `scrapings` on the `categories` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE "categories" DROP COLUMN "scrapings";

-- CreateTable
CREATE TABLE "scrapings" (
    "id" TEXT NOT NULL,
    "date" DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "scrapings_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "_CategoryToScraping" (
    "A" TEXT NOT NULL,
    "B" TEXT NOT NULL
);

-- CreateIndex
CREATE UNIQUE INDEX "_CategoryToScraping_AB_unique" ON "_CategoryToScraping"("A", "B");

-- CreateIndex
CREATE INDEX "_CategoryToScraping_B_index" ON "_CategoryToScraping"("B");

-- AddForeignKey
ALTER TABLE "_CategoryToScraping" ADD CONSTRAINT "_CategoryToScraping_A_fkey" FOREIGN KEY ("A") REFERENCES "categories"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "_CategoryToScraping" ADD CONSTRAINT "_CategoryToScraping_B_fkey" FOREIGN KEY ("B") REFERENCES "scrapings"("id") ON DELETE CASCADE ON UPDATE CASCADE;
