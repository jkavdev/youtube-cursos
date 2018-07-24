package br.com.jkavdev.angularjs.listatelefonicaapi.controller.cache;

import net.sf.ehcache.statistics.StatisticsGateway;

import java.io.Serializable;

public class CacheStatisticDTO implements Serializable {

    private String cacheName;
    private String cacheHits;
    private String cacheMisses;
    private String objectCount;
    private String inMemoryHits;
    private String inMemoryMisses;
    private String memoryStoreObjectCount;
    private String onDiskHits;
    private String onDiskMisses;
    private String diskStoreObjectCount;

    public CacheStatisticDTO() {
    }

    public CacheStatisticDTO(String cacheName, String cacheHits, String cacheMisses) {
        this.cacheName = cacheName;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
    }

    public CacheStatisticDTO(String cacheName, String cacheHits, String cacheMisses, String objectCount, String inMemoryHits, String inMemoryMisses, String memoryStoreObjectCount, String onDiskHits, String onDiskMisses, String diskStoreObjectCount) {
        this.cacheName = cacheName;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
        this.objectCount = objectCount;
        this.inMemoryHits = inMemoryHits;
        this.inMemoryMisses = inMemoryMisses;
        this.memoryStoreObjectCount = memoryStoreObjectCount;
        this.onDiskHits = onDiskHits;
        this.onDiskMisses = onDiskMisses;
        this.diskStoreObjectCount = diskStoreObjectCount;
    }

    public static CacheStatisticDTO from(StatisticsGateway stats) {
        return new CacheStatisticDTO(
                stats.getAssociatedCacheName(),
                String.valueOf(stats.cacheHitCount()),
                String.valueOf(stats.cacheMissCount()));
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheHits() {
        return cacheHits;
    }

    public void setCacheHits(String cacheHits) {
        this.cacheHits = cacheHits;
    }

    public String getCacheMisses() {
        return cacheMisses;
    }

    public void setCacheMisses(String cacheMisses) {
        this.cacheMisses = cacheMisses;
    }

    public String getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(String objectCount) {
        this.objectCount = objectCount;
    }

    public String getInMemoryHits() {
        return inMemoryHits;
    }

    public void setInMemoryHits(String inMemoryHits) {
        this.inMemoryHits = inMemoryHits;
    }

    public String getInMemoryMisses() {
        return inMemoryMisses;
    }

    public void setInMemoryMisses(String inMemoryMisses) {
        this.inMemoryMisses = inMemoryMisses;
    }

    public String getMemoryStoreObjectCount() {
        return memoryStoreObjectCount;
    }

    public void setMemoryStoreObjectCount(String memoryStoreObjectCount) {
        this.memoryStoreObjectCount = memoryStoreObjectCount;
    }

    public String getOnDiskHits() {
        return onDiskHits;
    }

    public void setOnDiskHits(String onDiskHits) {
        this.onDiskHits = onDiskHits;
    }

    public String getOnDiskMisses() {
        return onDiskMisses;
    }

    public void setOnDiskMisses(String onDiskMisses) {
        this.onDiskMisses = onDiskMisses;
    }

    public String getDiskStoreObjectCount() {
        return diskStoreObjectCount;
    }

    public void setDiskStoreObjectCount(String diskStoreObjectCount) {
        this.diskStoreObjectCount = diskStoreObjectCount;
    }
}
