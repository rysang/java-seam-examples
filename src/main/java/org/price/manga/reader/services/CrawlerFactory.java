package org.price.manga.reader.services;

import org.price.manga.reader.services.api.Crawler;

public abstract class CrawlerFactory {

	public abstract Crawler newMangaCrawler();

	public abstract Crawler newIssueCrawler();
}
